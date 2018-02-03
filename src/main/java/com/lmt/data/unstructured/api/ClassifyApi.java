package com.lmt.data.unstructured.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmt.data.unstructured.entity.Classify;
import com.lmt.data.unstructured.entity.search.ClassifySearch;
import com.lmt.data.unstructured.service.ClassifyService;
import com.lmt.data.unstructured.util.ResultData;

/**
 * @author MT-Lin
 * @date 2018/1/7 7:21
 */
@RestController
@RequestMapping("/ClassifyApi")
@SuppressWarnings("rawtypes")
public class ClassifyApi {

	@Autowired
	private ClassifyService classifyService;

	@RequestMapping("/save")
	public Map save(@RequestBody Classify classify) {
		return this.classifyService.save(classify);
	}

	@RequestMapping("/getParentTree")
	public Map getParentTree(@RequestBody ClassifySearch classifySearch) {
		return this.classifyService.getParentTree(classifySearch.getClassifyType());
	}

	@RequestMapping("/search")
	public Map search(@RequestBody ClassifySearch classifySearch) {
		return this.classifyService.search(classifySearch);
	}

	@RequestMapping("/findOneById")
	public Map findOneById(@RequestBody ClassifySearch classifySearch) {
		String id = classifySearch.getId();
		if (StringUtils.isEmpty(id)) {
			return ResultData.newError("传入的分类ID为空");
		}
		return this.classifyService.findOneById(id);
	}

	@RequestMapping("/update")
	public Map update(@RequestBody Classify classify) {
		String id = classify.getId();
		if (StringUtils.isEmpty(id)) {
			return ResultData.newError("传入的分类ID为空");
		}
		return this.classifyService.update(classify);
	}

	@RequestMapping("/delete")
	public Map delete(@RequestBody List<Classify> classifies) {
		return this.classifyService.delete(classifies);
	}

}
