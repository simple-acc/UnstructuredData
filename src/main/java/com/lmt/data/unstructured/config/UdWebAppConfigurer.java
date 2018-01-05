package com.lmt.data.unstructured.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author MT-Lin
 * @date 2018/1/5 20:35
 */
//@Configuration
public class UdWebAppConfigurer extends WebMvcConfigurerAdapter{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenIdInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
