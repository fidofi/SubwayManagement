package com.fido.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author: fido
 * @date:2017��5��22�� ����6:44:53
 * @description��һ����·
 * 
 */
public class Subway implements Serializable  {
	private int bnumber;// �������
	private String bname;// ��������
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