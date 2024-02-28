package com.leetcode.codereview.httpclient.my.enums;

public enum UserTypeEnum {
    MIS("MIS", "MIS"),
    MEITUAN("MEITUAN", "美团账号"),
    EMP_ID("EMP_ID", "美团员工ID"),
    DX_UID("DX_UID", "大象UID"),
    WE_CHAT("WE_CHAT", "微信账号");

    private String value;
    private String description;

    UserTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}