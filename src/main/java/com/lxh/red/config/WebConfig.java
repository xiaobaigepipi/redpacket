package com.lxh.red.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

/*
 * @PackageName: com.lxh.red.config
 * @ClassName: WebConfig
 * @Description:
 * @author: 辉
 * @date: 2019/12/29 0:44
 * */
@Configuration
//定义spring mvc扫描的包
@ComponentScan(value = "com.*", includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
//启动spring mvc配置
@EnableWebMvc
public class WebConfig {

    /*
     * @Author 辉
     * @Description //TODO 实现视图解析器
     * @Date 0:51 2019/12/29
     * @Param []
     * @return org.springframework.web.servlet.ViewResolver
     **/
    @Bean(name = "internalResourceViewResolver")
    public ViewResolver initViewResolver() {
        InternalResourceViewResolver irv = new InternalResourceViewResolver();
        irv.setPrefix("/WEB-INF/jsp/");
        irv.setSuffix(".jsp");
        return irv;
    }

    /*
     * @Author 辉
     * @Description //TODO json转换器
     * @Date 0:56 2019/12/29
     * @Param []
     * @return org.springframework.web.servlet.HandlerAdapter
     **/
    @Bean(name = "requestMappingHandlerAdapter")
    public HandlerAdapter initAdapter() {
        RequestMappingHandlerAdapter rma = new RequestMappingHandlerAdapter();
        //http json转换器 接收JSON类型的转换
        MappingJackson2HttpMessageConverter json = new MappingJackson2HttpMessageConverter();
        MediaType media = MediaType.APPLICATION_JSON;
        List<MediaType> ms = new ArrayList<>();
        ms.add(media);
        //加入转换器的支持类型
        json.setSupportedMediaTypes(ms);
        //往适配器加入json转换器
        rma.getMessageConverters().add(json);
        return rma;
    }

}
