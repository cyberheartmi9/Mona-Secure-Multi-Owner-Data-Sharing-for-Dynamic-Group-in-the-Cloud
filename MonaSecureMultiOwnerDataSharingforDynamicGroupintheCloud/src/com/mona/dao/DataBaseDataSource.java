package com.mona.dao;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class DataBaseDataSource {

	BeanFactory beanFactory;

	public static BeanFactory getDataFactory() {

		Resource resource = new ClassPathResource("applicationContext.xml");
		return new XmlBeanFactory(resource);

	}

}
