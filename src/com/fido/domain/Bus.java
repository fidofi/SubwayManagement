package com.fido.domain;

/**
 * 
 * @author: fido
 * @date:2017年5月22日 下午6:44:53
 * @description：公交车类，即一条线路
 *
 */
public class Bus {
	private int bnumber;// 公交编号
	private String bname;// 公交号码

	public Bus() {

	}

	public Bus(int bnumber, String bname) {
		this.bnumber = bnumber;
		this.bname = bname;
	}

	public int getBnumber() {
		return bnumber;
	}

	public void setBnumber(int bnumber) {
		this.bnumber = bnumber;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

}
