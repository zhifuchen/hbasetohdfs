package com.wysengine.fishing.export.entity;

import com.wysengine.fishing.export.util.DateUtil;

import java.util.List;

/**
 * Created by chenzhifu on 2017/11/20.
 */
public class Schema {

    /**
     * type : record
     * namespace : com.wysengine.fishing.mywind
     * name : exportParquet
     * fields : [{"name":"first","type":"string"},{"name":"last","type":"string"}]
     */

    private String type;
    private String namespace;
    private String name;
    private List<FieldsBean> fields;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FieldsBean> getFields() {
        return fields;
    }

    public void setFields(List<FieldsBean> fields) {
        this.fields = fields;
    }

    public static class FieldsBean {
        /**
         * name : first
         * type : string
         * default: string
         * order:
         */

        private String name;
        private Object type;
        private String default_;
        private String order;

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getDefault() {
            return default_;
        }

        public void setDefault(String default_) {
            this.default_ = default_;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }
    }

    public static void main(String[] args) {
        Long start = DateUtil.getTimestamp("2017-10-20 00:00:00", DateUtil.longDateTimeFormatWithSplit);
        Long end = DateUtil.getTimestamp("2017-11-20 00:00:00", DateUtil.longDateTimeFormatWithSplit);
        System.out.println(start);
        System.out.println(end);

    }
}
