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
 * @date:2017年5月24日 下午8:40:40
 * @description：构建图的相关操作
 *
 */
public class MakeGraphDao {
	
	
	/**
	 * 
	 * @datae：2017年5月24日
	 * @author:by fido       
	 * @param bname  
	 * @return 根据线路名查出该线路所有站点
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Station> getStationListByBus(int bnumber) throws SQLException, ClassNotFoundException{
		 String sql="select station.snumber,sname from station,sb where sb.snumber=station.snumber and sb.bnumber=?";
		 BeanListHandler handler=new BeanListHandler(Class.forName("com.fido.domain.Station"));//list里面都是站点对象
		 Object[] args={bnumber};
		 List<Station> list=(List)JDBCUtils.find(sql,args,handler);//获得指定线路的所有站点
		 return list;		 
	}
	
	/***
	 * 
	 * @datae：2017年5月25日
	 * @author:by fido       
	 * @return 全部站点（不重复）的数量  
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
    	    if(rs.next()){ //注意游标
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
		String sql="SELECT snumber,sname FROM station GROUP BY sname HAVING COUNT(*)>1";//可获得有处于两条线路上的站点
		 BeanListHandler handler=new BeanListHandler(Class.forName("com.fido.domain.Station"));//list里面都是站点对象
		 List<Station> list=(List)JDBCUtils.find(sql,null,handler);//
		 return list;		 
	}

}
