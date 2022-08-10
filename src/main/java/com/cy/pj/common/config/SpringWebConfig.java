package com.cy.pj.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;

@Configuration
public class SpringWebConfig {
    //注册过滤器
    @Bean
    public FilterRegistrationBean<Filter> newFilterRegistrationBean(){
        //1.构建过滤器注册器
        FilterRegistrationBean<Filter> fBean = new FilterRegistrationBean<>();
        //2.创建过滤器对象，shiroFilterFactory已经在SpringShiroConfig中配置过
        DelegatingFilterProxy filter = new DelegatingFilterProxy("shiroFilterFactory");
        //3.将过滤器注册到注册器中
        fBean.setFilter(filter);
        //4.配置过滤器映射路径
        fBean.addUrlPatterns("/*");
        return fBean;
    }
}
