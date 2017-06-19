package com.fido.domain;

import java.util.ArrayList;

public class ListAndCount {
	private ArrayList<Station> list;//线路
	private int count;//该线路的站点数

	public ListAndCount(ArrayList<Station> list, int count) {
		super();
		this.list = list;
		this.count = count;
	}

	public ArrayList<Station> getList() {
		return list;
	}

	public void setList(ArrayList<Station> list) {
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
