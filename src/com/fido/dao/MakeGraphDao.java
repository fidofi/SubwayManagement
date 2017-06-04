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
	public List<Station> getStationListByBus(int bnumber) {
		String sql = "select station.snumber,sname from station,sb where sb.snumber=station.snumber and sb.bnumber=?";
		BeanListHandler handler = null;
		List<Station> list = null;
		try {
			handler = new BeanListHandler(
					Class.forName("com.fido.domain.Station"));
			Object[] args = { bnumber };
			list = (List) JDBCUtils.find(sql, args, handler);// ���ָ����·������վ��
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("�Ҳ���station����");
		}// list���涼��վ�����
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("���ݵ����Ż�ȡ��·ʱ����");
		}
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
	
	public List<Station> getCommonList() {
		String sql = "SELECT snumber,sname FROM station GROUP BY sname HAVING COUNT(*)>1";// �ɻ���д���������·�ϵ�վ��
		BeanListHandler handler = null;
		List<Station> list = null;
		try {
			handler = new BeanListHandler(
					Class.forName("com.fido.domain.Station"));
			list = (List) JDBCUtils.find(sql, null, handler);//
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// list���涼��վ�����
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ȡ����վ��ʱ����");
		}
		return list;
	}
	//�ҳ�ÿ��վ�����ڵ���·��һ��վ������ж�����·
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
		}// list���涼��վ�����
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�ҳ�ÿ��վ�����ڵ���·ʱ����");
		}
		return list;
	}
	//�������ַ�װstation����
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
			System.out.println("����վ�������վ������ʱ�����");
		}
		return station;
	}
	
	//�������õ�ͼ�ṹ�����浽�����ļ���
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
			System.out.println("�Ҳ����ļ�");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("д��ͼ�ṹʱ�����쳣");
		}
		finally{
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			    System.out.println("�ر�������쳣");
			}
		}
	}
	
	//���ļ��ж�ȡͼ�ṹ
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
			System.out.println("��ȡͼ�ṹ��ʱ���Ҳ����ļ�");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("��ȡͼ�ṹ��ʱ�����");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return graph;	
	}
	//��ȡ������·��
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
    	    while(rs.next()){ //ע���α�
    	        list.add(rs.getString(1));
    	    }
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    		System.out.println("��ȡ����·������ʱ�����");
    	}
    	finally{
    		JDBCUtils.release(conn, ps, rs);
    	}
    	return list;
    	
    }
    
    //������·����ȡ����·�����е�վ��
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
    	    if(rs.next()){ //ע���α�
    	         bnumber=rs.getInt(1);
    	    }
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    		System.out.println("��ȡ����·������ʱ�����");
    	}
    	finally{
    		JDBCUtils.release(conn, ps, rs);
    	}
    	return this.getStationListByBus(bnumber);
    	 
    	 
    }
}
