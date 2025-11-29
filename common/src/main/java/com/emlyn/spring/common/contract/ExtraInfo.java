package com.emlyn.spring.common.contract;

import java.util.HashMap;

public class ExtraInfo extends HashMap<String, Object> {

    public String getStr(String key) {
        Object value = this.get(key);
        return value != null ? value.toString() : null;
    }

    public Integer getInt(String key) {
        Object value = this.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return null;
    }

    public Boolean getBol(String key) {
        Object value = this.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return null;
    }

}
