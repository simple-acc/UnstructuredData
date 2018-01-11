package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.*;
import com.lmt.data.unstructured.repository.AuditRepository;
import com.lmt.data.unstructured.repository.ResourceTempRepository;
import com.lmt.data.unstructured.repository.TagTempRepository;
import com.lmt.data.unstructured.service.AuditService;
import com.lmt.data.unstructured.service.ResourceService;
import com.lmt.data.unstructured.service.TagService;
import com.lmt.data.unstructured.util.EntityUtils;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import com.lmt.data.unstructured.util.UdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TagTempRepository tagTempRepository;

    @Autowired
    private ResourceTempRepository resourceTempRepository;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TagService tagService;

    @Autowired
    private ResourceService resourceService;

    private Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);

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
            updateAudit.setAuditor(redisCache.getUserId(audit));
            BeanUtils.copyProperties(updateAudit, exist, EntityUtils.getNullPropertyNames(updateAudit));
            entityManager.merge(exist);
            this.auditRepository.save(exist);
        }
        logger.info("开始将待审核资源信息复制到系统资源表");
        if (UdConstant.AUDIT_STATUS_PASS.equals(audit.getStatus())){
            Map result = this.copyToSystemResource(audit.getResourceTemps());
            if (Integer.valueOf(result.get(UdConstant.RESULT_CODE).toString())
                    != UdConstant.RESULT_CORRECT_CODE){
                return result;
            }
        }
        logger.info("待审核资源信息复制结束");
        return ResultData.newOK("审核成功");
    }

    /**
     * @apiNote 将待审核的资源数据复制到系统资源表
     * @param resourceTemps 待审核资源数据
     * @return Map
     */
    private Map copyToSystemResource(List<ResourceTemp> resourceTemps) {
        for (ResourceTemp resourceTemp : resourceTemps) {
            logger.info("开始将待审核资源 [ID="+resourceTemp.getId()+"]信息复制到系统资源表");
            ResourceTemp exist = this.resourceTempRepository.findOne(resourceTemp.getId());
            if (null == exist){
                return ResultData.newError("审核的资源 [ID="+resourceTemp.getId()+"] 不存在");
            }
            Resource resource = new Resource();
            BeanUtils.copyProperties(exist, resource, EntityUtils.getNullPropertyNames(exist));
            String expandedName = exist.getDesignation().substring(exist.getDesignation().lastIndexOf("."));
            String resourceFileName = exist.getId() + expandedName;
            resource.setResourceFileName(resourceFileName);
            resource.setId(null);
            Map result = this.resourceService.save(resource);
            if (Integer.valueOf(result.get(UdConstant.RESULT_CODE).toString())
                    != UdConstant.RESULT_CORRECT_CODE){
                return result;
            }
            this.copyToSystemResourceTags(resourceTemp.getId(), result.get(UdConstant.RESULT_DATA).toString());
            logger.info("待审核资源 [ID=" + resourceTemp.getId() +"] 信息复制结束");
        }
        return ResultData.newOK("系统资源数据复制成功");
    }

    /**
     * @apiNote 将待审核资源的标签复制到系统资源标签表
     * @param resourceTempId 待审核资源ID
     * @param resourceId 资源ID
     */
    private void copyToSystemResourceTags(String resourceTempId, String resourceId) {
        TagTemp exist = this.tagTempRepository.findByResourceTempId(resourceTempId);
        if (null == exist){
            return;
        }
        Tag tag = new Tag();
        BeanUtils.copyProperties(exist, tag, EntityUtils.getNullPropertyNames(exist));
        tag.setResourceId(resourceId);
        Map result = this.tagService.save(tag);
        if (Integer.valueOf(result.get(UdConstant.RESULT_CODE).toString())
                != UdConstant.RESULT_CORRECT_CODE){
            logger.error("系统资源 [ID="+resourceId+"] 标签保存失败："
                    + result.get(UdConstant.RESULT_MSG));
        }
    }

}
