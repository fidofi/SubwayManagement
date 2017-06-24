package com.fido.domain;

public class Compare {
	private Integer listIndex;//在序列的下标
	private Integer changeCount;//换乘次数
	
	
	public Compare(Integer listIndex, Integer changeCount) {

		this.listIndex = listIndex;
		this.changeCount = changeCount;
	}
	public Integer getListIndex() {
		return listIndex;
	}
	public void setListIndex(Integer listIndex) {
		this.listIndex = listIndex;
	}
	public Integer getChangeCount() {
		return changeCount;
	}
	public void setChangeCount(Integer changeCount) {
		this.changeCount = changeCount;
	}
	
	

}
