package com.fido.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fido.domain.Graph;
import com.fido.domain.Station;
import com.fido.domain.Subway;
import com.fido.impl.BeanHandler;
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
	public List<Station> getStationListByBus(int bnumber) {
		String sql = "select station.snumber,sname from station,sb where sb.snumber=station.snumber and sb.bnumber=?";
		BeanListHandler handler = null;
		List<Station> list = null;
		try {
			handler = new BeanListHandler(
					Class.forName("com.fido.domain.Station"));
			Object[] args = { bnumber };
			list = (List) JDBCUtils.find(sql, args, handler);// 获得指定线路的所有站点
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("找不到station对象");
		}// list里面都是站点对象
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("根据地铁号获取线路时出错");
		}
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
	
	public List<Station> getCommonList() {
		String sql = "SELECT snumber,sname FROM station GROUP BY sname HAVING COUNT(*)>1";// 可获得有处于两条线路上的站点
		BeanListHandler handler = null;
		List<Station> list = null;
		try {
			handler = new BeanListHandler(
					Class.forName("com.fido.domain.Station"));
			list = (List) JDBCUtils.find(sql, null, handler);//
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// list里面都是站点对象
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获取公共站点时出错");
		}
		return list;
	}
	//找出每个站点所在的线路，一个站点可能有多条线路
	public List<Subway> getSubwayList(String sname) {
		String sql = "SELECT * FROM bus WHERE bnumber IN (SELECT bnumber FROM sb WHERE snumber IN (SELECT snumber FROM station WHERE sname=?))";
		BeanListHandler handler = null;
		List<Subway> list = null;
		try {
			handler = new BeanListHandler(
					Class.forName("com.fido.domain.Subway"));
			Object[] args = { sname };
			list = (List) JDBCUtils.find(sql, args, handler);//
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}// list里面都是站点对象
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("找出每个站点所在的线路时出错");
		}
		return list;
	}
	//根据名字封装station对象
	public Station getStationByName(String name){
		String sql="SELECT snumber,sname FROM station where sname=?";
		BeanHandler handler=null;
		Station station=null;
		try {
			handler = new BeanHandler(Class.forName("com.fido.domain.Station"));
			Object args[]={name};
			station=(Station) JDBCUtils.find(sql, args, handler);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("根据站点名获得站点对象的时候出错");
		}
		return station;
	}
	
	//将构建好的图结构，保存到磁盘文件中
	public void write2txt(Graph graph) {
		File file=new File("D://graph.txt");
		FileOutputStream fileout=null;
		ObjectOutputStream out=null;
		try {
			fileout = new FileOutputStream(file);
			out=new ObjectOutputStream(fileout);
			out.writeObject(graph);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("找不到文件");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("写入图结构时发生异常");
		}
		finally{
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			    System.out.println("关闭输出流异常");
			}
		}
	}
	
	//从文件中读取图结构
	public Graph read(){
		File file=new File("D://graph.txt");
		FileInputStream filein=null;
		ObjectInputStream in=null;
		Graph graph=null;
		try {
			filein = new FileInputStream(file);
		    in=new ObjectInputStream(filein);
		    graph=(Graph) in.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("读取图结构的时候找不到文件");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("读取图结构的时候出错");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return graph;	
	}
	//获取所有线路名
    public List<String> getAllSubwayName(){
    	Connection conn=null;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	List<String> list=new ArrayList<String>();
    	try {
    		
    		conn=JDBCUtils.getConnection();
    		String sql="select bname from bus";
    		ps=conn.prepareStatement(sql);
    	    rs= ps.executeQuery();
    	    while(rs.next()){ //注意游标
    	        list.add(rs.getString(1));
    	    }
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    		System.out.println("获取所有路线名的时候出错");
    	}
    	finally{
    		JDBCUtils.release(conn, ps, rs);
    	}
    	return list;
    	
    }
    
    //根据线路名获取该线路下所有的站点
    public List<Station> getStationListByBname(String name){
    	Connection conn=null;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	int bnumber=0;
    	try {
    		
    		conn=JDBCUtils.getConnection();
    		String sql="select bnumber from bus where bname=?";
    		ps=conn.prepareStatement(sql);
    		ps.setString(1, name);
    	    rs= ps.executeQuery();
    	    if(rs.next()){ //注意游标
    	         bnumber=rs.getInt(1);
    	    }
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    		System.out.println("获取所有路线名的时候出错");
    	}
    	finally{
    		JDBCUtils.release(conn, ps, rs);
    	}
    	return this.getStationListByBus(bnumber);
    	 
    	 
    }
}
