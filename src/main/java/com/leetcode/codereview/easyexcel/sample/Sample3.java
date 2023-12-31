package com.leetcode.codereview.easyexcel.sample;

import com.alibaba.excel.annotation.ExcelProperty;

public class Sample3 {

    @ExcelProperty("用户")
    private String user;

    @ExcelProperty("用户类型")
    private String usertype;
    @ExcelProperty("用户类型")
    private String productType;

    @ExcelProperty("使用前完单天数")
    private Integer beforeDay;

    @ExcelProperty("使用后完单天数")
    private Integer afterDay;

    @ExcelProperty("使用前完单量")
    private Integer beforeNum;
    @ExcelProperty("使用后完单量")
    private Integer afterNum;

    @ExcelProperty("使用前收入")
    private Double beforeIncome;

    @ExcelProperty("使用后收入")
    private Double afterIncome;

    @ExcelProperty("使用前补贴")
    private Double beforeSubsidy;


    @ExcelProperty("使用后补贴")
    private Double afterSubsidy;

    @Override
    public String toString() {
        return "Sample3{" +
                "user='" + user + '\'' +
                ", usertype='" + usertype + '\'' +
                ", productType='" + productType + '\'' +
                ", beforeDay=" + beforeDay +
                ", afterDay=" + afterDay +
                ", beforeNum=" + beforeNum +
                ", afterNum=" + afterNum +
                ", beforeIncome=" + beforeIncome +
                ", afterIncome=" + afterIncome +
                ", beforeSubsidy=" + beforeSubsidy +
                ", afterSubsidy=" + afterSubsidy +
                '}';
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getBeforeDay() {
        return beforeDay;
    }

    public void setBeforeDay(Integer beforeDay) {
        this.beforeDay = beforeDay;
    }

    public Integer getAfterDay() {
        return afterDay;
    }

    public void setAfterDay(Integer afterDay) {
        this.afterDay = afterDay;
    }

    public Integer getBeforeNum() {
        return beforeNum;
    }

    public void setBeforeNum(Integer beforeNum) {
        this.beforeNum = beforeNum;
    }

    public Integer getAfterNum() {
        return afterNum;
    }

    public void setAfterNum(Integer afterNum) {
        this.afterNum = afterNum;
    }

    public Double getBeforeIncome() {
        return beforeIncome;
    }

    public void setBeforeIncome(Double beforeIncome) {
        this.beforeIncome = beforeIncome;
    }

    public Double getAfterIncome() {
        return afterIncome;
    }

    public void setAfterIncome(Double afterIncome) {
        this.afterIncome = afterIncome;
    }

    public Double getBeforeSubsidy() {
        return beforeSubsidy;
    }

    public void setBeforeSubsidy(Double beforeSubsidy) {
        this.beforeSubsidy = beforeSubsidy;
    }

    public Double getAfterSubsidy() {
        return afterSubsidy;
    }

    public void setAfterSubsidy(Double afterSubsidy) {
        this.afterSubsidy = afterSubsidy;
    }
}
