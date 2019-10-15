package com.yzd.mybatis.core.dao;

import java.util.LinkedHashMap;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

/**
 * @Title: DefaultMapFactory.java
 * @Package com.kjj.core.dao
 * @Description: 继承默认对象工厂，添加对象类型
 * @author ZYLORG
 * @date 2016年5月25日 上午9:38:16
 * @copyright Beijing KJJ Electronic commerce Co., LTD
 * @version V1.0   
 */
public class DefaultMapFactory extends DefaultObjectFactory {

	private static final long serialVersionUID = 665746939666922500L;

	protected Class<?> resolveInterface(Class<?> type) {
		Class<?> classToCreate;
		//可以添加多个Map子接口或接口实现类
		if (type == LinkedHashMap.class) {
			classToCreate = LinkedHashMap.class;
		} else {
			classToCreate = type;
		}
		return classToCreate;
	}
}

