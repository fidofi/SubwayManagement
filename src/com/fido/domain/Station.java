package com.fido.domain;

/**
 * 
 * @author: fido
 * @date:2017��5��22�� ����6:45:08
 * @description:վ����
 *
 */
public class Station {
	private int snumber;// վ����
	private String sname;// վ������

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
	   return "��ţ�"+snumber+"վ����:"+sname;
	}

}
