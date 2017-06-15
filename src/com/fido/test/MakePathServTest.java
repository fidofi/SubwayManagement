package com.fido.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		long startTime=System.currentTimeMillis();   //获取开始时间
		List<Station> commonList=dao.getCommonList();
		for(int i=0;i<commonList.size();i++){
			 common.addCommonStation(commonList.get(i));
		}	
		
              for(int k=0;k<8;k++){
            	  service.create(k+1);
              }
              service.create(302);
              Station one=dao.getStationByName("林和西");
              Station two=dao.getStationByName("体育西路");
              service.insertEdge(one, two);
                 
           Graph graph=service.getGraph();
       	MakePathServ serv=new MakePathServ(graph);
       	List<ArrayList<Station>> path=serv.findAllPath("长湴", "珠江新城");
       	HashMap<Integer,Integer> findLess=new HashMap<Integer,Integer>();//找出换乘次数最少的
       	for(int i=0;i<path.size();i++){
       		 findLess.put(i, 0);
       	}
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
       				  String str="在"+temp.get(k).getSname()+"换乘"+change+"→";
       				  int value=findLess.get(i);
       				  findLess.put(i, ++value);
       				  int current=findLess.get(i);//获得当前的换乘次数
       				  System.out.println(current);
       				  System.out.print(str);
       			  }
       			  else{
       				  String str2=temp.get(k).getSname()+"→"; 	
       				  System.out.print(str2);
       			  }
       			  change=null;
       		  }
       		  System.out.println("**");
       	}
       	//主要是为了找出换乘最小的线路，如何知道map的value，求所有value最小时对应的Key 即path集合里的下标
       	List<Integer> index=new ArrayList<Integer>();
     for(Map.Entry<Integer, Integer> entry:findLess.entrySet()){
    	  int key=entry.getKey();
    	  int value=entry.getValue();
    	  index.add(value);
     }
     int min=index.get(0);
     int dex=0;
     for(int i=1;i<index.size();i++){
    	   if(min>=index.get(i)){
    		   min=index.get(i);
    		   System.out.println(min);
    		   dex=i;
    	   }
     }
     System.out.println(dex);
     System.out.println(path.get(dex));
       	long endTime=System.currentTimeMillis(); //获取结束时间

      System.out.println("程序运行时间： "+(double)(endTime-startTime)/1000+"s");
       			
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
    //   	serv.dfs("长湴", "珠江新城");
	}
}
