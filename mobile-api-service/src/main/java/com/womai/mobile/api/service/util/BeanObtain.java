package com.womai.mobile.api.service.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.web.util
 * 创建者: Chang Jinan
 * 日  期: 2016/5/11
 * 时  间: 14:26
 * 描  述：
 */
@Component("beanObtain")
public class BeanObtain implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public <T> T obtainBean(String operation) {
        return (T) applicationContext.getAutowireCapableBeanFactory().getBean(operation);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
