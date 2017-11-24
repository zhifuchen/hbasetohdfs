package com.wysengine.fishing.export.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
*/

public class PropertyUtil {
	private static Logger logger = Logger.getLogger(PropertyUtil.class);
	private static final Properties properties = new Properties();
	static {
		try {
			try (final InputStream stream = PropertyUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
                properties.load(stream);
            }
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}

	public static String getProperty(String property) {
		return String.valueOf(properties.get(property));
	}

	public static Map<String,String> getPropertyMap() {
		Map<String, String> map = new HashMap<>();
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
		}
		return map;
	}

}
