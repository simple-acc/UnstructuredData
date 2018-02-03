package com.lmt.data.unstructured.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmt.data.unstructured.entity.Audit;
import com.lmt.data.unstructured.entity.search.AuditSearch;
import com.lmt.data.unstructured.service.AuditService;

/**
 * @author MT-Lin
 * @date 2018/1/10 22:13
 */
@RestController
@RequestMapping("/AuditApi")
@SuppressWarnings("rawtypes")
public class AuditApi {

	@Autowired
	private AuditService auditService;

	@RequestMapping("/update")
	public Map update(@RequestBody Audit audit) {
		return this.auditService.update(audit);
	}

	@RequestMapping("/search")
	public Map search(@RequestBody AuditSearch auditSearch) {
		return this.auditService.search(auditSearch);
	}
}
