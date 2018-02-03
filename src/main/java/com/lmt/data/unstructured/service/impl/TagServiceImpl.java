package com.lmt.data.unstructured.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmt.data.unstructured.entity.Tag;
import com.lmt.data.unstructured.entity.TagTemp;
import com.lmt.data.unstructured.repository.TagRepository;
import com.lmt.data.unstructured.repository.TagTempRepository;
import com.lmt.data.unstructured.service.TagService;
import com.lmt.data.unstructured.util.ResultData;

/**
 * @author MT-Lin
 * @date 2018/1/11 16:09
 */
@Service("TagServiceImpl")
@SuppressWarnings({ "rawtypes" })
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private TagTempRepository tagTempRepository;

	private Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

	@Override
	public Map save(Tag tag) {
		Tag exist = tagRepository.findByResourceId(tag.getResourceId());
		if (null != exist) {
			return ResultData.newError("该系统资源已有标签记录");
		}
		this.tagRepository.save(tag);
		if (null == tag.getId()) {
			return ResultData.newError("系统资源标签保存失败");
		}
		return ResultData.newOK("系统资源标签保存成功");
	}

	@Override
	public Map addTag(String resourceTempId, String resourceId) {
		TagTemp exist = this.tagTempRepository.findByResourceTempId(resourceTempId);
		if (null == exist) {
			logger.warn("待审核资源[ID={}]没有标签", resourceTempId);
			return ResultData.newOK("该资源[ID=" + resourceTempId + "]没有标签");
		}
		Tag tag = new Tag();
		tag.setTag(exist.getTag());
		tag.setCreateTime(exist.getCreateTime());
		tag.setResourceId(resourceId);
		return this.save(tag);
	}
}
