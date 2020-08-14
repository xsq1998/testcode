package com.gec.hrm.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;


public abstract class Util<T> {
	Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

	private static Properties p = new Properties();
	
	private static DataSource ds;
	
	static{
		try(InputStream in = Util.class.getResourceAsStream("/db.properties")){
			//将文件流加载到p对象
			p.load(in);
			//创建数据库连接池
			ds = DruidDataSourceFactory.createDataSource(p);  //自动填充属性,创建出数据源来
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection(){
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	// 通用更新模板方法
	public boolean update(String sql, List<Object> obj) {
		int row = 0;
		try {
			pst = getConnection().prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.size(); i++) {
					pst.setObject(i + 1, obj.get(i));
				}
			}
			row = pst.executeUpdate();
			if (row > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose(conn, pst, rs);
		}
		return false;
	}

	// 通用查询模板方法
	public List<T> query(String sql, Object...obj) {
		List<T> list = new ArrayList<>();
		try {
			pst = getConnection().prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pst.setObject(i + 1, obj[i]);
			}
			rs = pst.executeQuery();
			while(rs.next()){
				//将重写方法获取的对象,放入集合中
				list.add(getEntity(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose(conn, pst, rs);
		}
		return list;
	}

	//通过子类实现类,具体重写获取rs的方法
	public abstract T getEntity(ResultSet rs) throws Exception;

	
	//查询出总记录数
	public int queryCount(String sql,Object...obj){
		int row = 0;
		try {
			pst = getConnection().prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pst.setObject(i + 1, obj[i]);
			}
			rs = pst.executeQuery();
			if(rs.next()){
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose(conn, pst, rs);
		}
		return row;
	}
	
	public void getClose(Connection conn, PreparedStatement pst, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
