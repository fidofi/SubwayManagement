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
 * @date:2017年5月24日 下午7:32:15
 * @description：用c3p0连接池+jdbc
 *
 */
public class JDBCUtils {
	private static ComboPooledDataSource  datasource=null;

	//静态代码块初始化
	static{
		datasource=new ComboPooledDataSource("mysql_fido");//c3p0的连接池,默认寻找c3p0-config.xml这个文件
	}	
	//获得连接
    public static Connection getConnection() throws SQLException{
    	return datasource.getConnection();
    } 

    /**
     * 
     * @datae：2017年5月24日
     * @author:by fido       
     * @param sql
     * @param args  
     * @return  增删改操作都是更新操作，抽象出方法和所需要的共同参数
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
			ps.executeUpdate();//增删改都是用这个方法
		} catch (SQLException e) {
			e.printStackTrace();
		}
        finally{ //释放连接
        	JDBCUtils.release(conn, ps, rs);
        }
    	
    }
    /**
     * 
     * @datae：2017年5月24日
     * @author:by fido       
     * @param sql
     * @param args
     * @param rst
     * @return 运用策略模式 把查询操作进行封装
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
            if(args!=null){ //判断传进来的参数是否为空
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
    
    
    //释放资源
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
