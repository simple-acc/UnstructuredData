package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.Resource;
import com.lmt.data.unstructured.repository.ResourceRepository;
import com.lmt.data.unstructured.service.ResourceService;
import com.lmt.data.unstructured.util.FileUtil;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/11 11:26
 */
@Service("ResourceServiceImpl")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private FileUtil fileUtil;

    @Override
    public Map save(Resource resource) {
        if (!fileUtil.existResourceFile(resource.getResourceFileName())){
            return ResultData.newError("该资源文件不存在");
        }
        Resource exist = this.resourceRepository.findByResourceFileName(resource.getResourceFileName());
        if (null != exist){
            return ResultData.newError("该资源文件 ["+resource.getResourceFileName()+"] 在系统资源中已存在");
        }
        this.resourceRepository.save(resource);
        if (null == resource.getId()){
            return ResultData.newError("系统资源信息保存失败");
        }
        return ResultData.newOK("系统资源信息保存成功", resource.getId());
    }
}
