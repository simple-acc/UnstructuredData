package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.*;
import com.lmt.data.unstructured.entity.search.AuditSearch;
import com.lmt.data.unstructured.repository.AuditRepository;
import com.lmt.data.unstructured.service.AuditService;
import com.lmt.data.unstructured.service.ResourceService;
import com.lmt.data.unstructured.service.TagService;
import com.lmt.data.unstructured.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/10 20:51
 */
@Service("AuditServiceImpl")
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EntityManagerQuery entityManagerQuery;

    @Override
    public Map save(Audit audit) {
        Audit exist = this.auditRepository.findByObjIdAndOperation(audit.getObjId(), audit.getOperation());
        if (null != exist){
            return ResultData.newError("该资源的审核操作记录已存在");
        }
        this.auditRepository.save(audit);
        if (null == audit.getId()){
            return ResultData.newError("审核数据保存失败");
        }
        return ResultData.newOK("审核数据保存成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map update(Audit audit) {
        List<ResourceTemp> resourceTemps = audit.getResourceTemps();
        // 处理审核记录信息
        for (ResourceTemp resourceTemp : resourceTemps) {
            Audit exist = this.auditRepository.findByObjIdAndStatusEquals(
                    resourceTemp.getId(), UdConstant.AUDIT_STATUS_WAIT);
            if (null == exist){
                return ResultData.newError("该待审核资源 [ID = " + resourceTemp.getId() + "] 的待审核记录不存在");
            }
            Audit updateAudit = new Audit();
            updateAudit.setStatus(audit.getStatus());
            updateAudit.setRemark(audit.getRemark());
            updateAudit.setAuditorId(redisCache.getUserId(audit));
            BeanUtils.copyProperties(updateAudit, exist, EntityUtils.getNullPropertyNames(updateAudit));
            entityManager.merge(exist);
            this.auditRepository.save(exist);
        }
        // 审核通过，处理资源信息
        if (UdConstant.AUDIT_STATUS_PASS.equals(audit.getStatus())){
            Map result = this.handleSystemResource(audit.getResourceTemps());
            if (Integer.valueOf(result.get(UdConstant.RESULT_CODE).toString())
                    != UdConstant.RESULT_CORRECT_CODE) {
                return result;
            }
        }
        return ResultData.newOK("审核成功");
    }

    @Override
    public Map search(AuditSearch auditSearch) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.remark, ");
        sql.append("a.audit_time AS auditTime, ");
        sql.append("a.create_time AS createTime, ");
        sql.append("rt.designation AS resourceTempDesignation, ");
        sql.append("(SELECT ui.user_name FROM user_info AS ui WHERE ui.id = a.auditor_id) ");
        sql.append("AS auditor, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = a.operation) ");
        sql.append("AS operation, ");
        sql.append("(SELECT dd.designation FROM digital_dictionary AS dd WHERE dd.code = a.status) ");
        sql.append("AS status ");
        sql.append("FROM audit AS a, resource_temp AS rt WHERE rt.id = a.obj_id ");
        if (!StringUtils.isEmpty(auditSearch.getKeyword())){
            sql.append("AND (a.remark LIKE ? OR rt.designation LIKE ? ) ");
            auditSearch.setParamsCount(2);
        }
        Map result = this.entityManagerQuery.paginationSearch("audit", sql, auditSearch);
        return ResultData.newOK("审核记录查询成功", result);
    }

    /**
     * @apiNote 处理资源表
     * @param resourceTemps 待审核资源数据
     * @return Map
     */
    private Map handleSystemResource(List<ResourceTemp> resourceTemps) {
        for (ResourceTemp resourceTemp : resourceTemps) {
            Map result = null;
            if (UdConstant.AUDIT_OPERATION_ADD.equals(resourceTemp.getOperationCode())){
                // 新增资源通过审核
                result = this.resourceService.addResourceFromResourceTemp(resourceTemp);
            }
            if (UdConstant.AUDIT_OPERATION_DELETE.equals(resourceTemp.getOperationCode())){
                // 删除资源通过审核
                result = this.deleteSystemResource(resourceTemp);
            }
            assert null != result;
            assert null != result.get(UdConstant.RESULT_CODE);
            if (!CheckResult.isOK(result)){
                return result;
            }
        }
        return ResultData.newOK("资源数据处理成功");
    }

    /**
     * @apiNote 删除资源
     * @param resourceTemp 要删除的资源对应的待审核资源信息
     * @return Map
     * TODO 删除资源
     */
    private Map deleteSystemResource(ResourceTemp resourceTemp) {
        return null;
    }

}
