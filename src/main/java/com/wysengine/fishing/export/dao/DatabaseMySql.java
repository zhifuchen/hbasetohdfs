package com.wysengine.fishing.export.dao;

import com.wysengine.fishing.export.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @author Louis Yan 
* @since louis.yan@wysengine.com
* @version 2017年9月6日 上午11:31:49 
*  
*/

public class DatabaseMySql implements Database{
	private Logger logger = LoggerFactory.getLogger(DatabaseMySql.class);
	/**
	 * database user
	 */
	private String user = "";
	/**
	 * database password
	 */
	private String password = "";
	/**
	 * database driver class name
	 */
	private String driverClassName = "";
	/**
	 * database jdbc url
	 */
	private String url = "";

	private volatile static DatabaseMySql database;

	private DatabaseMySql() {}

	/**
	 * get DBUtil instance by singleton
	 * @return
	 */
	public static DatabaseMySql getInstance() {
		if(database == null) {
			synchronized(DatabaseMySql.class) {
				if (database == null) {
					database = new DatabaseMySql();
				}
			}
		}
		
		return database;
	}
	
	/**
	 * initialize database configuration
	 */
	public void initialize(Map<String, String> dbconfig) {
		if(dbconfig != null) {
			this.user = dbconfig.get(Const.mysqlUserName).trim();
			this.password = dbconfig.get(Const.mysqlPassword).trim();
			this.driverClassName = dbconfig.get(Const.mysqlDriverClassName).trim();
			this.url = dbconfig.get(Const.mysqlUrl);

			registerDriver();
		}
	}

	/**
	 * register database driver
	 */
	private void registerDriver() {
		try {
			Class.forName(driverClassName);
			logger.info("jdbc driver registed.");
		}catch(Exception e) {
			logger.error(e.toString(), e);
		}
	}
	/**
	 * get database connection
	 * @return
	 */
	private Connection getConnection() {
		try {
			logger.info("database connected successfully.");
			return DriverManager.getConnection(url, user, password);
		}catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return null;
	}

	/**
	 * execute insert, update and delete option
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Boolean executeUpdate(String sql, List<Object> params) {
		Boolean flag = false;
		int count = -1;
		Connection connection = this.getConnection();
		PreparedStatement statement = null;
		try {
			if(connection != null) {
				statement = connection.prepareStatement(sql);

				if(params != null && !params.isEmpty()) {
					int index = 1;
					for(int i = 0;i < params.size();i ++) {
						statement.setObject(index ++, params.get(i));
					}
				}
				
				count = statement.executeUpdate();
				flag = count > 0 ? true : false;
			}
		}catch(Exception e) {
			logger.error(e.toString(), e);
		}finally {
			this.closeConnection(connection,statement);
		}
		
		return flag;
	}

	public Integer executeInsert(String sql, List<Object> params) {
		int count = -1;
		Connection connection = this.getConnection();
		PreparedStatement statement = null;
		try {
			if(connection != null) {
				statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				if(params != null && !params.isEmpty()) {
					int index = 1;
					for(int i = 0;i < params.size();i ++) {
						statement.setObject(index ++, params.get(i));
					}
				}
				count = statement.executeUpdate();
				if (count > 0) {
					ResultSet generatedKeys = statement.getGeneratedKeys();
					if (generatedKeys.next()) {
						return generatedKeys.getInt(1);
					}else {
						throw new SQLException("insert failed, no ID obtained.");
					}
				} else {
					throw new SQLException("insert failed");
				}
			}
		}catch(Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}finally {
			this.closeConnection(connection,statement);
		}
		return null;
	}

	/**
	 * execute databse query
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> executeQuery(String sql, List<Object> params){
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Connection connection = this.getConnection();
		PreparedStatement statement = null;
		try {
			if(connection != null) {
				statement = connection.prepareStatement(sql);
				if(params != null && !params.isEmpty()) {
					int index = 1;
					for(int i = 0;i < params.size();i ++) {
						statement.setObject(index ++, params.get(i));
					}
				}
				
				ResultSet resultSet = statement.executeQuery();
				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnLen = metaData.getColumnCount();
				
				while(resultSet.next()) {
					Map<String, Object> elment = new HashMap<String, Object>();
					for(int i = 0;i < columnLen;i ++) {
						String columnName = metaData.getColumnName(i + 1);
						Object columnValue = resultSet.getObject(columnName);
						
						elment.put(columnName, columnValue);
					}
					resultList.add(elment);
				}
			}
		}catch(Exception e) {
			logger.error(e.toString(), e);
		}finally {
			this.closeConnection(connection,statement);
		}
		
		return resultList;
	}
	
	/**
	 * close database connection
	 */
	private void closeConnection(Connection connection, Statement statement) {
		try {
			if(statement != null) {
				statement.close();
			}
			
			if(connection != null) {
				connection.close();
			}
		}catch(Exception e) {
			logger.error(e.toString(), e);
		}
	}
}
