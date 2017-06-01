package com.fido.domain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author: fido
 * @date:2017��5��25�� ����8:14:44
 * @description����Ÿ�����·�Ĺ�ͬվ��
 * 
 */
public class CommonStation {
	private static HashMap<String, ArrayList<Integer>> commonMap = new HashMap<String, ArrayList<Integer>>();

	public static HashMap<String, ArrayList<Integer>> getCommonMap() {
		return commonMap;
	}

	public static void setCommonMap(
			HashMap<String, ArrayList<Integer>> commonMap) {
		CommonStation.commonMap = commonMap;
	}
	public void addCommonStation(Station station) {
		ArrayList list = new ArrayList<Integer>();
		list.add(station.getSnumber());
		list.add(0);
		this.commonMap.put(station.getSname(), list);
	}
}
