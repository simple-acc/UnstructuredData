package com.lmt.data.unstructured.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmt.data.unstructured.entity.CollectionFolder;
import com.lmt.data.unstructured.entity.search.CollectionFolderSearch;
import com.lmt.data.unstructured.service.CollectionFolderService;
import com.lmt.data.unstructured.util.RedisCache;

/**
 * @author MT-Lin
 * @date 2018/1/26 14:21
 */
@RestController
@RequestMapping("/CollectionFolderApi")
@SuppressWarnings("rawtypes")
public class CollectionFolderApi {

	@Autowired
	private CollectionFolderService collectionFolderService;

	@Autowired
	private RedisCache redisCache;

	@RequestMapping("/save")
	public Map save(@RequestBody CollectionFolder collectionFolder) {
		collectionFolder.setCreator(redisCache.getUserId(collectionFolder));
		return this.collectionFolderService.save(collectionFolder);
	}

	@RequestMapping("/search")
	public Map search(@RequestBody CollectionFolderSearch collectionFolderSearch) {
		return this.collectionFolderService.search(collectionFolderSearch);
	}

	@RequestMapping("/getParentTree")
	public Map getParentTree() {
		return this.collectionFolderService.getParentTree();
	}

	@RequestMapping("/delete")
	public Map delete(@RequestBody List<CollectionFolder> collectionFolders) {
		return this.collectionFolderService.delete(collectionFolders);
	}

	@RequestMapping("/update")
	public Map update(@RequestBody CollectionFolder collectionFolder) {
		return this.collectionFolderService.update(collectionFolder);
	}
}
