package com.iutbm.example.iutbm.couchot.healthybody;

import java.io.Serializable;

public class Test implements Serializable {

    String ID;
    Integer P0;
    Integer P1;
    Integer P2;
    String date;

    public Test(String ID, Integer p0, Integer p1, Integer p2,String date) {
        this.ID = ID;
        P0 = p0;
        P1 = p1;
        P2 = p2;
        this.date=date;
    }

    public Test() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getP0() {
        return P0;
    }

    public void setP0(Integer p0) {
        P0 = p0;
    }

    public Integer getP1() {
        return P1;
    }

    public void setP1(Integer p1) {
        P1 = p1;
    }

    public Integer getP2() {
        return P2;
    }

    public void setP2(Integer p2) {
        P2 = p2;
    }
}
