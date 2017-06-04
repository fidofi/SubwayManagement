package com.fido.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.fido.dao.MakeGraphDao;
import com.fido.domain.CommonStation;
import com.fido.domain.Graph;
import com.fido.domain.Station;
import com.fido.domain.Subway;
import com.fido.service.MakeGraphServ;
import com.fido.service.MakePathServ;
import com.fido.utils.MapUtils;

public class MakePathServTest {
	MakeGraphServ service=new MakeGraphServ();
	MakeGraphDao dao=new MakeGraphDao();
	CommonStation common=new CommonStation();
	

	@Test
	public void testFindAllPaht() throws ClassNotFoundException, SQLException {
		long startTime=System.currentTimeMillis();   //��ȡ��ʼʱ��
		List<Station> commonList=dao.getCommonList();
		for(int i=0;i<commonList.size();i++){
			 common.addCommonStation(commonList.get(i));
		}	
		
              for(int k=0;k<8;k++){
            	  service.create(k+1);
              }
              service.create(302);
              Station one=dao.getStationByName("�ֺ���");
              Station two=dao.getStationByName("������·");
              service.insertEdge(one, two);
                 
           Graph graph=service.getGraph();
       	MakePathServ serv=new MakePathServ(graph);
       	List<ArrayList<Station>> path=serv.findAllPath("����", "����԰");
       	String change=null;
       	for(int i=0;i<path.size();i++){
       		  ArrayList<Station> temp=path.get(i);
       		  for(int k=0;k<temp.size();k++){
       			  if(k!=0&&k!=temp.size()-1){
       				  HashMap<Integer,Subway> map1= temp.get(k-1).getSubwayMap();
       				  HashMap<Integer,Subway> map2=temp.get(k).getSubwayMap();
       				  HashMap<Integer,Subway> map3=temp.get(k+1).getSubwayMap();
       				  if(!MapUtils.ifsame(map1, map3)){
       					    change=MapUtils.getSameSubwayName(map1,map2, map3);
       				  }
       			  }
       			  if(change!=null){
       				  System.out.print("��"+temp.get(k).getSname()+"����"+change+"��");

       			  }
       			  else{
       				  System.out.print(temp.get(k).getSname()+"��");
       				  
       			  }
       			  change=null;
       		  }
       		  System.out.println("****");
       	}
       	long endTime=System.currentTimeMillis(); //��ȡ����ʱ��

      System.out.println("��������ʱ�䣺 "+(double)(endTime-startTime)/1000+"s");
       			
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
    //   	serv.dfs("����", "�齭�³�");
	}
}
