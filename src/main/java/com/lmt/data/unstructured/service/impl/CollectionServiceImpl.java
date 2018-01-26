package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.Collection;
import com.lmt.data.unstructured.repository.CollectionRepository;
import com.lmt.data.unstructured.service.CollectionService;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/25 9:43
 */
@Service("CollectServiceImpl")
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Override
    public Map save(Collection collection) {
        Collection exist = collectionRepository.findByObjId(collection.getObjId());
        if (null != exist){
            return ResultData.newError("请勿重复收藏");
        }
        this.collectionRepository.save(collection);
        if (null == collection.getId()){
            return ResultData.newError("收藏失败，请反馈，谢谢！");
        }
        return ResultData.newOK("收藏成功");
    }

    @Override
    public boolean isCollected(String userId, String resourceId) {
        Collection exist = this.collectionRepository
                .findByObjIdAndAndCreator(resourceId, userId);
        return exist != null;
    }

    @Override
    public int getCollectNum(String userId) {
        return this.collectionRepository.countByCreator(userId);
    }
}
