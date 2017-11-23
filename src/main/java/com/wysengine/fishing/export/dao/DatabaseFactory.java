package com.wysengine.fishing.export.dao;


import com.wysengine.fishing.export.entity.DatabaseType;

import java.util.Map;

/** 
* @author Louis Yan 
* @since louis.yan@wysengine.com
* @version 2017年9月6日 下午1:47:55 
*  
*/

public class DatabaseFactory {
	/**
	 * create database by database type
	 * @param databaseType
	 * @param dbconfig
	 * @return
	 */
	public static Database createDatabase(DatabaseType databaseType, Map<String, String> dbconfig) {
		Database database;
		if(databaseType.equals(DatabaseType.mysql)) {
			database = DatabaseMySql.getInstance();
			database.initialize(dbconfig);
			
			return database;
		}
		
		return null;
	}
}
