package com.lmt.data.unstructured.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lmt.data.unstructured.base.BaseSearch;

/**
 * @author MT-Lin
 * @date 2018/1/7 18:11
 */
@Component
@SuppressWarnings({ "rawtypes" })
public class EntityManagerQuery {

	private EntityManager entityManager;

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Map<String, Object> paginationSearch(StringBuffer sql, List<Object> parameters, BaseSearch baseSearch) {
		Query getResultQuery = entityManager.createNativeQuery(sql.toString());
		String countBuffer = "SELECT COUNT(*) FROM (" + sql + ") AS COUNT_TABLE";
		// 设置参数
		int position = 1;
		Query countQuery = entityManager.createNativeQuery(countBuffer);
		for (Object parameter : parameters) {
			countQuery.setParameter(position, parameter);
			getResultQuery.setParameter(position, parameter);
			position++;
		}
		// 数据总数量
		Object totalElements = countQuery.getResultList().get(0);
		getResultQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		int currentPage = baseSearch.getCurrentPage() - 1;
		int pageSize = baseSearch.getPageSize();
		getResultQuery.setFirstResult(currentPage * pageSize);
		getResultQuery.setMaxResults(pageSize);
		List resultList = getResultQuery.getResultList();
		Map<String, Object> resultMap = new HashMap<>(2);
		resultMap.put(UdConstant.TOTAL_ELEMENTS, totalElements);
		resultMap.put(UdConstant.CONTENT, resultList);
		return resultMap;
	}

	public Map<String, Object> paginationSearch(StringBuffer sql, BaseSearch baseSearch) {
		String keyword = baseSearch.getKeyword();
		int currentPage = baseSearch.getCurrentPage() - 1;
		int pageSize = baseSearch.getPageSize();
		Query nativeQuery = entityManager.createNativeQuery(sql.toString());
		// 设置参数
		for (int i = 0; i < baseSearch.getParamsCount(); i++) {
			nativeQuery.setParameter(i + 1, keyword);
		}
		// 数据总数量
		Object totalElements = nativeQuery.getResultList().size();
		nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		nativeQuery.setFirstResult(currentPage * pageSize);
		nativeQuery.setMaxResults(pageSize);
		List resultList = nativeQuery.getResultList();
		Map<String, Object> resultMap = new HashMap<>(3);
		resultMap.put(UdConstant.TOTAL_ELEMENTS, totalElements);
		resultMap.put(UdConstant.CONTENT, resultList);
		return resultMap;
	}

	public List nativeSqlSearch(StringBuffer sql, List<Object> parameters, int dataSize) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql.toString());
		int position = 1;
		for (Object parameter : parameters) {
			nativeQuery.setParameter(position, parameter);
			position++;
		}
		if (dataSize > 0) {
			nativeQuery.setMaxResults(dataSize);
		}
		nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return nativeQuery.getResultList();
	}

	public List nativeSqlSearchOneColumn(StringBuffer sql, List<Object> parameters) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql.toString());
		int position = 1;
		for (Object parameter : parameters) {
			nativeQuery.setParameter(position, parameter);
			position++;
		}
		return nativeQuery.getResultList();
	}
}
