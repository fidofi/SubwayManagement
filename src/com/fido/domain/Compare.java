package com.fido.domain;

public class Compare {
	private Integer listIndex;//�����е��±�
	private Integer changeCount;//���˴���
	
	
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
