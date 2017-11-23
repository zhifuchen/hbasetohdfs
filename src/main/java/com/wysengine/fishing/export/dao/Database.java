package com.wysengine.fishing.export.dao;

import java.util.List;
import java.util.Map;

/** 
* @author Louis Yan 
* @since louis.yan@wysengine.com
* @version 2017年9月6日 下午1:47:43 
*  
*/

public interface Database {
	/**
	 * initialize database configuration
	 */
	public void initialize(Map<String, String> dbconfig);
	/**
	 * execute insert, update and delete sql statement
	 * @param sql
	 * @param params
	 * @return
	 */
	public Boolean executeUpdate(String sql, List<Object> params);
	/**
	 * query databse data
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> executeQuery(String sql, List<Object> params);

	/**
	 * execute Insert and return generated id
	 * @param sql
	 * @param params
	 * @return id
	 */
	public Integer executeInsert(String sql, List<Object> params);
}
