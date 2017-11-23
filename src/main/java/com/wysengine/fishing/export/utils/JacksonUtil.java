package com.wysengine.fishing.export.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenzhifu on 2017/8/11.
 */
public class JacksonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static Map<String, Object> json2Map(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        HashMap<String, Object> map = null;
        try {
            map = objectMapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return map;
    }
    public static List<Map<String, Object>> json2List(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        List<Map<String, Object>> list = null;
        try {
            list = objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return list;
    }

    public static String object2Json(Object object) {
        if (object == null) {
            return null;
        }
        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return json;
    }
    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhifu");
        map.put("age", "11");
        map.put("time", new Date());
        list.add(map);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "jj");
        map2.put("age", "14");
        list.add(map2);
        String json = object2Json(list);
        System.out.println(json);
        List<Map<String, Object>> list1 = json2List(json);
        System.out.println(list1);

    }
}
