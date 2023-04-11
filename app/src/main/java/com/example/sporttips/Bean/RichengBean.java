package com.example.sporttips.Bean;


import java.io.Serializable;

public class RichengBean implements Serializable {
    private int id;
    private String name;
    private String qiangdu;
    private int sum;
    private String sport;
    private String jihua;
    private int year;
    private int month;
    private int day;
    private String date;
    private String done;

    public RichengBean(int id,String name,String qiangdu,int sum,String sport,String jihua,int year,int month,int day,String date,String done){
        this.id = id;
        this.name = name;
        this.qiangdu = qiangdu;
        this.sum = sum;
        this.sport = sport;
        this.jihua = jihua;
        this.year = year;
        this.month = month;
        this.day = day;
        this.date = date;
        this.done = done;
    }

    public String getDone() {
        return done;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getSum() {
        return sum;
    }

    public int getYear() {
        return year;
    }

    public String getDate() {
        return date;
    }

    public String getJihua() {
        return jihua;
    }

    public String getQiangdu() {
        return qiangdu;
    }

    public String getSport() {
        return sport;
    }
}
