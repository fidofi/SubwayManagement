package com.fido.domain;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 
 * @author: fido
 * @date:2017年5月23日 下午7:57:05
 * @description:图的顶点类
 * 
 */
public class Vertex implements Serializable  {
	private LinkedList<Station> edgeList;// 用链表存储该站点的邻接结点
	private Station station;// 该站点的信息

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
