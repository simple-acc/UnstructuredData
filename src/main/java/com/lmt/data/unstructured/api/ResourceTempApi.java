package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.Audit;
import com.lmt.data.unstructured.entity.ResourceTemp;
import com.lmt.data.unstructured.entity.TagTemp;
import com.lmt.data.unstructured.entity.search.ResourceTempSearch;
import com.lmt.data.unstructured.service.AuditService;
import com.lmt.data.unstructured.service.ResourceTempService;
import com.lmt.data.unstructured.service.TagTempService;
import com.lmt.data.unstructured.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/9 12:48
 */
@RestController
@RequestMapping("/ResourceTempApi")
public class ResourceTempApi {

    private Logger logger = LoggerFactory.getLogger(ResourceTempApi.class);

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private ResourceTempService resourceTempService;

    @Autowired
    private TagTempService tagTempService;

    @Autowired
    private AuditService auditService;

    @RequestMapping("search")
    public Map search(@RequestBody ResourceTempSearch resourceTempSearch){
        return this.resourceTempService.search(resourceTempSearch);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @RequestMapping("/upload")
    public Map upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request){
        // 保存待审核的资源数据
        String resourceMd5 = fileUtil.saveFile(multipartFile);
        if (null == resourceMd5){
            return ResultData.newError("资源保存失败，请从重试或联系管理员");
        }
        Object saveResourceTempResult= this.saveResourceTemp(resourceMd5, request);
        if (saveResourceTempResult instanceof Map){
            return (Map) saveResourceTempResult;
        }
        String resourceTempId = (String) saveResourceTempResult;
        this.saveResourceTempTags(request, resourceTempId);
        Map saveAuditDataResult = this.saveAuditData(resourceTempId);
        if (Integer.valueOf(saveAuditDataResult.get(UdConstant.RESULT_CODE).toString())
                != UdConstant.RESULT_CORRECT_CODE){
            return saveAuditDataResult;
        }
        if (!fileUtil.renameFile(multipartFile, resourceTempId)){
            return ResultData.newError("文件重命名失败，请联系管理员");
        }
        return ResultData.newOK("上传资源成功");
    }

    /**
     * @apiNote 保存待审核数据
     * @param resourceTempId 待审核资源ID
     * @return Map
     */
    private Map saveAuditData(String resourceTempId) {
        Audit audit = new Audit();
        audit.setObjId(resourceTempId);
        audit.setOperation(UdConstant.OPERATION_ADD_RESOURCE);
        audit.setStatus(UdConstant.AUDIT_STATUS_WAIT);
        return this.auditService.save(audit);
    }

    /**
     * @apiNote 保存待审核资源的标签
     * @param request 包含标签参数的request
     * @param resourceTempId 待审核资源ID
     */
    private void saveResourceTempTags(HttpServletRequest request, String resourceTempId) {
        // 保存待审核资源的标签
        String tags = request.getParameter("tags");
        if (StringUtils.isEmpty(tags)){
            return;
        }
        if (!StringUtils.isEmpty(tags)){
            TagTemp tagTemp = new TagTemp();
            tagTemp.setResourceTempId(resourceTempId);
            tagTemp.setTag(tags);
            Map saveTagsResult = this.tagTempService.save(tagTemp);
            if (Integer.valueOf(saveTagsResult.get(UdConstant.RESULT_CODE).toString())
                    != UdConstant.RESULT_CORRECT_CODE){
                logger.error("待审核资源 [ id = " + resourceTempId
                        +" ] 标签保存失败：" + saveTagsResult.get(UdConstant.RESULT_MSG));
            }
        }
    }

    /**
     * @apiNote 保存待审核资源
     * @param resourceMd5 资源文件的MD5值
     * @param request 带有待审核资源数据的request
     * @return 保存成功返回待审核资源ID，保存失败返回Map
     */
    private Object saveResourceTemp(String resourceMd5, HttpServletRequest request) {
        ResourceTemp resourceTemp = new ResourceTemp();
        String tokenId = request.getSession().getAttribute(UdConstant.USER_LOGIN_EVIDENCE).toString();
        resourceTemp.setAuthorId(redisCache.getUserId(tokenId));
        resourceTemp.setMd5(resourceMd5);
        resourceTemp.setDesignation(request.getParameter("designation"));
        resourceTemp.setResourceType(request.getParameter("resourceType"));
        resourceTemp.setResourceSize(Double.parseDouble(request.getParameter("resourceSize")));
        String classifyId = request.getParameter("classifyId");
        if (!StringUtils.isEmpty(classifyId)){
            resourceTemp.setClassifyId(classifyId);
        }
        String description = request.getParameter("description");
        if (!StringUtils.isEmpty(description)){
            resourceTemp.setDescription(description);
        }
        Map result = this.resourceTempService.save(resourceTemp);
        if (Integer.valueOf(result.get(UdConstant.RESULT_CODE).toString()) != UdConstant.RESULT_CORRECT_CODE) {
            return result;
        }
        return result.get(UdConstant.RESULT_DATA).toString();
    }
}
