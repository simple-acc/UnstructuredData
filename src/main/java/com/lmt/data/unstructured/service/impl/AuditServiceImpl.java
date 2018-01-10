package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.Audit;
import com.lmt.data.unstructured.entity.ResourceTemp;
import com.lmt.data.unstructured.repository.AuditRepository;
import com.lmt.data.unstructured.service.AuditService;
import com.lmt.data.unstructured.service.ResourceTempService;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import com.lmt.data.unstructured.util.UdConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author MT-Lin
 * @date 2018/1/10 20:51
 */
@Service("AuditServiceImpl")
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private ResourceTempService resourceTempService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private EntityManager entityManager;

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
        for (ResourceTemp resourceTemp : resourceTemps) {
            Audit exist = this.auditRepository.findByObjIdAndStatusEquals(
                    resourceTemp.getId(), UdConstant.AUDIT_STATUS_WAIT);
            if (null == exist){
                return ResultData.newError("该待审核资源 [ID = " + resourceTemp.getId() + "] 的待审核记录不存在");
            }
            Audit updateAudit = new Audit();
            updateAudit.setStatus(audit.getStatus());
            updateAudit.setRemark(audit.getRemark());
            updateAudit.setAuditor(redisCache.getUserId(audit.getTokenId()));
            BeanUtils.copyProperties(updateAudit, exist, getNullPropertyNames(updateAudit));
            entityManager.merge(exist);
            this.auditRepository.save(exist);
        }
        // 将数据复制到资源表
        return ResultData.newOK("审核成功");
    }

    private String[] getNullPropertyNames ( Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
