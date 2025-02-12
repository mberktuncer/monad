package com.mberktuncer.monad.model;

public class Employee {

    private String TC;
    private String ad;
    private String soyad;

    public Employee() {
    }

    public Employee(String TC, String ad, String soyad) {
        this.TC = TC;
        this.ad = ad;
        this.soyad = soyad;
    }

    public String getTC() {
        return TC;
    }

    public void setTC(String TC) {
        this.TC = TC;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }
}
