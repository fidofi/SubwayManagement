package com.fido.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 
 * @author: fido
 * @date:2017年5月24日 下午8:19:49
 * @description：对应查询结果为单个的情况
 * 
 */
public class BeanHandler implements ResultSetHandler {
	private Class clazz;// 用于表示要封装的bean类
	public BeanHandler(Class clazz) {
		this.clazz = clazz;
	}
	@Override
	public Object handler(ResultSet rs) {
		try {
			if (!rs.next()) { // 如果结果集里面没有就返回Null
				return null;
			}
			Object bean = clazz.newInstance(); // 通过反射创建Bean对象
			ResultSetMetaData rsmt = rs.getMetaData();// 获得结果集的元数据
			int columnNum = rsmt.getColumnCount();// 获得共有多少列（即多少个属性）
			for (int i = 0; i < columnNum; i++) {
				String columnName = rsmt.getColumnName(i + 1);// 得到每列的列名
				Object columnData = rs.getObject(i + 1);// 得到数据

				Field field = clazz.getDeclaredField(columnName);// 通过反射，获得类的对应属性
				field.setAccessible(true);
				field.set(bean, columnData); // 然后设置相应的属性的值
			}
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
