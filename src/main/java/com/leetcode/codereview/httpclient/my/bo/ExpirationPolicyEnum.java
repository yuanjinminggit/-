package com.leetcode.codereview.httpclient.my.bo;

public enum ExpirationPolicyEnum {
    AUTO("AUTO", "由应用工厂自行控制（10分钟没有会话则过期）"),
    NEVER("NEVER", "会话永不过期");

    private String description;
    private String value;

    ExpirationPolicyEnum(String value, String description) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }
}