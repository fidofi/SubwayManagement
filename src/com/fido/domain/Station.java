package com.fido.domain;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @author: fido
 * @date:2017��5��22�� ����6:45:08
 * @description:վ����
 * 
 */
public class Station implements Serializable {
	private int snumber;// վ����
	private String sname;// վ������
	private HashMap<Integer, Subway> subwayMap;// ��վ�����ڵ���·��һ��վ���λ�ڶ�����·��

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
		return "��ţ�" + snumber + "վ����:" + sname;
	}

	public HashMap<Integer, Subway> getSubwayMap() {
		return subwayMap;
	}

	public void setSubwayMap(HashMap<Integer, Subway> subwayMap) {
		this.subwayMap = subwayMap;
	}

}
