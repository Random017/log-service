package com.gudsen.moza.log.config;

import com.gudsen.moza.webutil.interceptor.CrosInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ruanmingcong 2018/9/26 21:03
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 跨域过滤器
        registry.addInterceptor(new CrosInterceptor()).order(1);
    }
}