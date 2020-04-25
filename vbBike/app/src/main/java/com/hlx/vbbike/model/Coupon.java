package com.hlx.vbbike.model;

public class Coupon {

    private String zhe;

    public String getZhe() {
        return zhe;
    }

    public void setZhe(String zhe) {
        this.zhe = zhe;
    }

    public Coupon(){

    }
    public Coupon(String zhe){
        this.zhe = zhe;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "zhe='" + zhe + '\'' +
                '}';
    }
}
