package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.Tag;
import com.lmt.data.unstructured.repository.TagRepository;
import com.lmt.data.unstructured.service.TagService;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/11 16:09
 */
@Service("TagServiceImpl")
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Map save(Tag tag) {
        Tag exist = tagRepository.findByResourceId(tag.getResourceId());
        if (null != exist){
            return ResultData.newError("该系统资源已有标签记录");
        }
        this.tagRepository.save(tag);
        if (null == tag.getId()){
            return ResultData.newError("系统资源标签保存失败");
        }
        return ResultData.newOK("系统资源标签保存成功");
    }
}
