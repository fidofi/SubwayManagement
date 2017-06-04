package com.fido.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fido.dao.MakeGraphDao;
import com.fido.domain.CommonStation;
import com.fido.domain.Graph;
import com.fido.domain.Station;
import com.fido.domain.Vertex;
import com.fido.service.MakeGraphServ;

public class MakeGraphServTest {
	
	MakeGraphServ service=new MakeGraphServ();
	MakeGraphDao dao=new MakeGraphDao();
	CommonStation common=new CommonStation();

	

	@Test
	public void testCreate() throws ClassNotFoundException, SQLException {
		List<Station> commonList=dao.getCommonList();
		for(int i=0;i<commonList.size();i++){
			 common.addCommonStation(commonList.get(i));
			 System.out.println(commonList.get(i));
		}	
        for(int k=0;k<8;k++){
      	  service.create(k+1);
        }
        service.create(302);
        Station one=dao.getStationByName("�ֺ���");
        Station two=dao.getStationByName("������·");
        service.insertEdge(one, two);
                 
           Graph graph=service.getGraph();
           HashMap<Integer,Vertex> vertexMap=graph.getVertexMap();
           for(Map.Entry<Integer, Vertex> entry:vertexMap.entrySet()){
        	     System.out.print("վ����:"+entry.getKey()+"վ������"+entry.getValue().getStation().getSname()+"  ");
        	     LinkedList<Station> list=entry.getValue().getEdgeList();
        	     System.out.print("�ڽ�վ�㣺");
        	     for(int i=0;i<list.size();i++){
        	    	  System.out.print(list.get(i)+"��");
        	     }
        	     System.out.println(" ");
           }
	       
	}

}
