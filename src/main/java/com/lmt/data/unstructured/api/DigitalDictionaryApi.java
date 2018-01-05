package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.DigitalDictionary;
import com.lmt.data.unstructured.entity.search.DigitalDictionarySearch;
import com.lmt.data.unstructured.service.DigitalDictionaryService;
import com.lmt.data.unstructured.util.RedisCache;
import com.lmt.data.unstructured.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/3 9:11
 */
@RestController
@RequestMapping("/DigitalDictionaryApi")
public class DigitalDictionaryApi {

    @Autowired
    private DigitalDictionaryService digitalDictionaryService;

    /**
     * TODO 从缓存中获取当前登陆人信息的功能已完成
     * @apiNote 保存数据字典
     * @param digitalDictionary 保存的数据字典
     * @return Map
     */
    @RequestMapping("/save")
    public Map save(@RequestBody DigitalDictionary digitalDictionary){
        digitalDictionary.setCreator(RedisCache.getUserName(digitalDictionary));
        return this.digitalDictionaryService.save(digitalDictionary);
    }

    @RequestMapping("/findOneById")
    public Map findOneById(@RequestBody DigitalDictionarySearch digitalDictionarySearch){
        String id = digitalDictionarySearch.getId();
        if (null == id){
            return ResultData.newError("传入的数据字典ID为空").toMap();
        }
        return this.digitalDictionaryService.findOneById(id);
    }

    @RequestMapping("/update")
    public Map update(@RequestBody DigitalDictionary digitalDictionary){
        return this.digitalDictionaryService.update(digitalDictionary);
    }

    @RequestMapping("/search")
    public Map search(@RequestBody DigitalDictionarySearch digitalDictionarySearch){
        return this.digitalDictionaryService.search(digitalDictionarySearch);
    }

    @RequestMapping("/findChildrenForTree")
    public Map findChildrenForTree(@RequestBody DigitalDictionarySearch digitalDictionarySearch){
        return this.digitalDictionaryService.findChildrenForTree(digitalDictionarySearch.getParentCode());
    }

    @RequestMapping("/delete")
    public Map delete(@RequestBody List<DigitalDictionary> digitalDictionaries){
        return this.digitalDictionaryService.delete(digitalDictionaries);
    }
}
