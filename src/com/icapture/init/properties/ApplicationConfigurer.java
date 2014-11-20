package com.icapture.init.properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.util.StringUtil;

/**
 * 持有spring容器applicationContext对象持有,提供公共方法访问容器对象
 * 
 * @ClassName: ApplicationConfigurer 
 * @author huxiaohuan 
 * @date 2014年9月28日 下午1:49:00 
 * @version V1.0
 */
public class ApplicationConfigurer implements ApplicationContextAware {
	
	/**
	 * 全局容器spring applicationContext对象持有
	 */
	private static ApplicationContext application;

	public void setApplicationContext(ApplicationContext app)
			throws BeansException {
		application = app;
	}
	
	/**
     * 获取 Spring Context中 beanName对应的Bean实例.<br/>
     * 如果通过beanName在context中没有找或者 ApplicationContext没有正确初始化时,<br/>
     * 该函数都将返回null同时不会抛出异常信息.
     * 
     * @param beanName Bean名称.
     * @return Object Bean实例.
     */
    public static Object getBean(String beanName) {
        if (StringUtil.isBlank(beanName)) {
            return null;
        }

        try {
            return application.getBean(beanName);
        } catch (BeansException e) {
            return null;
        }
    }

    /**
     * 获取 Spring Context中 beanName对应的Bean实例并转换为具体的类型.<br/>
     * 如果通过beanName在context中没有找或者 ApplicationContext没有正确初始化时,<br/>
     * 该函数都将返回null同时不会抛出异常信息.
     * 
     * @param <T>
     * @param beanName Bean名称.
     * @param clazz Bean类型.
     * @return T Bean实例.
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (StringUtil.isBlank(beanName) || null == clazz) {
            return null;
        }

        try {
            return (T) application.getBean(beanName, clazz);
        } catch (BeansException e) {
            return null;
        }
    }

    /**
     * 获取 指定 Context中 beanName对应的Bean实例.<br/>
     * 如果通过beanName在context中没有找或者 ApplicationContext没有正确初始化时,<br/>
     * 该函数都将返回null同时不会抛出异常信息.
     * 
     * @param context spring ApplicationContext 实例
     * @param beanName ioc中对应bean的名称
     * @return Object Spring Bean的实例
     */
    public static Object getBean(ApplicationContext context, String beanName) {
        try {
            return context.getBean(beanName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取 Spring Context中 beanName对应的Bean实例的 Class.<br/>
     * 如果与给定名字相应的bean定义没有被找到, 将返回 null.
     * 
     * @param beanName Bean名称.
     * @return Class 注册对象的类型.
     */
    public static Class<?> getBeanType(String beanName) {
        if (StringUtil.isBlank(beanName)) {
            return null;
        }

        try {
            return application.getType(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义, 则返回true.<br/>
     * 如果与给定名字相应的bean定义没有被找到, 将返回 null.
     * 
     * @param beanName 配置中定义的beanName.
     * @return Boolean 存在则返回 true; 无则返回 false.
     */
    public static Boolean isExistsBean(String beanName) {
        if (StringUtil.isBlank(beanName)) {
            return null;
        }

        try {
            return application.containsBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    /**
     * 判断以给定名字注册的bean定义是否为 singleton Bean.<br/>
     * 如果与给定名字相应的bean定义没有被找到, 将返回 null.
     * 
     * @param beanName 配置中定义的beanName.
     * @return Boolean 是则返回 true; 不是则返回 false.
     */
    public static Boolean isSingletonBean(String beanName) {
        if (StringUtil.isBlank(beanName)) {
            return null;
        }

        try {
            return application.isSingleton(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }
	
}
