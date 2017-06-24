package com.fido.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fido.dao.MakeGraphDao;
import com.fido.domain.Compare;
import com.fido.domain.ListAndCount;
import com.fido.domain.Station;
import com.fido.domain.Subway;

public class ShowPathUtils {

	// 对给进的站点路线，写明换乘线路
	public static String showPath(ArrayList<Station> list) {
		StringBuffer buffer = new StringBuffer();
		String change = null;
		for (int k = 0; k < list.size(); k++) {
			if (k != 0 && k != list.size() - 1) {
				Station map1 = list.get(k - 1);
				Station map2 = list.get(k);
				Station map3 = list.get(k + 1);
				if (!MapUtils.ifsame(map1, map2, map3)) {
					change = MapUtils.getSameSubwayName(map1, map2, map3);
				}
			}
			if (change != null) {
				buffer.append("在" + list.get(k).getSname() + "换乘" + change
						+ "→");
			} else {
				if (k != list.size() - 1)
					buffer.append(list.get(k).getSname() + "→");
				else {
					buffer.append(list.get(k).getSname() + "\n\r");
					buffer.append("*******************" + "\n\r");
				}
			}
			change = null;
		}
		return buffer.toString();
	}

	// 显示换乘最少
	public static String showLessChange(List<ListAndCount> path) {
		HashMap<Integer, Integer> findLess = new HashMap<Integer, Integer>();// 找出换乘次数最少的
		// int end = path.size() <= 10 ? path.size() : 10;
		int end = path.size();
		for (int i = 0; i < end; i++) {
			findLess.put(i, 0);
		}
		String change = null;
		for (int i = 0; i < end; i++) {
			ArrayList<Station> temp = path.get(i).getList();// 获得该线路
			for (int k = 0; k < temp.size(); k++) {
				if (k != 0 && k != temp.size() - 1) {
					Station map1 = temp.get(k - 1);
					Station map2 = temp.get(k);
					Station map3 = temp.get(k + 1);
					if (!MapUtils.ifsame(map1, map2, map3)) {
						change = MapUtils.getSameSubwayName(map1, map2, map3);
					}

				}
				if (change != null) {
					int value = findLess.get(i);
					findLess.put(i, ++value);

				}
				change = null;
			}
		}
		for (Map.Entry<Integer, Integer> entry : findLess.entrySet()) {
			System.out.println(entry.getKey() + "**" + entry.getValue());
		}
		// 以下是找出换乘最少的
		List<Compare> index = new ArrayList<Compare>();
		for (Map.Entry<Integer, Integer> entry : findLess.entrySet()) {
			Compare value = new Compare(entry.getKey(), entry.getValue());
			index.add(value);
		}
		int min = Integer.MAX_VALUE;
		int dex = 0;
		for (int i = 0; i < index.size(); i++) {
			if (min > index.get(i).getChangeCount()) {
				min = index.get(i).getChangeCount();
				dex = index.get(i).getListIndex();
			}
		}
		System.out.println("下标为：" + dex);
		for (int i = 0; i < path.get(dex).getList().size(); i++) {
			System.out.println(path.get(dex).getList().get(i));
		}
		return ShowPathUtils.showPath(path.get(dex).getList());
	}
}
