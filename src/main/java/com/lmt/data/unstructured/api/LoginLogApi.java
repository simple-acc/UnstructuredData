package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.search.LoginLogSearch;
import com.lmt.data.unstructured.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/11 23:14
 */
@RestController
@RequestMapping("/LoginLogApi")
public class LoginLogApi {

    @Autowired
    private LoginLogService loginLogService;

    @RequestMapping("/search")
    public Map search(@RequestBody LoginLogSearch loginLogSearch){
        return this.loginLogService.search(loginLogSearch);
    }
}
