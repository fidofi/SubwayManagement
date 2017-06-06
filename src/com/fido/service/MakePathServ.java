package com.fido.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.fido.domain.Graph;
import com.fido.domain.Station;
import com.fido.domain.Vertex;

/**
 * 
 * @author: fido
 * @date:2017年5月26日 下午2:01:15
 * @description:给定图，查找两图之间的所有路径以及最短路径
 * 
 */
public class MakePathServ {
	private HashMap<Integer, Vertex> vertexMap = new HashMap<Integer, Vertex>();// 顶点集合
	private HashMap<Integer, Boolean> ifexist = new HashMap<Integer, Boolean>();;// 用于后面的算法来判断该站点是否存在站点中
	private Stack<Station> stack=null;
	private HashMap<String, Station> nameIdMap = new HashMap<String, Station>();// 用于输入名字寻找站点对象
	private int min=Integer.MAX_VALUE;

	public MakePathServ(Graph graph) {
		vertexMap = graph.getVertexMap();// 获得该图的站点集合
		

	}

	public List<ArrayList<Station>> findAllPath(String startname, String endname) {
		List<Integer> vertexIdList = new ArrayList<Integer>();
		List<Vertex> vertexList = new ArrayList<Vertex>();
		for (Map.Entry<Integer, Vertex> entry : vertexMap.entrySet()) {
			vertexIdList.add(entry.getKey());
			vertexList.add(entry.getValue());
		}
		// 初始的所有顶点都没进栈
		for (int i = 0; i < vertexIdList.size(); i++) {
			ifexist.put(vertexIdList.get(i), false);
			nameIdMap.put(vertexList.get(i).getStation().getSname(), vertexList
					.get(i).getStation());
		}
		//上面这段本来是写在构造方法的，可是这样测试的时候发现，若两次输入起点终点互换的查询时，会报错原因是map中已经记录了，故这里要
//		每一次寻找都要重置map
		stack = new Stack<Station>();// 栈实现遍历
		min=Integer.MAX_VALUE;//最小值记得重置为整数的最大，不然一直会记录着第一次查询成功的最小值
	    List<ArrayList<Station>> path=new ArrayList<ArrayList<Station>>();//存放找到的线路
		Station start = nameIdMap.get(startname);
		// 先把起点放入栈中，并做标记已放入栈中了
		if(start!=null){

			stack.push(start);
			ifexist.put(start.getSnumber(), true);
			dfs(startname,endname,path);
			LinkedList<Station> nearList = vertexMap.get(start.getSnumber())
					.getEdgeList();// 获得起点的所有邻接结点
			while (!stack.isEmpty()) {
				for (int i = 0; i < nearList.size(); i++) {
					dfs(nearList.get(i).getSname(), endname,path);
				}
				stack.pop();// 这里要记得把起点从栈中弄掉不然栈中一直会有一个元素就会陷入死循环
			}
		}
		return path;
	}

	public void dfs(String startname, String endname, List<ArrayList<Station>> path) {
		Station start = nameIdMap.get(startname);
		Station end = nameIdMap.get(endname);
		if (!ifexist.get(start.getSnumber())) { // 未在栈中
			stack.push(start);
			ifexist.put(start.getSnumber(), true);// 放入栈中然后标明已经进栈了

			if (stack.peek().getSnumber() == end.getSnumber()) {
				min=stack.size();//重置
				ArrayList<Station> tempList=new ArrayList<Station>(); //找到了其中的一条路线了
				for (Station station : stack) {
					tempList.add(station); 
					
				}
				path.add(tempList);//加入到总的路线信息中
				ifexist.put(stack.pop().getSnumber(), false);// 弹出来之后标明未进栈
				return;
			}
			LinkedList<Station> tempNearList = vertexMap.get(start.getSnumber())
					.getEdgeList();
			for (int k = 0; k< tempNearList.size(); k++) {
				//如果此时已经到达上一次找到终点时的路径长度仍未找到路，就不用接着往下遍历了，节省时间
				if(stack.size()==min){
					continue;
				}
				dfs(tempNearList.get(k).getSname(), endname,path);
			}
			//如果该站点在栈中但它的所有邻接站点都遍历完了也要出栈不然会出现站点跳乱	，同时记得凡是出栈都要把标志重新设置		
			ifexist.put(stack.pop().getSnumber(), false);
		} 
	}
}
