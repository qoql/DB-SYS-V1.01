package com.cy.pj.common.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

/**
 * @Configuration：该注解描述类为一个配置对象，此对象会交给spring管理
 */
@Configuration
public class SpringShiroConfig {
    @Bean
    public DefaultWebSessionManager newSessionManger(){
        DefaultWebSessionManager sManager = new DefaultWebSessionManager();
        sManager.setGlobalSessionTimeout(60*60*1000);
        return sManager;
    }
    /**
     * cookie
     * @return
     */
    @Bean
    public CookieRememberMeManager newRememberMeManager(){
        CookieRememberMeManager cManager = new CookieRememberMeManager();
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setMaxAge(24*7*60*60);
        cManager.setCookie(cookie);
        return cManager;
    }

    /**
     * 缓存
     */
    @Bean
    public CacheManager newCacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    /**
     * SecurityManager：shiro框架的核心管理对象，提供认证和授权操作
     * @Bean：用于整合第三方bean资源时使用的注解方法，被该注解描述的方法，
     * 其返回值会交给spring管理，返回值一般为对象
     * @return
     */
    /**shiro的核心*/
    @Bean("sManger")
    public SecurityManager newSecurityManager(@Autowired Realm realm,
                                              @Autowired CacheManager cacheManager){
        DefaultWebSecurityManager sManger = new DefaultWebSecurityManager();
        sManger.setRealm(realm);
        //关联缓存
        sManger.setCacheManager(cacheManager);
        //关联cooike
        sManger.setRememberMeManager(newRememberMeManager());
        //关联session
        sManger.setSessionManager(newSessionManger());
        return sManger;
    }

    @Bean("shiroFilterFactory")
    public ShiroFilterFactoryBean newShiroFilterFactoryBean(@Autowired SecurityManager sManger){
        //构建ShiroFilter工厂对象
        ShiroFilterFactoryBean sffBean = new ShiroFilterFactoryBean();
        //关联securityManger对象
        sffBean.setSecurityManager(sManger);
        //定义未认证时请求重新访问认证界面（也就是登录界面）
        sffBean.setLoginUrl("/doLoginUI");
        //定义map指定过滤规则（哪些资源运行匿名访问，哪些资源需要认证后才能访问）
        //为什么选择LinkedHashMap：它是会记录顺序的map，选择它我们能先指定哪些资源运行匿名访问，后指定哪些资源需要认证后才能访问
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        //我们设置运行所有静态资源被匿名访问，除此之外都需要认证访问
        //“anon"表示运行匿名访问
        //"authc"表示需要认证才能访问
        map.put("/build/**","anon");
        map.put("/dist/**","anon");
        map.put("/plugins/**","anon");
        //登录操作是匿名的
        map.put("/user/doLogin","anon");
        //退出登录操作是shiro提供的
        map.put("/doLogout", "logout");
        //"authc"表示需要认证才能访问
        //map.put("/**","authc");
        // /** = user，当登入操作时不做检查
        map.put("/**","user");
        //过滤器工厂会根据传入的map创建多个过滤器
        sffBean.setFilterChainDefinitionMap(map);
        return sffBean;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor
    newLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    @DependsOn("lifecycleBeanPostProcessor")
    @Bean
    public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor
    newAuthorizationAttributeSourceAdvisor(
            @Autowired SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor=
                new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
