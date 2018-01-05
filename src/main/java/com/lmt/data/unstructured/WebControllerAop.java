package com.lmt.data.unstructured;

import com.lmt.data.unstructured.base.BaseEntity;
import com.lmt.data.unstructured.base.BaseSearch;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author MT-Lin
 * @date 2018/1/3 13:32
 */
@Aspect
@Component
public class WebControllerAop {

    private final static Logger logger = LoggerFactory.getLogger(WebControllerAop.class);

    @Pointcut("execution(public * com.lmt.data.unstructured.api.*.*(..))")
    public void controllerPointCut(){}

    @Before("controllerPointCut()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // TODO 在请求参数中添加tokenId
        HttpSession session = request.getSession();
        Object tokenId = session.getAttribute("tokenId");
        // TODO url要过滤掉登录页面需要的连接请求（未完成）只有在登录页面需要的连接不需要tokenId其他的请求都要
        // URL
        logger.info("url = {}", request.getRequestURI());
        if (null != tokenId) {
            Object o = joinPoint.getArgs()[0];
            if (o instanceof BaseSearch) {
                ((BaseSearch) joinPoint.getArgs()[0]).setTokenId(tokenId.toString());
            } else if (o instanceof BaseEntity) {
                ((BaseEntity) joinPoint.getArgs()[0]).setTokenId(tokenId.toString());
            }
        }

        // 请求方法
        logger.info("request method = {}", request.getMethod());
        // 类
        logger.info("class = {}", joinPoint.getSignature().getDeclaringTypeName());
        // 方法
        logger.info("method name = {}", joinPoint.getSignature().getName());
        // 参数
        logger.info("parameters = {}",joinPoint.getArgs());
    }

    @After("controllerPointCut()")
    public void doAfter(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("url = {} end of execution", request.getRequestURL());
    }

    @AfterReturning(returning = "object", pointcut = "controllerPointCut()")
    public void doAfterReturn(Object object){
        if (null == object){
            logger.error("The data returned NULL");
        } else {
            logger.info("response = {}", object.toString());
        }
    }
}
