package com.lxh.red.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/*
 * @PackageName: com.lxh.red.config
 * @ClassName: WebAppInitializer
 * @Description:
 * @author: 辉
 * @date: 2019/12/28 23:40
 * */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    //spring Ioc环境配置
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {RootConfig.class};
    }


    //DispatcherServlet 环境配置
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebConfig.class};
    }

    //拦截请求配置类
    @Override
    protected String[] getServletMappings() {
        return new String[] { "*.do" };
    }

    //上传文件配置
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        //上传文件路径
        String filepath = "D:/project/red";
        //单个文件大小
        Long singleMax = (long) (5*Math.pow(2, 20));
        //总共文件大小
        Long totalMax = (long) (10*Math.pow(2, 20));
        //设置上传文件配置
        registration.setMultipartConfig(new MultipartConfigElement(filepath, singleMax, totalMax, 0 ));
    }
}
