package com.lmt.data.unstructured.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lmt.data.unstructured.entity.Resource;
import com.lmt.data.unstructured.entity.Tag;
import com.lmt.data.unstructured.entity.es.ResourceEs;
import com.lmt.data.unstructured.entity.es.ResourceEsUser;
import com.lmt.data.unstructured.entity.search.ResourceEsSearch;
import com.lmt.data.unstructured.repository.TagRepository;
import com.lmt.data.unstructured.service.CollectionService;
import com.lmt.data.unstructured.service.ResourceEsService;
import com.lmt.data.unstructured.service.ResourceService;
import com.lmt.data.unstructured.service.UserInfoService;
import com.lmt.data.unstructured.util.EntityUtils;
import com.lmt.data.unstructured.util.FileUtil;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import com.lmt.data.unstructured.util.UdConstant;

/**
 * @author MT-Lin
 * @date 2018/1/17 8:52
 */
@Service("ResourceEsServiceImpl")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ResourceEsServiceImpl implements ResourceEsService {

	private Logger logger = LoggerFactory.getLogger(ResourceEsService.class);

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private CollectionService collectionService;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private RedisCache redisCache;

	@Autowired
	private TransportClient transportClient;

	/**
	 * resource的ES类型
	 */
	private final String ES_TYPE = "resource";

	/**
	 * 查询字段
	 */
	private final String[] fieldNames = { "id", "auditRemark", "author", "content", "description", "designation",
			"tags" };

	@Override
	public Map saveResourceES(Resource resource, String auditRemark) {
		ResourceEs resourceEs = new ResourceEs();
		BeanUtils.copyProperties(resource, resourceEs, EntityUtils.getNullPropertyNames(resource));
		resourceEs.setId(null);
		resourceEs.setResourceId(resource.getId());
		resourceEs.setAuthor(this.userInfoService.getUserNameById(resource.getAuthorId()));
		resourceEs.setContent(fileUtil.getFileContent(resource.getResourceFileName()));
		resourceEs.setAuditRemark(auditRemark);
		Tag tag = tagRepository.findByResourceId(resource.getId());
		if (null != tag) {
			resourceEs.setTags(Arrays.asList(tag.getTag().split(",")));
		}
		IndexResponse response = transportClient.prepareIndex(UdConstant.ES_INDEX, this.ES_TYPE)
				.setSource(JSON.toJSONString(resourceEs), XContentType.JSON).get();
		if (null == resource.getId()) {
			return ResultData.newError("返回的es_id为空");
		}
		resource.setEsId(response.getId());
		return ResultData.newOK("数据成功保存到ES");
	}

	@Override
	public Map searchFromEs(ResourceEsSearch resourceEsSearch) {
		String keyword = resourceEsSearch.getKeyword().replace("%", "");
		SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(UdConstant.ES_INDEX);
		searchRequestBuilder.setFrom(resourceEsSearch.getCurrentPage() - 1).setSize(resourceEsSearch.getPageSize())
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setExplain(true);
		searchRequestBuilder.setQuery(QueryBuilders.multiMatchQuery(keyword, this.fieldNames).analyzer("ik_max_word"));
		// 设置高亮字段
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("content").field("designation");
		highlightBuilder.preTags("<span style=\"color:red\">");
		highlightBuilder.postTags("</span>");
		searchRequestBuilder.highlighter(highlightBuilder);
		// 获取返回数据
		SearchResponse searchResponse = searchRequestBuilder.get();
		List<ResourceEsUser> resourceEsUsers = new ArrayList<>();
		List<String> resourceIdList = new ArrayList<>();
		Map<String, Object> sourceAsMap;
		StringBuilder highlight = new StringBuilder();
		SearchHits hits = searchResponse.getHits();
		String id;
		for (SearchHit hit : hits) {
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			sourceAsMap = hit.getSourceAsMap();
			id = hit.getId();
			// 处理高亮字段
			for (String field : highlightFields.keySet()) {
				Text[] fragments = highlightFields.get(field).getFragments();
				for (Text fragment : fragments) {
					if ("designation".equals(field)) {
						String replace = fragment.string().replace("<span style=\"color:red\">", "").replace("</span>",
								"");
						String newStr = sourceAsMap.get(field).toString().replace(replace, fragment.string());
						sourceAsMap.replace(field, newStr);
					} else {
						highlight.append(fragment.string());
					}
				}
				if (highlight.length() > 0) {
					highlight.append("...");
				}
				sourceAsMap.put("highlight", highlight.toString());
				highlight.setLength(0);
			}
			sourceAsMap.put("id", id);
			ResourceEsUser resourceEsUser = JSONObject.parseObject(JSON.toJSONString(sourceAsMap),
					ResourceEsUser.class);
			resourceIdList.add(resourceEsUser.getResourceId());
			resourceEsUsers.add(resourceEsUser);
		}
		// 资源收藏情况设置
		resourceIdList = this.collectionService.getCollected(redisCache.getUserId(resourceEsSearch), resourceIdList);
		for (ResourceEsUser resourceEsUser : resourceEsUsers) {
			if (resourceIdList.contains(resourceEsUser.getResourceId())) {
				resourceEsUser.setCollected(true);
			}
		}
		Map<String, Object> result = new HashMap<>(3);
		result.put("content", resourceEsUsers);
		result.put("totalElements", hits.getTotalHits());
		return ResultData.newOK("查询成功", result);
	}

	@Override
	public void updateDownloadNum(String esId) {
		UpdateRequest updateRequest = new UpdateRequest(UdConstant.ES_INDEX, this.ES_TYPE, esId)
				.script(new Script("ctx._source.downloadNum += 1"));
		try {
			UpdateResponse updateResponse = transportClient.update(updateRequest).get();
			int responseStatus = updateResponse.status().getStatus();
			if (responseStatus != UdConstant.ES_RESPONSE_SUCCESS) {
				logger.error("ES更新下载次数出错，返回的状态码：{}", responseStatus);
			}
		} catch (InterruptedException e) {
			logger.error("ES更新下载次数时出现异常: InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			logger.error("ES更新下载次数时出现异常: ExecutionException");
			e.printStackTrace();
		}
	}

	@Override
	public void updateCollectionNum(String resourceId, int collectionOperation) {
		Resource resource = this.resourceService.findOneById(resourceId);
		if (null == resource) {
			logger.error("收藏操作的资源[ID={}]不存在", resourceId);
			return;
		}
		UpdateRequest updateRequest = new UpdateRequest(UdConstant.ES_INDEX, this.ES_TYPE, resource.getEsId());
		Script script;
		switch (collectionOperation) {
		case UdConstant.COLLECTION_OPERATION_ADD:
			script = new Script("ctx._source.collectionNum += 1");
			break;
		case UdConstant.COLLECTION_OPERATION_CANCEL:
			script = new Script("ctx._source.collectionNum -= 1");
			break;
		default:
			return;
		}
		updateRequest.script(script);
		try {
			UpdateResponse updateResponse = transportClient.update(updateRequest).get();
			int responseStatus = updateResponse.status().getStatus();
			if (responseStatus != UdConstant.ES_RESPONSE_SUCCESS) {
				logger.error("ES更新收藏次数出错，返回的状态码：{}", responseStatus);
			}
		} catch (InterruptedException e) {
			logger.error("ES更新收藏次数时出现异常：InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			logger.error("ES更新收藏次数时出现异常：ExecutionException");
			e.printStackTrace();
		}
	}
}
