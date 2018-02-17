package com.lmt.data.unstructured.config;

import com.lmt.data.unstructured.base.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author MT-Lin
 * @date 2018/2/17 20:07
 */
@Configuration
public class LoginInterceptorConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/DigitalDictionaryApi/getChildrenForSelect",
                        "/UserInfoApi/login");
    }
}
