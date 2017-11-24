package com.wysengine.fishing.export.service;

import com.wysengine.fishing.export.ExportHBaseTableToParquet;
import com.wysengine.fishing.export.dao.Database;
import com.wysengine.fishing.export.dao.DatabaseFactory;
import com.wysengine.fishing.export.entity.DatabaseType;
import com.wysengine.fishing.export.entity.Schema;
import com.wysengine.fishing.export.util.JacksonUtil;
import com.wysengine.fishing.export.util.PropertyUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
* @author Louis Yan 
* @since louis.yan@wysengine.com
* @version 2017年9月11日 下午3:29:53 
*  
*/

public class TurbineConfigService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * database
	 */
	private Database database;
	/**
	 * constructor
	 * @param config
	 */
	public TurbineConfigService(Map<String, String> config) {
		database = DatabaseFactory.createDatabase(DatabaseType.mysql, config);
	}
	/**
	 * get all of the turbine config list
	 * @return
	 */
	public List<Map<String, Object>> getTurbineConfigList(){
		List<Map<String, Object>> turbineConfigList = new ArrayList<Map<String, Object>>();
		String sql = "select ID,TAG_NAME,TAG_NAME_COM,DESCRIPTION,TURBINE_MODEL from dv_turbine_config";
		turbineConfigList = database.executeQuery(sql, null);
		
		return turbineConfigList;
	}
	public List<Map<String, Object>> getTurbineConfigList4Turbine(String turbineCode){
		List<Map<String, Object>> turbineConfigList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT c.TAG_NAME_COM,(SELECT description from dv_turbine_config c2 where c2.TAG_NAME_COM = c.TAG_NAME_COM limit 1) DESCRIPTION " +
				"    FROM dv_turbine_config c " +
				"      INNER JOIN tb_wind_base_wtgs w on w.MODEL_ = c.TURBINE_MODEL " +
				"    WHERE w.CODE_ = ? and c.description != '' and c.description is not NULL GROUP BY c.TAG_NAME_COM ORDER BY c.ID";
		List<Object> params = new ArrayList<Object>();
		params.add(turbineCode);
		turbineConfigList = database.executeQuery(sql, params);
		return turbineConfigList;
	}

	public static String getTurbineSchema(String turbineCode) {

		TurbineConfigService turbineConfigService = new TurbineConfigService(PropertyUtil.getPropertyMap());
		List<Map<String, Object>> turbineConfigList = turbineConfigService.getTurbineConfigList4Turbine(turbineCode);
		Schema schema = new Schema();
		schema.setType("record");
		schema.setNamespace("com.wysengine.fishing.mywind");
		schema.setName("exportParquet_" + turbineCode);
		List<Schema.FieldsBean> fieldsBeanList = new ArrayList<>();
		String[] type = new String[]{"null","string"};
		for (Map<String, Object> map : turbineConfigList) {
			Schema.FieldsBean fieldsBean = new Schema.FieldsBean();
			fieldsBean.setName(String.valueOf(map.get("TAG_NAME_COM")).trim());
			fieldsBean.setType(type);
			fieldsBean.setDefault("");
			fieldsBean.setOrder("ignore");
			fieldsBeanList.add(fieldsBean);
		}
		Schema.FieldsBean fieldsBean = new Schema.FieldsBean();
		fieldsBean.setName(ExportHBaseTableToParquet.ROW_KEY);
		fieldsBean.setType("string");
		fieldsBean.setOrder("ascending");
		fieldsBeanList.add(fieldsBean);
		schema.setFields(fieldsBeanList);
		return JacksonUtil.object2Json(schema);
	}

	public static void main(String[] args) {
		String turbineCode = "20005010";
		String turbineSchema = getTurbineSchema(turbineCode);
		System.out.println(turbineSchema);
	}
}
