package com.wysengine.fishing.export.service;

import com.wysengine.fishing.export.dao.Database;
import com.wysengine.fishing.export.dao.DatabaseFactory;
import com.wysengine.fishing.export.entity.DatabaseType;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
* @author Louis Yan 
* @since louis.yan@wysengine.com
* @version 2017年9月6日 下午7:34:10 
*  
*/

public class TurbineService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Database database;
	
	public TurbineService(Map<String, String> config) {
		database = DatabaseFactory.createDatabase(DatabaseType.mysql, config);
	}
	
	public List<Map<String, Object>> getTurbineListByProject(String projectId){
		List<Map<String, Object>> turbineList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT GUID, FARM_CODE, FARM_NAME, CODE_, LOCATION_CODE, ENABLE_, MANUFACTURER_, MODEL_, MODEL_DETAIL, RATED_POWER, ROTOR_DIAMETER, HUB_HEIGHT, CUTIN_SPEED, RATED_WIND_SPEED, CUTOUT_SPEED, EXTREME_WIND_SPEED, EXPECT_LIFE, AVAILABILITY_, COORDINATE_, ALTITUDE_, IF_IN_SEA, IF_HIGHLAND, STATUS_TAG, MASTER_TYPE, TRANSDUCER_TYPE, BOX_TRANSFORMER, TOWER_TYPE, PLC_VERSION, TRANSDUCER_VERSION, PITCH_VERSION, PITCH_TYPE, MASTER_VERSION, TURBINE_CONFIG FROM tb_wind_base_wtgs WHERE FARM_CODE=?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(projectId);
		turbineList = database.executeQuery(sql, paramList);
		
		return turbineList;
	}
	public int getTurbineCountByProject(String projectCode){
		String sql = "SELECT count(*) count FROM tb_wind_base_wtgs WHERE FARM_CODE=?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(projectCode);
		List<Map<String, Object>> turbineList = database.executeQuery(sql, paramList);
		return Integer.parseInt(String.valueOf(turbineList.get(0).get("count")));
	}

	public List<Map<String, Object>> getRunModelTurbines() {
		String sql = "SELECT w.GUID, w.FARM_CODE, w.FARM_NAME, w.CODE_, w.LOCATION_CODE, w.ENABLE_, w.MANUFACTURER_, w.MODEL_, w.MODEL_DETAIL, w.RATED_POWER, w.ROTOR_DIAMETER, w.HUB_HEIGHT, w.CUTIN_SPEED, w.RATED_WIND_SPEED, w.CUTOUT_SPEED, w.EXTREME_WIND_SPEED, w.EXPECT_LIFE, w.AVAILABILITY_, w.COORDINATE_, w.ALTITUDE_, w.IF_IN_SEA, w.IF_HIGHLAND, w.STATUS_TAG, w.MASTER_TYPE, w.TRANSDUCER_TYPE, w.BOX_TRANSFORMER, w.TOWER_TYPE, w.PLC_VERSION, w.TRANSDUCER_VERSION, w.PITCH_VERSION, w.PITCH_TYPE, w.MASTER_VERSION, w.TURBINE_CONFIG FROM tb_wind_base_wtgs w inner join tb_wind_base_farm f on w.farm_code = f.CODE_ where f.`RUN_MODEL` = 1 ";
//		String sql = "SELECT w.GUID, w.FARM_CODE, w.FARM_NAME, w.CODE_, w.LOCATION_CODE, w.ENABLE_, w.MANUFACTURER_, w.MODEL_, w.MODEL_DETAIL, w.RATED_POWER, w.ROTOR_DIAMETER, w.HUB_HEIGHT, w.CUTIN_SPEED, w.RATED_WIND_SPEED, w.CUTOUT_SPEED, w.EXTREME_WIND_SPEED, w.EXPECT_LIFE, w.AVAILABILITY_, w.COORDINATE_, w.ALTITUDE_, w.IF_IN_SEA, w.IF_HIGHLAND, w.STATUS_TAG, w.MASTER_TYPE, w.TRANSDUCER_TYPE, w.BOX_TRANSFORMER, w.TOWER_TYPE, w.PLC_VERSION, w.TRANSDUCER_VERSION, w.PITCH_VERSION, w.PITCH_TYPE, w.MASTER_VERSION, w.TURBINE_CONFIG FROM tb_wind_base_wtgs w where w.CODE_ = '20002004'";
		return database.executeQuery(sql,null);
	}

	public Map<String, Object> getTurbineByCode(String code) {
		String sql = "SELECT GUID, FARM_CODE, FARM_NAME, CODE_, LOCATION_CODE, ENABLE_, MANUFACTURER_, MODEL_, MODEL_DETAIL, RATED_POWER, ROTOR_DIAMETER, HUB_HEIGHT, CUTIN_SPEED, RATED_WIND_SPEED, CUTOUT_SPEED, EXTREME_WIND_SPEED, EXPECT_LIFE, AVAILABILITY_, COORDINATE_, ALTITUDE_, IF_IN_SEA, IF_HIGHLAND, STATUS_TAG, MASTER_TYPE, TRANSDUCER_TYPE, BOX_TRANSFORMER, TOWER_TYPE, PLC_VERSION, TRANSDUCER_VERSION, PITCH_VERSION, PITCH_TYPE, MASTER_VERSION, TURBINE_CONFIG FROM tb_wind_base_wtgs WHERE CODE_ = ?";
		List<Object> paramList = new ArrayList<>();
		paramList.add(code);
		List<Map<String, Object>> list = database.executeQuery(sql, paramList);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
