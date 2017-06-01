package com.fido.domain;

/**
 * 
 * @author: fido
 * @date:2017年5月22日 下午6:45:08
 * @description:站点类
 *
 */
public class Station {
	private int snumber;// 站点编号
	private String sname;// 站点名字

	public Station() {

	}

	public Station(int snumber, String sname) {
		this.snumber = snumber;
		this.sname = sname;
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
	   return "编号："+snumber+"站点名:"+sname;
	}

}
