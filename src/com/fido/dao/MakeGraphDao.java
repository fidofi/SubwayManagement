package com.fido.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.fido.domain.Station;
import com.fido.impl.BeanListHandler;
import com.fido.utils.JDBCUtils;

/**
 * 
 * @author: fido
 * @date:2017��5��24�� ����8:40:40
 * @description������ͼ����ز���
 *
 */
public class MakeGraphDao {
	
	
	/**
	 * 
	 * @datae��2017��5��24��
	 * @author:by fido       
	 * @param bname  
	 * @return ������·���������·����վ��
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Station> getStationListByBus(int bnumber) throws SQLException, ClassNotFoundException{
		 String sql="select station.snumber,sname from station,sb where sb.snumber=station.snumber and sb.bnumber=?";
		 BeanListHandler handler=new BeanListHandler(Class.forName("com.fido.domain.Station"));//list���涼��վ�����
		 Object[] args={bnumber};
		 List<Station> list=(List)JDBCUtils.find(sql,args,handler);//���ָ����·������վ��
		 return list;		 
	}
	
	/***
	 * 
	 * @datae��2017��5��25��
	 * @author:by fido       
	 * @return ȫ��վ�㣨���ظ���������  
	 * @return int
	 */
	public int getVertexNum(){
		Connection conn=null;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	try {
    		conn=JDBCUtils.getConnection();
    		String sql="select count(distinct sname) from station";
    		ps=conn.prepareStatement(sql);
    	    rs= ps.executeQuery();
    	    if(rs.next()){ //ע���α�
    	    	 return rs.getInt(1);
    	    }
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	finally{
    		JDBCUtils.release(conn, ps, rs);
    	}
    	return 0;
	}
	
	public List<Station> getCommonList() throws SQLException, ClassNotFoundException{
		String sql="SELECT snumber,sname FROM station GROUP BY sname HAVING COUNT(*)>1";//�ɻ���д���������·�ϵ�վ��
		 BeanListHandler handler=new BeanListHandler(Class.forName("com.fido.domain.Station"));//list���涼��վ�����
		 List<Station> list=(List)JDBCUtils.find(sql,null,handler);//
		 return list;		 
	}

}
