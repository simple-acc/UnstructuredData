package com.lmt.data.unstructured.api;

import com.lmt.data.unstructured.entity.Audit;
import com.lmt.data.unstructured.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author MT-Lin
 * @date 2018/1/10 22:13
 */
@RestController
@RequestMapping("/AuditApi")
public class AuditApi {

    @Autowired
    private AuditService auditService;

    @RequestMapping("/update")
    public Map update(@RequestBody Audit audit){
        return this.auditService.update(audit);
    }
}
