package com.lmt.data.unstructured.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmt.data.unstructured.entity.ResourceDownload;
import com.lmt.data.unstructured.entity.search.ResourceDownloadSearch;
import com.lmt.data.unstructured.service.ResourceDownloadService;
import com.lmt.data.unstructured.util.RedisCache;

/**
 * @author MT-Lin
 * @date 2018/1/27 21:38
 */
@RestController
@RequestMapping("/ResourceDownloadApi")
@SuppressWarnings("rawtypes")
public class ResourceDownloadApi {

	@Autowired
	private ResourceDownloadService resourceDownloadService;

	@Autowired
	private RedisCache redisCache;

	@RequestMapping("/save")
	public Map save(@RequestBody ResourceDownload resourceDownload) {
		resourceDownload.setUserId(redisCache.getUserId(resourceDownload));
		return this.resourceDownloadService.save(resourceDownload);
	}

	@RequestMapping("/search")
	public Map search(@RequestBody ResourceDownloadSearch resourceDownloadSearch) {
		resourceDownloadSearch.setUserId(redisCache.getUserId(resourceDownloadSearch));
		return this.resourceDownloadService.search(resourceDownloadSearch);
	}

}
