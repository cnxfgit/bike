package com.hlx.vbbike.model;

public class Bill {
    private String bid;
    private String rtime;
    private String btime;
    private String time;
    private String ispay;
    public String getBid() {
        return bid;
    }
    public void setBid(String bid) {
        this.bid = bid;
    }
    public String getRtime() {
        return rtime;
    }
    public void setRtime(String rtime) {
        this.rtime = rtime;
    }
    public String getBtime() {
        return btime;
    }
    public void setBtime(String btime) {
        this.btime = btime;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getIspay() {
        return ispay;
    }
    public void setIspay(String ispay) {
        this.ispay = ispay;
    }
    @Override
    public String toString() {
        return "Bill [bid=" + bid + ", rtime=" + rtime + ", btime=" + btime + ", time=" + time + ", ispay=" + ispay
                + "]";
    }

    public Bill() {

    }

    public Bill(String bid,String btime,String rtime,String time,String ispay) {
        this.bid = bid;
        this.btime = btime;
        this.rtime = rtime;
        this.time = time;
        this.ispay = ispay;
    }
}