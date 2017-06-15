package com.fido.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author: fido
 * @date:2017年5月24日 下午8:20:12
 * @description：对应查询结果为集合的情况
 * 
 */
public class BeanListHandler implements ResultSetHandler {
	private Class clazz; // 用于代表要代表的bean对象
	public BeanListHandler(Class clazz) {
		this.clazz = clazz;
	}
	@Override
	public Object handler(ResultSet rs) {
		try {
			if (!rs.next()) {
				return null;
			}
			List list = new ArrayList<>();
			// 这里不能直接用while循环，要用do..while，不然会漏了第一个数据
			do {
				Object bean = clazz.newInstance();
				ResultSetMetaData rmt = rs.getMetaData(); // 获得结果集的元数据
				int columnNum = rmt.getColumnCount();
				for (int i = 0; i < columnNum; i++) {
					String columnName = rmt.getColumnName(i + 1);
					Object columnData = rs.getObject(i + 1);

					Field field = bean.getClass().getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(bean, columnData);
				}
				list.add(bean);
			} while (rs.next());
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

