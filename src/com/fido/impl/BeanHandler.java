package com.fido.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 
 * @author: fido
 * @date:2017��5��24�� ����8:19:49
 * @description����Ӧ��ѯ���Ϊ���������
 * 
 */
public class BeanHandler implements ResultSetHandler {
	private Class clazz;// ���ڱ�ʾҪ��װ��bean��
	public BeanHandler(Class clazz) {
		this.clazz = clazz;
	}
	@Override
	public Object handler(ResultSet rs) {
		try {
			if (!rs.next()) { // ������������û�оͷ���Null
				return null;
			}
			Object bean = clazz.newInstance(); // ͨ�����䴴��Bean����
			ResultSetMetaData rsmt = rs.getMetaData();// ��ý������Ԫ����
			int columnNum = rsmt.getColumnCount();// ��ù��ж����У������ٸ����ԣ�
			for (int i = 0; i < columnNum; i++) {
				String columnName = rsmt.getColumnName(i + 1);// �õ�ÿ�е�����
				Object columnData = rs.getObject(i + 1);// �õ�����

				Field field = clazz.getDeclaredField(columnName);// ͨ�����䣬�����Ķ�Ӧ����
				field.setAccessible(true);
				field.set(bean, columnData); // Ȼ��������Ӧ�����Ե�ֵ
			}
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
