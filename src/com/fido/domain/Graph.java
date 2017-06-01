package com.fido.domain;

import java.util.HashMap;

/**
 * 
 * @author: fido
 * @date:2017年5月23日 下午7:30:40
 * @description:邻接表来实现图
 * 
 */

public class Graph {
	private HashMap<Integer, Vertex> vertexMap;// 图的各顶点用HashMap来保存（因为为了后续的插入方便将原先的顺序表改成了HashMap）
	private int vertexNum;// 图的顶点数
	private int edgeNum;// 图的边数

	public Graph() {
          vertexMap=new HashMap<Integer,Vertex>();
	}

	public HashMap<Integer, Vertex> getVertexMap() {
		return vertexMap;
	}

	public void setVertexMap(HashMap<Integer, Vertex> vertexMap) {
		this.vertexMap = vertexMap;
	}

	public int getVertexNum() {
		return vertexNum;
	}

	public void setVertexNum(int vertexNum) {
		this.vertexNum = vertexNum;
	}

	public int getEdgeNum() {
		return edgeNum;
	}

	public void setEdgeNum(int edgeNum) {
		this.edgeNum = edgeNum;
	}

}
