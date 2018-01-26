package com.lmt.data.unstructured.service.impl;

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
import com.lmt.data.unstructured.service.UserInfoService;
import com.lmt.data.unstructured.util.*;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author MT-Lin
 * @date 2018/1/17 8:52
 */
@SuppressWarnings("TryWithIdenticalCatches")
@Service("ResourceEsServiceImpl")
public class ResourceEsServiceImpl implements ResourceEsService {

    private Logger logger = LoggerFactory.getLogger(ResourceEsService.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TransportClient client;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private FileUtil fileUtil;

    /**
     * resource的ES类型
     */
    private final String ES_TYPE = "resource";

    /**
     * 查询字段
     */
    private final String[] fieldNames = {"auditRemark", "author", "content", "description", "designation", "tags"};

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
        if (null != tag){
            resourceEs.setTags(Arrays.asList(tag.getTag().split(",")));
        }
        IndexResponse response = client.prepareIndex(UdConstant.ES_INDEX, this.ES_TYPE)
                .setSource(JSON.toJSONString(resourceEs), XContentType.JSON)
                .get();
        if (null == resource.getId()){
            return ResultData.newError("返回的es_id为空");
        }
        resource.setEsId(response.getId());
        return ResultData.newOK("数据成功保存到ES");
    }

    @Override
    public Map searchFromEs(ResourceEsSearch resourceEsSearch) {
        String keyword = resourceEsSearch.getKeyword().replace("%","");
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(UdConstant.ES_INDEX);
        searchRequestBuilder
                .setFrom(resourceEsSearch.getCurrentPage() - 1)
                .setSize(resourceEsSearch.getPageSize())
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setExplain(true);
        searchRequestBuilder.setQuery(QueryBuilders.multiMatchQuery(keyword, this.fieldNames).analyzer("ik_max_word"));
        // 设置高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("content").field("designation");
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchRequestBuilder.highlighter(highlightBuilder);
        // 获取返回数据
        SearchResponse searchResponse = searchRequestBuilder.get();
        Map<String, Object> result = new HashMap<>(2);
        List<Object> resources = new ArrayList<>();
        Map<String, Object> sourceAsMap;
        StringBuilder highlight = new StringBuilder();
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            sourceAsMap = hit.getSourceAsMap();
            for (String s : highlightFields.keySet()) {
                Text[] fragments = highlightFields.get(s).getFragments();
                for (Text fragment : fragments) {
                    if ("designation".equals(s)){
                        String replace = fragment.string()
                                .replace("<span style=\"color:red\">", "")
                                .replace("</span>", "");
                        String newStr = sourceAsMap.get(s).toString().replace(replace, fragment.string());
                        sourceAsMap.replace(s, newStr);
                    } else {
                        highlight.append(fragment.string());
                    }
                }
                if (highlight.length() > 0 ){
                    highlight.append("...");
                }
                sourceAsMap.put("highlight", highlight.toString());
                highlight.setLength(0);
            }
            ResourceEsUser resourceEsUser =
                    JSONObject.parseObject(JSON.toJSONString(sourceAsMap), ResourceEsUser.class);
            // TODO 判断资源是否收藏过
            resourceEsUser.setCollected(
                    this.collectionService.isCollected(
                            redisCache.getUserId(resourceEsSearch), resourceEsUser.getResourceId()));
            resources.add(resourceEsUser);
        }
        result.put("content", resources);
        result.put("totalElements", hits.getTotalHits());
        return ResultData.newOK("查询成功", result);
    }

    @Override
    public void updateDownloadNum(String esId, int downloadNum) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(UdConstant.ES_INDEX);
        updateRequest.type(this.ES_TYPE);
        updateRequest.id(esId);
        try {
            updateRequest.doc(jsonBuilder().startObject().field("downloadNum", downloadNum).endObject());
        } catch (IOException e) {
            logger.error("updateDownloadNum jsonBuilder 出现IO异常");
            e.printStackTrace();
        }
        try {
            client.update(updateRequest).get();
        } catch (InterruptedException e) {
            logger.error("ES更新下载次数时出现异常");
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.error("ES更新下载次数时出现异常");
            e.printStackTrace();
        }

    }

    @Override
    public void updateCollectionNum(String esId, int collectionNum) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(UdConstant.ES_INDEX);
        updateRequest.type(this.ES_TYPE);
        updateRequest.id(esId);
        try {
            updateRequest.doc(jsonBuilder().startObject().field("collectionNum", collectionNum).endObject());
        } catch (IOException e) {
            logger.error("updateCollectionNum jsonBuilder 出现IO异常");
            e.printStackTrace();
        }
        try {
            client.update(updateRequest).get();
        } catch (InterruptedException e) {
            logger.error("ES更新收藏次数时出现异常");
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.error("ES更新收藏次数时出现异常");
            e.printStackTrace();
        }
    }
}
