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
 * @date:2017��5��24�� ����7:17:59
 * @description�������ݿ���ȡ����Ϣ����ͼ
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
	 * @datae��2017��5��25��
	 * @author:by fido
	 * @param station
	 * @return ���վ����Ϣ�����㼯���ע�⹫��վ��Ĵ���
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void insertVertex(Station station) throws ClassNotFoundException, SQLException {
		String sname = station.getSname();// �ö��������
		int snumber = station.getSnumber();// �ö���ı��
		List<Subway> subwayList=dao.getSubwayList(sname);//���ø�վ��������·
		HashMap<Integer,Subway> subwayMap=new HashMap<Integer,Subway>();
		for(int i=0;i<subwayList.size();i++){
			 subwayMap.put(subwayList.get(i).getBnumber(), subwayList.get(i));
		}
		station.setSubwayMap(subwayMap);
		if (CommonStation.getCommonMap().containsKey(sname)) { // �������ԭ��ȷ���õĹ���վ��
			if (CommonStation.getCommonMap().get(sname).get(1) ==1) {// ���Ҳ��ǵ�һ�β���
				return;
			}
			else{ //���ڹ���վ�㣬����û�в��뵽ͼ��
				ArrayList<Integer> list=CommonStation.getCommonMap().get(sname);
				list.set(1, 1);
				CommonStation.getCommonMap().put(sname, list);//������Ǳ�ʾ�Ѿ��������		
			}
		} 
			// ���붥�㵽����map����
			Vertex vertex = new Vertex(station);
			graph.getVertexMap().put(station.getSnumber(), vertex);
			int num = graph.getVertexNum();
			graph.setVertexNum(num++);
	}

	/**
	 * 
	 * @datae��2017��5��25��
	 * @author:by fido
	 * @param start
	 * @param end
	 * @param graph
	 * @return void
	 */
	public void insertEdge(Station start, Station end) {
		// �������Ҫ������վ��ı��
		int startIndex = start.getSnumber();
		int endIndex = end.getSnumber();
		// �������Ҫ������վ�������
		String startName = start.getSname();
		String endName = end.getSname();
		if (CommonStation.getCommonMap().containsKey(startName)) {
			startIndex = CommonStation.getCommonMap().get(startName).get(0);// ����ظ�վ��ı��
		}
		if (CommonStation.getCommonMap().containsKey(endName)) {
			endIndex = CommonStation.getCommonMap().get(endName).get(0);// ����ظ�վ��ı��
		}
		// �ֱ���ӵ����Զ����Ӧ���ڽӵ�������
		graph.getVertexMap().get(startIndex).getEdgeList().add(end);
		graph.getVertexMap().get(endIndex).getEdgeList().add(start);
		int num = graph.getEdgeNum();
		graph.setEdgeNum(num++);
	}

	/**
	 * 
	 * @datae��2017��5��25��
	 * @author:by fido
	 * @param bnumber
	 * @return ������·��ţ������ݿ�ӻ�ȡ����·������վ�㣬����������
	 */
	public void create(int bnumber) {
		try {
			List<Station> stationList = dao.getStationListByBus(bnumber);
			for (int i = 0; i < stationList.size(); i++) {
				this.insertVertex(stationList.get(i));
			}
			for (int i = 0; i < stationList.size(); i++) {
				if (i != stationList.size() - 1) { // ������·�����һ�����ض�����������
					this.insertEdge(stationList.get(i), stationList.get(i + 1));
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("������·����ͼ����������");
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
