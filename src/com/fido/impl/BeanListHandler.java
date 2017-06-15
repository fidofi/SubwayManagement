package com.fido.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author: fido
 * @date:2017��5��24�� ����8:20:12
 * @description����Ӧ��ѯ���Ϊ���ϵ����
 * 
 */
public class BeanListHandler implements ResultSetHandler {
	private Class clazz; // ���ڴ���Ҫ�����bean����
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
			// ���ﲻ��ֱ����whileѭ����Ҫ��do..while����Ȼ��©�˵�һ������
			do {
				Object bean = clazz.newInstance();
				ResultSetMetaData rmt = rs.getMetaData(); // ��ý������Ԫ����
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

