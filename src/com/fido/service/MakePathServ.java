package com.fido.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.fido.domain.Graph;
import com.fido.domain.ListAndCount;
import com.fido.domain.Station;
import com.fido.domain.Vertex;

/**
 * 
 * @author: fido
 * @date:2017��5��26�� ����2:01:15
 * @description:����ͼ��������ͼ֮�������·���Լ����·��
 * 
 */
public class MakePathServ {
	private HashMap<Integer, Vertex> vertexMap = new HashMap<Integer, Vertex>();// ���㼯��
	private HashMap<Integer, Boolean> ifexist = new HashMap<Integer, Boolean>();;// ���ں�����㷨���жϸ�վ���Ƿ����վ����
	private Stack<Station> stack = null;
	private HashMap<String, Station> nameIdMap = new HashMap<String, Station>();// ������������Ѱ��վ�����

	public MakePathServ(Graph graph) {
		vertexMap = graph.getVertexMap();// ��ø�ͼ��վ�㼯��

	}

	public List<ListAndCount> findAllPath(String startname, String endname) {
		List<Integer> vertexIdList = new ArrayList<Integer>();
		List<Vertex> vertexList = new ArrayList<Vertex>();
		for (Map.Entry<Integer, Vertex> entry : vertexMap.entrySet()) {
			vertexIdList.add(entry.getKey());
			vertexList.add(entry.getValue());
		}
		// ��ʼ�����ж��㶼û��ջ
		for (int i = 0; i < vertexIdList.size(); i++) {
			ifexist.put(vertexIdList.get(i), false);
			nameIdMap.put(vertexList.get(i).getStation().getSname(), vertexList
					.get(i).getStation());
		}
		// ������α�����д�ڹ��췽���ģ������������Ե�ʱ���֣���������������յ㻥���Ĳ�ѯʱ���ᱨ��ԭ����map���Ѿ���¼�ˣ�������Ҫ
		// ÿһ��Ѱ�Ҷ�Ҫ����map
		stack = new Stack<Station>();// ջʵ�ֱ���

		List<ListAndCount> path = new ArrayList<ListAndCount>();// ����ҵ�����·
		if (!nameIdMap.containsKey(endname)
				|| !nameIdMap.containsKey(startname)) {
			return null;
		}
		Station start = nameIdMap.get(startname);
		// �Ȱ�������ջ�У���������ѷ���ջ����
		if (start != null) {
			stack.push(start);
			ifexist.put(start.getSnumber(), true);
			dfs(startname, endname, path);
			LinkedList<Station> nearList = vertexMap.get(start.getSnumber())
					.getEdgeList();// ������������ڽӽ��
			while (!stack.isEmpty()) {
				for (int i = 0; i < nearList.size(); i++) {
					dfs(nearList.get(i).getSname(), endname, path);
				}
				stack.pop();// ����Ҫ�ǵð�����ջ��Ū����Ȼջ��һֱ����һ��Ԫ�ؾͻ�������ѭ��
			}
		}
		return path;
	} 
	public void dfs(String startname, String endname,
			List<ListAndCount> path) {
		Station start = nameIdMap.get(startname);
		Station end = nameIdMap.get(endname);
		if (!ifexist.get(start.getSnumber())) { // δ��ջ��
			stack.push(start);
			ifexist.put(start.getSnumber(), true);// ����ջ��Ȼ������Ѿ���ջ��

			if (stack.peek().getSnumber() == end.getSnumber()) {
				ArrayList<Station> tempList = new ArrayList<Station>(); // �ҵ������е�һ��·����
				for (Station station : stack) {
					tempList.add(station);
				}
				ListAndCount listcount=new ListAndCount(tempList,tempList.size());
				path.add(listcount);// ���뵽�ܵ�·����Ϣ��
				ifexist.put(stack.pop().getSnumber(), false);// ������֮�����δ��ջ
				return;
			}
			LinkedList<Station> tempNearList = vertexMap
					.get(start.getSnumber()).getEdgeList();
			for (int k = 0; k < tempNearList.size(); k++) {
				// �����ʱ�Ѿ�������һ���ҵ��յ�ʱ��·��������δ�ҵ�·���Ͳ��ý������±����ˣ���ʡʱ��
				dfs(tempNearList.get(k).getSname(), endname, path);
			}
			// �����վ����ջ�е����������ڽ�վ�㶼��������ҲҪ��ջ��Ȼ�����վ������ ��ͬʱ�ǵ÷��ǳ�ջ��Ҫ�ѱ�־��������
			ifexist.put(stack.pop().getSnumber(), false);
		}
	}
}
