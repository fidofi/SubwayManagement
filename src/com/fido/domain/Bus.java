package com.fido.domain;

/**
 * 
 * @author: fido
 * @date:2017��5��22�� ����6:44:53
 * @description���������࣬��һ����·
 *
 */
public class Bus {
	private int bnumber;// �������
	private String bname;// ��������

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
