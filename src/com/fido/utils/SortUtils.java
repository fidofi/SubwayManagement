package com.fido.utils;

import java.util.List;

import com.fido.domain.ListAndCount;


public class SortUtils {
	//交换
	public static List<ListAndCount> swap(List<ListAndCount> list, int i, int j) {
		ListAndCount temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
		return list;
	}

	 //划分函数
	public static int partition(List<ListAndCount> list, int low, int high) {
		while (high > low) {
			ListAndCount temp = list.get(low);
			int key = temp.getCount();// 取得基准
			while (high > low && list.get(high).getCount() >= key) {
				high--;
			}
			list.set(low, list.get(high));
			while (high > low && list.get(low).getCount()< key) {
				low++;
			}
			ListAndCount temp_two = list.get(low);
			list.set(high, temp_two);
			list.set(low, temp);
		}
		return low;
	}
  
	//快速排序
	public static void quickSort(List<ListAndCount> list, int low, int high) {
		if (low >= high) {
			return;
		}
		int index = SortUtils.partition(list, low, high);
		SortUtils.quickSort(list, low, index - 1);
		SortUtils.quickSort(list, index + 1, high);
	}
	
}
