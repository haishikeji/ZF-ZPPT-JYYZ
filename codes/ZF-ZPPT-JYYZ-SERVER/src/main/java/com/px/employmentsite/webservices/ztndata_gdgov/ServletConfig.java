package com.px.employmentsite.webservices.ztndata_gdgov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Autowired
    private ProxyHandler proxyHandler;


    @Bean
    public ServletRegistrationBean getServletRegistrationBean() {
        //写请求前缀
        String format = String.format("/%s/*", "gdgovMapApi");
        ServletRegistrationBean bean = new ServletRegistrationBean(proxyHandler);
        bean.addUrlMappings(format);
        return bean;
    }


}
