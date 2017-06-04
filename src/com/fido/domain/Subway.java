package com.fido.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author: fido
 * @date:2017年5月22日 下午6:44:53
 * @description：一条线路
 * 
 */
public class Subway implements Serializable  {
	private int bnumber;// 地铁编号
	private String bname;// 地铁名字
	public Subway() {

	}

	public Subway(int bnumber, String bname) {
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
