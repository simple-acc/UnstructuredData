package com.lmt.data.unstructured.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @apiNote 拦截器
 * @author MT-Lin
 * @date 2018/1/5 20:29
 */

public class TokenIdInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        InputStream is = httpServletRequest.getInputStream ();
        StringBuilder responseStrBuilder = new StringBuilder ();
        BufferedReader streamReader = new BufferedReader (new InputStreamReader(is,"UTF-8"));
        String inputStr;
        while ((inputStr = streamReader.readLine ()) != null) {
            responseStrBuilder.append(inputStr);
        }
        String parameter = responseStrBuilder.toString();

        System.err.println(parameter);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
}
