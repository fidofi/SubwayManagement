package com.fido.domain;

import java.util.HashMap;

/**
 * 
 * @author: fido
 * @date:2017��5��23�� ����7:30:40
 * @description:�ڽӱ���ʵ��ͼ
 * 
 */

public class Graph {
	private HashMap<Integer, Vertex> vertexMap;// ͼ�ĸ�������HashMap�����棨��ΪΪ�˺����Ĳ��뷽�㽫ԭ�ȵ�˳���ĳ���HashMap��
	private int vertexNum;// ͼ�Ķ�����
	private int edgeNum;// ͼ�ı���

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
