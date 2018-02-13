package com.lmt.data.unstructured.api;

import java.util.Map;

import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lmt.data.unstructured.entity.search.ResourceSearch;
import com.lmt.data.unstructured.service.ResourceService;

/**
 * @author MT-Lin
 * @date 2018/1/13 9:24
 */
@RestController
@RequestMapping("/ResourceApi")
@SuppressWarnings("rawtypes")
public class ResourceApi {

	@Autowired
	private ResourceService resourceService;

	@RequestMapping("/getAuthorInfo")
	public Map getAuthorInfo(@RequestBody ResourceSearch resourceSearch){
		return this.resourceService.getAuthorInfo(resourceSearch);
	}

	@RequestMapping("/getResourceDetail")
	public Map getResourceDetail(@RequestBody ResourceSearch resourceSearch){
		if (StringUtils.isEmpty(resourceSearch.getId())){
			return ResultData.newError("传入的资源ID为空，无法获取资源详细信息。");
		}
		return this.resourceService.getResourceDetail(resourceSearch.getId());
	}

	@RequestMapping("/getTopFiveByDissertation")
	public Map getTopFiveByDissertation() {
		return this.resourceService.getTopFiveByDissertation();
	}

	@RequestMapping("/search")
	public Map search(@RequestBody ResourceSearch resourceSearch) {
		return this.resourceService.search(resourceSearch);
	}

	@RequestMapping("/modifyDissertation")
	public Map modifyDissertation(@RequestBody ResourceSearch resourceSearch) {
		return this.resourceService.modifyDissertation(resourceSearch);
	}
}
