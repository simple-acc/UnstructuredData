package com.lmt.data.unstructured.service.impl;

import com.lmt.data.unstructured.entity.DigitalDictionary;
import com.lmt.data.unstructured.entity.search.DigitalDictionarySearch;
import com.lmt.data.unstructured.repository.DigitalDictionaryRepository;
import com.lmt.data.unstructured.service.DigitalDictionaryService;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/3 16:44
 */
@Service("DigitalDictionaryServiceImpl")
public class DigitalDictionaryServiceImpl implements DigitalDictionaryService{

    @Resource
    private DigitalDictionaryRepository digitalDictionaryRepository;

    @Override
    public Map save(DigitalDictionary digitalDictionary) {
        if (null != this.digitalDictionaryRepository.findByCode(digitalDictionary.getCode())){
            return ResultData.newError("该编码已经存在：" + digitalDictionary.getCode()).toMap();
        }
        this.digitalDictionaryRepository.save(digitalDictionary);
        if (null != digitalDictionary.getId()){
            return ResultData.newOK("添加数据字典成功").toMap();
        } else {
            return ResultData.newError("添加数据字典失败").toMap();
        }
    }

    @Override
    public Map findAll(DigitalDictionarySearch digitalDictionarySearch) {
        int currentPage = digitalDictionarySearch.getCurrentPage() - 1;
        int pageSize = digitalDictionarySearch.getPageSize();
//        Order idOrder = new Order(Sort.Direction.ASC, "id");
//        List<Order> orders = new ArrayList<>();
//        Sort sort = new Sort(orders);
//        PageRequest pageRequest = new PageRequest(currentPage, pageSize, sort);
        PageRequest pageRequest = new PageRequest(currentPage, pageSize);
        Page<DigitalDictionary> page = this.digitalDictionaryRepository.findAll(pageRequest);
        return ResultData.newOk("查询成功", page).toMap();
    }

    @Override
    public Map findOneById(String id) {
        DigitalDictionary result = this.digitalDictionaryRepository.findOne(id);
        if (null == result){
            return ResultData.newError("该数据字典不存在").toMap();
        } else {
            return ResultData.newOk("数据字典获取成功", result).toMap();
        }
    }

    @Override
    public Map update(DigitalDictionary digitalDictionary) {
        DigitalDictionary old = this.digitalDictionaryRepository.findOne(digitalDictionary.getId());
        if (null == old){
            return ResultData.newError("修改的数据字典不存在").toMap();
        }
        this.digitalDictionaryRepository.save(digitalDictionary);
        return ResultData.newOK("修改成功").toMap();
    }

    @Override
    public Map findChildrenForTree(String parentCode) {
        List result;
        if (null == parentCode){
            result = this.digitalDictionaryRepository.findByParentCodeIsNull();
            return ResultData.newOk("查询成功", result).toMap();
        }
        result = this.digitalDictionaryRepository.findByParentCode(parentCode);
        return ResultData.newOk("查询成功", result).toMap();
    }

    @Override
    public Map search(DigitalDictionarySearch digitalDictionarySearch) {
        String keyword = digitalDictionarySearch.getKeyword();
        int currentPage = digitalDictionarySearch.getCurrentPage() - 1;
        int pageSize = digitalDictionarySearch.getPageSize();
//        Order idOrder = new Order(Sort.Direction.ASC, "id");
//        List<Order> orders = new ArrayList<>();
//        Sort sort = new Sort(orders);
//        PageRequest pageRequest = new PageRequest(currentPage, pageSize, sort);
        PageRequest pageRequest = new PageRequest(currentPage, pageSize);
        if (null != keyword){
            Page result = this.digitalDictionaryRepository.findByCodeLikeOrDescriptionLikeOrDesignationLikeOrCreatorLikeOrderByCodeAsc
                    (keyword, keyword, keyword, keyword, pageRequest);
            return ResultData.newOk("查询成功", result).toMap();
        }
        return this.findAll(digitalDictionarySearch);
    }

    @Override
    public Map delete(List<DigitalDictionary> digitalDictionaries) {
        this.digitalDictionaryRepository.deleteInBatch(digitalDictionaries);
        return ResultData.newOK("删除成功").toMap();
    }
}
