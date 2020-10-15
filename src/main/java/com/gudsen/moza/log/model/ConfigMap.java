package com.gudsen.moza.log.model;

import java.io.Serializable;

/**
 * @author ruanmingcong 2020.8.4 17:33
 */
public class ConfigMap implements Serializable {
    private static final long serialVersionUID = 7097217774343988359L;

    public String key;
    public String value;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigMap{");
        sb.append("key='").append(key).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
