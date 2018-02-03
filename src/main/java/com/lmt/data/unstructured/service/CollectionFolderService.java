package com.lmt.data.unstructured.service;

import java.util.List;
import java.util.Map;

import com.lmt.data.unstructured.entity.CollectionFolder;
import com.lmt.data.unstructured.entity.search.CollectionFolderSearch;

/**
 * @author MT-Lin
 * @date 2018/1/26 14:23
 */
@SuppressWarnings({ "rawtypes" })
public interface CollectionFolderService {

	/**
	 * @apiNote 新增收藏夹
	 * @param collectionFolder
	 *            收藏夹
	 * @return Map
	 */
	Map save(CollectionFolder collectionFolder);

	/**
	 * @apiNote 搜索收藏夹
	 * @param collectionFolderSearch
	 *            搜索条件
	 * @return Map
	 */
	Map search(CollectionFolderSearch collectionFolderSearch);

	/**
	 * @apiNote 获取所属收藏夹选项
	 * @return Map
	 */
	Map getParentTree();

	/**
	 * @apiNote 批量删除收藏夹
	 * @param collectionFolders
	 *            要删除的收藏夹
	 * @return Map
	 */
	Map delete(List<CollectionFolder> collectionFolders);

	/**
	 * @apiNote 修改收藏夹
	 * @param collectionFolder
	 *            修改的收藏夹
	 * @return Map
	 */
	Map update(CollectionFolder collectionFolder);
}
