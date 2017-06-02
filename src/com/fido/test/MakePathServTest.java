package com.fido.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fido.dao.MakeGraphDao;
import com.fido.domain.CommonStation;
import com.fido.domain.Graph;
import com.fido.domain.Station;
import com.fido.service.MakeGraphServ;
import com.fido.service.MakePathServ;

public class MakePathServTest {
	MakeGraphServ service=new MakeGraphServ();
	MakeGraphDao dao=new MakeGraphDao();
	CommonStation common=new CommonStation();
	

	@Test
	public void testFindAllPaht() throws ClassNotFoundException, SQLException {
		List<Station> commonList=dao.getCommonList();
		for(int i=0;i<commonList.size();i++){
			 common.addCommonStation(commonList.get(i));
		}	
		
              for(int k=0;k<8;k++){
            	  service.create(k+1);
              }
                 
           Graph graph=service.getGraph();
       	MakePathServ serv=new MakePathServ(graph);
       	List<ArrayList<Station>> path=serv.findAllPath("长湴", "大学城南");
       	for(int i=0;i<path.size();i++){
       		  ArrayList<Station> temp=path.get(i);
       		  for(int k=0;k<temp.size();k++){
       			    System.out.print(temp.get(k).getSname()+"→");
       		  }
       		  System.out.println("****");
       	}
       			
	}

	//@Test
	public void testDfs() throws ClassNotFoundException, SQLException {
		List<Station> commonList=dao.getCommonList();
		for(int i=0;i<commonList.size();i++){
			 common.addCommonStation(commonList.get(i));
		}	
                 service.create(3);
                 service.create(4);
                 service.create(5);
                 service.create(6);
                 
           Graph graph=service.getGraph();
       	MakePathServ serv=new MakePathServ(graph);
       	serv.dfs("长湴", "珠江新城");
	}
}
