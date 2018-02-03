package com.lmt.data.unstructured.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lmt.data.unstructured.entity.Collection;
import com.lmt.data.unstructured.entity.search.CollectionSearch;
import com.lmt.data.unstructured.repository.CollectionRepository;
import com.lmt.data.unstructured.service.CollectionService;
import com.lmt.data.unstructured.util.CheckResult;
import com.lmt.data.unstructured.util.EntityManagerQuery;
import com.lmt.data.unstructured.util.ResultData;

/**
 * @author MT-Lin
 * @date 2018/1/25 9:43
 */
@Service("CollectServiceImpl")
@SuppressWarnings({ "rawtypes" })
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private CollectionRepository collectionRepository;

	@Autowired
	private EntityManagerQuery entityManagerQuery;

	@Override
	public Map save(Collection collection) {
		Collection exist = collectionRepository.findByObjId(collection.getObjId());
		if (null != exist) {
			return ResultData.newError("请勿重复收藏");
		}
		this.collectionRepository.save(collection);
		if (null == collection.getId()) {
			return ResultData.newError("收藏失败，请反馈，谢谢！");
		}
		return ResultData.newOK("资源收藏成功");
	}

	@Override
	public int getCollectNum(String userId) {
		return this.collectionRepository.countByCreator(userId);
	}

	@Override
	public Map delete(Collection collection) {
		Collection exist = this.collectionRepository.findByObjIdAndAndCreator(collection.getObjId(),
				collection.getCreator());
		if (exist == null) {
			return ResultData.newError("该资源还未收藏取消收藏失败");
		}
		this.collectionRepository.delete(exist);
		return ResultData.newOK("成功取消收藏");
	}

	@Override
	public Map search(CollectionSearch collectionSearch) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.id, c.creator, r.designation, ");
		sql.append("c.collection_folder_id AS collectionFolderId, ");
		sql.append("c.obj_id AS objId, ");
		sql.append("r.id AS resourceId, ");
		sql.append("r.es_id AS esId, ");
		sql.append("(SELECT count(id) FROM resource_download AS rd WHERE rd.resource_id = c.obj_id) ");
		sql.append("AS downloadNum, ");
		sql.append("ui.user_name AS author, ");
		sql.append("c.create_time AS createTime, ");
		sql.append("cf.designation AS folder ");
		sql.append("FROM collection AS c, resource AS r, collection_folder AS cf, user_info AS ui ");
		sql.append("WHERE r.id = c.obj_id AND cf.id = c.collection_folder_id ");
		sql.append("AND ui.id = r.author_id AND c.creator = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(collectionSearch.getCreator());
		if (!StringUtils.isEmpty(collectionSearch.getDesignation())) {
			sql.append("AND r.designation LIKE ? ");
			parameters.add("%" + collectionSearch.getDesignation() + "%");
		}
		sql.append("ORDER BY c.create_time DESC ");
		Map<String, Object> result = entityManagerQuery.paginationSearch(sql, parameters, collectionSearch);
		return ResultData.newOK("个人收藏信息查询成功", result);
	}

	@Override
	public Map update(Collection collection) {
		Collection exist = this.collectionRepository.findOne(collection.getId());
		if (null == exist) {
			return ResultData.newError("修改的收藏信息不存在");
		}
		this.collectionRepository.save(collection);
		return ResultData.newOK("成功修改收藏信息");
	}

	@Override
	public Map delete(List<Collection> collections) {
		Map result;
		for (Collection collection : collections) {
			result = this.delete(collection);
			if (!CheckResult.isOK(result)) {
				return result;
			}
		}
		return ResultData.newOK("成功取消收藏");
	}

	@Override
	public List getCollected(String userId, List<String> resourceIdList) {
		if (resourceIdList.size() == 0) {
			return resourceIdList;
		}
		StringBuffer sql = new StringBuffer();
		List<Object> parameters = new ArrayList<>();
		sql.append("SELECT c.obj_id AS resourceId ");
		sql.append("FROM collection AS c ");
		sql.append("WHERE c.creator = ? ");
		parameters.add(userId);
		sql.append("AND obj_id IN (");
		for (String resourceId : resourceIdList) {
			sql.append("?, ");
			parameters.add(resourceId);
		}
		sql.setLength(sql.length() - 2);
		sql.append(" )");
		return this.entityManagerQuery.nativeSqlSearchOneColumn(sql, parameters);
	}
}
