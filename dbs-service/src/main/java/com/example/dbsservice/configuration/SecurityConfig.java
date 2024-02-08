package com.example.dbsservice.configuration;

import com.example.dbsservice.jwt.SecurityFilter;
import com.example.dbsservice.validator.TokenValidator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig {
    @Resource
    private TokenValidator tokenValidator;

    @Resource
    private TokenConfig tokenConfig;

    @Bean
    public FilterRegistrationBean<SecurityFilter> filterFilterRegistrationBean() {
        FilterRegistrationBean<SecurityFilter> securityFilter =
                new FilterRegistrationBean<>();
        securityFilter.setFilter(new SecurityFilter(tokenValidator, tokenConfig));
        securityFilter.addUrlPatterns("/customer/*");
        securityFilter.addUrlPatterns("/customer/**");
        securityFilter.addUrlPatterns("/transaction/*");
        securityFilter.addUrlPatterns("/transaction/**");
        securityFilter.addUrlPatterns("/account/**");
        securityFilter.addUrlPatterns("/account/*");
        securityFilter.addUrlPatterns("/wallet/*");
        securityFilter.addUrlPatterns("/wallet/**");
        return securityFilter;
    }
}
