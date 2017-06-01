package com.fido.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.fido.dao.MakeGraphDao;
import com.fido.domain.Station;

public class MakeGraphDaoTest {
	MakeGraphDao dao=new MakeGraphDao();

	//@Test
	public void testGetStationListByBus() throws ClassNotFoundException, SQLException {
		 List<Station> list=dao.getStationListByBus(5);
		 for(int i=0;i<list.size();i++){
			 System.out.println(list.get(i).getSname());
		 }
	}
	
	//@Test
	public void testGetVertexNum(){
	      System.out.println(dao.getVertexNum());
	}
	
	

}
