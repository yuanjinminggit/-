package com.leetcode.codereview.easyexcel.sample;

import com.alibaba.excel.annotation.ExcelProperty;

public class Sample1 {

    @ExcelProperty("星期")
    private String week;

    @ExcelProperty("时段")
    private Integer period;
    @ExcelProperty("城市")
    private String city;

    @ExcelProperty("冒泡数")
    private Integer bubNum;

    @ExcelProperty("呼叫数")
    private Integer callNum;

    @ExcelProperty("应答数")
    private Integer resNUm;
    @ExcelProperty("完单数")
    private Integer comNum;

    @ExcelProperty("司机在线")
    private Integer onlineNum;

    @Override
    public String toString() {
        return "Sample1{" +
                "week='" + week + '\'' +
                ", period=" + period +
                ", city=" + city +
                ", bubNum=" + bubNum +
                ", callNum=" + callNum +
                ", resNUm=" + resNUm +
                ", comNum=" + comNum +
                ", onlineNum=" + onlineNum +
                '}';
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getBubNum() {
        return bubNum;
    }

    public void setBubNum(Integer bubNum) {
        this.bubNum = bubNum;
    }

    public Integer getCallNum() {
        return callNum;
    }

    public void setCallNum(Integer callNum) {
        this.callNum = callNum;
    }

    public Integer getResNUm() {
        return resNUm;
    }

    public void setResNUm(Integer resNUm) {
        this.resNUm = resNUm;
    }

    public Integer getComNum() {
        return comNum;
    }

    public void setComNum(Integer comNum) {
        this.comNum = comNum;
    }

    public Integer getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }
}
