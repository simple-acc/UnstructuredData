package com.lmt.data.unstructured.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmt.data.unstructured.entity.Dissertation;
import com.lmt.data.unstructured.entity.search.DissertationSearch;
import com.lmt.data.unstructured.service.DissertationService;
import com.lmt.data.unstructured.util.ResultData;

/**
 * @author MT-Lin
 * @date 2018/1/8 16:26
 */
@RestController
@RequestMapping("/DissertationApi")
@SuppressWarnings("rawtypes")
public class DissertationApi {

	@Autowired
	private DissertationService dissertationService;

	@RequestMapping("/save")
	public Map save(@RequestBody Dissertation dissertation) {
		return this.dissertationService.save(dissertation);
	}

	@RequestMapping("/search")
	public Map search(@RequestBody DissertationSearch dissertationSearch) {
		return this.dissertationService.search(dissertationSearch);
	}

	@RequestMapping("/findOneById")
	public Map findOneById(@RequestBody DissertationSearch dissertationSearch) {
		String id = dissertationSearch.getId();
		if (null == id) {
			return ResultData.newError("传入的专题ID为空");
		}
		return this.dissertationService.findOneById(id);
	}

	@RequestMapping("/delete")
	public Map delete(@RequestBody List<Dissertation> dissertations) {
		return this.dissertationService.delete(dissertations);
	}

	@RequestMapping("/update")
	public Map update(@RequestBody Dissertation dissertation) {
		return this.dissertationService.update(dissertation);
	}

	@RequestMapping("/getParentTree")
	public Map getParentTree() {
		return this.dissertationService.getParentTree();
	}
}
