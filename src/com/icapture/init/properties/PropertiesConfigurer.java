package com.icapture.init.properties;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.icapture.util.GlobalLogger;
import com.inter.PropertiesSource;
import com.util.LogsUtil;

public class PropertiesConfigurer extends PropertyPlaceholderConfigurer implements PropertiesSource {
	
	/**
	 * 全局properties配置文件对象持有
	 */
	private static Properties properties;
	
	protected static final Logger logger = GlobalLogger.init_properties;
	
	private PropertiesConfigurer(){
		super();
	}
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,Properties props) 
			throws BeansException {
		logger.info(LogsUtil.LINE);
        logger.info(LogsUtil.PREFIX2 + "Properties Load ...");
        
        super.processProperties(beanFactoryToProcess, props);
        logger.info(LogsUtil.PREFIX3 + "source:" + props);
        properties = props;
        
        logger.info(LogsUtil.PREFIX3 + "Properties Loading is complete");
    }
	
	/**
     * 检验提供的属性集合是否为空.
     * 
     * @param props 提供的属性集合
     * @return boolean 如果非null并且size>=1, 则返回 true;其他返回false.
     */
    public static boolean isEmpty(final Properties props) {
        return (null == props || props.size() == 0);
    }
	
	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @return 返回properties中该key对应的值,没有获取到值 返回null
	 */
	public static String getStringValueByKey(String key){
		if(isEmpty(properties)){
			logger.error(LogsUtil.PREFIX3 + "properties is null or size = 0");
			return null;
		}
		return properties.getProperty(key);
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

}
