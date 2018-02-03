package com.lmt.data.unstructured.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmt.data.unstructured.entity.TagTemp;
import com.lmt.data.unstructured.repository.TagTempRepository;
import com.lmt.data.unstructured.service.TagTempService;
import com.lmt.data.unstructured.util.ResultData;

/**
 * @author MT-Lin
 * @date 2018/1/10 12:23
 */
@Service("TagTempServiceImpl")
@SuppressWarnings({ "rawtypes" })
public class TagTempServiceImpl implements TagTempService {

	@Autowired
	private TagTempRepository tagTempRepository;

	@Override
	public Map save(TagTemp tagTemp) {
		TagTemp exist = this.tagTempRepository.findByResourceTempId(tagTemp.getId());
		if (null != exist) {
			return ResultData.newError("该资源已有标签存在");
		}
		this.tagTempRepository.save(tagTemp);
		if (null == tagTemp.getId()) {
			return ResultData.newError("待审核资源标签保存失败");
		}
		return ResultData.newOK("待审核资源标签保存成功");
	}
}
