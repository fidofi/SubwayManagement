package com.fido.domain;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 
 * @author: fido
 * @date:2017��5��23�� ����7:57:05
 * @description:ͼ�Ķ�����
 * 
 */
public class Vertex implements Serializable  {
	private LinkedList<Station> edgeList;// ������洢��վ����ڽӽ��
	private Station station;// ��վ�����Ϣ

	public Vertex() {

	}

	public LinkedList<Station> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(LinkedList<Station> edgeList) {
		this.edgeList = edgeList;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Vertex(LinkedList<Station> edgeList, Station station) {
		this.edgeList = edgeList;
		this.station = station;
	}

	public Vertex(Station station) {
		this.edgeList = new LinkedList<Station>();
		this.station = station;
	}



}
