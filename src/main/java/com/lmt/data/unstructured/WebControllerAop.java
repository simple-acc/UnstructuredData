package com.lmt.data.unstructured;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lmt.data.unstructured.base.BaseEntity;
import com.lmt.data.unstructured.base.BaseSearch;
import com.lmt.data.unstructured.util.UdConstant;

import java.io.IOException;

/**
 * @author MT-Lin
 * @date 2018/1/3 13:32
 */
@Aspect
@Component
public class WebControllerAop {

	private final static Logger logger = LoggerFactory.getLogger(WebControllerAop.class);

	@Pointcut("execution(public * com.lmt.data.unstructured.api.*.*(..))")
	public void controllerPointCut() {
	}

	@Before("controllerPointCut()")
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// TODO 在请求参数中添加tokenId
		HttpSession session = request.getSession();
		Object tokenId = session.getAttribute(UdConstant.USER_LOGIN_EVIDENCE);
		// URL
		String url = request.getRequestURI();
		logger.info("url = {}", url);
		if (null == tokenId) {
			if ("/DigitalDictionaryApi/getChildrenForSelect".equals(url)
                    || "/UserInfoApi/login".equals(url)){
                logger.error("tokenId is NULL，用户还未登录");
			}
		} else {
			logger.info("tokenId is " + tokenId);
		}
		if (null != tokenId && joinPoint.getArgs().length > 0) {
			Object o = joinPoint.getArgs()[0];
			if (o instanceof BaseSearch) {
				((BaseSearch) o).setTokenId(tokenId.toString());
			} else if (o instanceof BaseEntity) {
				((BaseEntity) o).setTokenId(tokenId.toString());
			}
		}

		// 请求方法
		logger.info("request method = {}", request.getMethod());
		// 类
		logger.info("class = {}", joinPoint.getSignature().getDeclaringTypeName());
		// 方法
		logger.info("method name = {}", joinPoint.getSignature().getName());
		// 参数
		logger.info("parameters = {}", joinPoint.getArgs());
	}

	@After("controllerPointCut()")
	public void doAfter() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		logger.info("url = {} end of execution", request.getRequestURL());
	}

	@AfterReturning(returning = "object", pointcut = "controllerPointCut()")
	public void doAfterReturn(Object object) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String url = request.getRequestURL().toString();
		if (null == object) {
			if (!url.endsWith(UdConstant.DOWNLOAD_FILE_URL)) {
				logger.error("The data returned NULL");
			}
		} else {
			logger.info("response = {}", object.toString());
		}
	}
}
