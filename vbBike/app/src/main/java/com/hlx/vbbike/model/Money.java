package com.hlx.vbbike.model;

public class Money {
    private String date;
    private String iscoupon;
    private String pay;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIscoupon() {
        return iscoupon;
    }

    public void setIscoupon(String iscoupon) {
        this.iscoupon = iscoupon;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "Money{" +
                "date='" + date + '\'' +
                ", iscoupon='" + iscoupon + '\'' +
                ", pay='" + pay + '\'' +
                '}';
    }

    public Money(String date, String iscoupon, String pay) {
        this.date = date;
        this.iscoupon = iscoupon;
        this.pay = pay;
    }

    public Money() {
    }
}
