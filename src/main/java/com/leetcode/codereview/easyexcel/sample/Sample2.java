package com.leetcode.codereview.easyexcel.sample;

import com.alibaba.excel.annotation.ExcelProperty;

public class Sample2 {
    @ExcelProperty("日期")
    private String date;

    @ExcelProperty("司机编号")
    private Integer driverNo;
    @ExcelProperty("平均星级")
    private Double star;

    @ExcelProperty("在线时长")
    private Double onlineTime;

    @ExcelProperty("完成订单数")
    private Integer orderComplete;

    @ExcelProperty("订单实际总公里数")
    private Double totalKilo;
    @ExcelProperty("车费收入")
    private Double income;

    @ExcelProperty("iph")
    private Double iph;

    @ExcelProperty("tph")
    private Double tph;

    @ExcelProperty("补贴")
    private Integer subsidy;

    @Override
    public String toString() {
        return "Sample2{" +
                "date='" + date + '\'' +
                ", driverNo=" + driverNo +
                ", star=" + star +
                ", onlineTime=" + onlineTime +
                ", orderComplete=" + orderComplete +
                ", totalKilo=" + totalKilo +
                ", income=" + income +
                ", iph=" + iph +
                ", tph=" + tph +
                ", subsidy=" + subsidy +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDriverNo() {
        return driverNo;
    }

    public void setDriverNo(Integer driverNo) {
        this.driverNo = driverNo;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public Double getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Double onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Integer getOrderComplete() {
        return orderComplete;
    }

    public void setOrderComplete(Integer orderComplete) {
        this.orderComplete = orderComplete;
    }

    public Double getTotalKilo() {
        return totalKilo;
    }

    public void setTotalKilo(Double totalKilo) {
        this.totalKilo = totalKilo;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getIph() {
        return iph;
    }

    public void setIph(Double iph) {
        this.iph = iph;
    }

    public Double getTph() {
        return tph;
    }

    public void setTph(Double tph) {
        this.tph = tph;
    }

    public Integer getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(Integer subsidy) {
        this.subsidy = subsidy;
    }
}