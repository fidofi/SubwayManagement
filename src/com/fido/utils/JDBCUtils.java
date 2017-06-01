package com.fido.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fido.impl.ResultSetHandler;
import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * 
 * @author: fido
 * @date:2017��5��24�� ����7:32:15
 * @description����c3p0���ӳ�+jdbc
 *
 */
public class JDBCUtils {
	private static ComboPooledDataSource  datasource=null;

	//��̬������ʼ��
	static{
		datasource=new ComboPooledDataSource("mysql_fido");//c3p0�����ӳ�,Ĭ��Ѱ��c3p0-config.xml����ļ�
	}	
	//�������
    public static Connection getConnection() throws SQLException{
    	return datasource.getConnection();
    } 

    /**
     * 
     * @datae��2017��5��24��
     * @author:by fido       
     * @param sql
     * @param args  
     * @return  ��ɾ�Ĳ������Ǹ��²��������������������Ҫ�Ĺ�ͬ����
     */
    public static void update(String sql,Object[]args){
    	Connection conn=null;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	try {
			conn=JDBCUtils.getConnection();
			ps=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				ps.setObject(i+1, args[i]);
			}
			ps.executeUpdate();//��ɾ�Ķ������������
		} catch (SQLException e) {
			e.printStackTrace();
		}
        finally{ //�ͷ�����
        	JDBCUtils.release(conn, ps, rs);
        }
    	
    }
    /**
     * 
     * @datae��2017��5��24��
     * @author:by fido       
     * @param sql
     * @param args
     * @param rst
     * @return ���ò���ģʽ �Ѳ�ѯ�������з�װ
     * @throws SQLException  
     * @return Object
     */
    
	public static Object find(String sql,Object[] args,ResultSetHandler rst) throws SQLException{
		Connection conn=null;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
        try {
			conn=JDBCUtils.getConnection();
			ps=conn.prepareStatement(sql);
            if(args!=null){ //�жϴ������Ĳ����Ƿ�Ϊ��
            	for(int i=0;i<args.length;i++){
    				ps.setObject(i+1, args[i]);
    			}
            }
		    rs=ps.executeQuery();
		    return rst.handler(rs);
		}
        finally{
        	JDBCUtils.release(conn, ps, rs);
        }     
	}
    
    
    //�ͷ���Դ
    public static void release(Connection conn,Statement ps,ResultSet rs){
   	 if(rs!=null){
		 try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 finally{
			 if(ps!=null){
				 try{
					 ps.close();
				 }
				 catch(SQLException e){
					 e.printStackTrace();
				 }
				 finally{
					 if(conn!=null){
						 try{
							 conn.close();
						 }
					 
					 catch(SQLException e){
    					 e.printStackTrace();
    				 }
					 conn=null;
					 }
				 }
				 ps=null;
			 }
		 }
		 rs=null;
	 }
    }
	
}
