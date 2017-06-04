package com.fido.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fido.dao.MakeGraphDao;
import com.fido.domain.CommonStation;
import com.fido.domain.Graph;
import com.fido.domain.Station;
import com.fido.domain.Subway;
import com.fido.domain.Vertex;

/**
 * 
 * @author: fido
 * @date:2017年5月24日 下午7:17:59
 * @description：从数据库中取出信息构造图
 * 
 */
public class MakeGraphServ {
	private MakeGraphDao dao = new MakeGraphDao();
	private Graph graph;
	private CommonStation common=new CommonStation();

	public MakeGraphServ() {
		graph = new Graph();
	}
	
	

	public Graph getGraph() {
		return graph;
	}



	public void setGraph(Graph graph) {
		this.graph = graph;
	}



	/**
	 * 
	 * @datae：2017年5月25日
	 * @author:by fido
	 * @param station
	 * @return 添加站点信息到顶点集合里，注意公共站点的处理
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void insertVertex(Station station) throws ClassNotFoundException, SQLException {
		String sname = station.getSname();// 该顶点的名称
		int snumber = station.getSnumber();// 该顶点的编号
		List<Subway> subwayList=dao.getSubwayList(sname);//设置该站点所属线路
		HashMap<Integer,Subway> subwayMap=new HashMap<Integer,Subway>();
		for(int i=0;i<subwayList.size();i++){
			 subwayMap.put(subwayList.get(i).getBnumber(), subwayList.get(i));
		}
		station.setSubwayMap(subwayMap);
		if (CommonStation.getCommonMap().containsKey(sname)) { // 如果属于原先确定好的公共站点
			if (CommonStation.getCommonMap().get(sname).get(1) ==1) {// 并且不是第一次插入
				return;
			}
			else{ //属于公共站点，但还没有插入到图中
				ArrayList<Integer> list=CommonStation.getCommonMap().get(sname);
				list.set(1, 1);
				CommonStation.getCommonMap().put(sname, list);//做个标记表示已经插入过了		
			}
		} 
			// 加入顶点到顶点map里面
			Vertex vertex = new Vertex(station);
			graph.getVertexMap().put(station.getSnumber(), vertex);
			int num = graph.getVertexNum();
			graph.setVertexNum(num++);
	}

	/**
	 * 
	 * @datae：2017年5月25日
	 * @author:by fido
	 * @param start
	 * @param end
	 * @param graph
	 * @return void
	 */
	public void insertEdge(Station start, Station end) {
		// 获得两个要关联的站点的编号
		int startIndex = start.getSnumber();
		int endIndex = end.getSnumber();
		// 获得两个要关联的站点的名称
		String startName = start.getSname();
		String endName = end.getSname();
		if (CommonStation.getCommonMap().containsKey(startName)) {
			startIndex = CommonStation.getCommonMap().get(startName).get(0);// 获得重复站点的编号
		}
		if (CommonStation.getCommonMap().containsKey(endName)) {
			endIndex = CommonStation.getCommonMap().get(endName).get(0);// 获得重复站点的编号
		}
		// 分别添加到各自顶点对应的邻接点链表中
		graph.getVertexMap().get(startIndex).getEdgeList().add(end);
		graph.getVertexMap().get(endIndex).getEdgeList().add(start);
		int num = graph.getEdgeNum();
		graph.setEdgeNum(num++);
	}

	/**
	 * 
	 * @datae：2017年5月25日
	 * @author:by fido
	 * @param bnumber
	 * @return 根据线路编号，从数据库从获取该线路的所有站点，并两两连接
	 */
	public void create(int bnumber) {
		try {
			List<Station> stationList = dao.getStationListByBus(bnumber);
			for (int i = 0; i < stationList.size(); i++) {
				this.insertVertex(stationList.get(i));
			}
			for (int i = 0; i < stationList.size(); i++) {
				if (i != stationList.size() - 1) { // 不是线路的最后一个都必定是两两相连
					this.insertEdge(stationList.get(i), stationList.get(i + 1));
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("根据线路构造图出现了问题");
			e.printStackTrace();
		}
	}
	public MakeGraphDao getDao() {
		return dao;
	}



	public void setDao(MakeGraphDao dao) {
		this.dao = dao;
	}



	public CommonStation getCommon() {
		return common;
	}



	public void setCommon(CommonStation common) {
		this.common = common;
	}

}
