package com.lmt.data.unstructured.util;

import com.lmt.data.unstructured.base.BaseSearch;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/7 18:11
 */
@Component
public class EntityManagerQuery {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection"})
    public Map<String, Object> paginationSearch(String tableName, StringBuffer sql, BaseSearch baseSearch){
        String keyword = baseSearch.getKeyword();
        int currentPage = baseSearch.getCurrentPage() - 1;
        int pageSize = baseSearch.getPageSize();
        // 获取表中数据的数量
        String countSql = "select count(id) as totalElements from " + tableName;
        Query countQuery = entityManager.createNativeQuery(countSql);
        Object totalElements = countQuery.getResultList().get(0);
        // 开始查询，拼接SQL查询语句
        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        for (int i = 0; i < baseSearch.getParamsCount(); i++) {
            nativeQuery.setParameter(i + 1, keyword);
        }
        nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        nativeQuery.setFirstResult(currentPage * pageSize);
        nativeQuery.setMaxResults(pageSize);
        List resultList = nativeQuery.getResultList();
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put(UdConstant.TOTAL_ELEMENTS, totalElements);
        resultMap.put(UdConstant.CONTENT, resultList);
        return resultMap;
    }
}
