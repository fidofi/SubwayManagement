package com.fido.domain;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @author: fido
 * @date:2017年5月22日 下午6:45:08
 * @description:站点类
 * 
 */
public class Station implements Serializable {
	private int snumber;// 站点编号
	private String sname;// 站点名字
	private HashMap<Integer, Subway> subwayMap;// 该站点所在的线路，一个站点可位于多条线路上

	public Station() {

	}

	public Station(int snumber, String sname) {
		this.snumber = snumber;
		this.sname = sname;
		this.subwayMap = new HashMap<Integer, Subway>();
	}

	public int getSnumber() {
		return snumber;
	}

	public void setSnumber(int snumber) {
		this.snumber = snumber;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	@Override
	public String toString() {
		return "编号：" + snumber + "站点名:" + sname;
	}

	public HashMap<Integer, Subway> getSubwayMap() {
		return subwayMap;
	}

	public void setSubwayMap(HashMap<Integer, Subway> subwayMap) {
		this.subwayMap = subwayMap;
	}

}
