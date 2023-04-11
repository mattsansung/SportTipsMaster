package com.example.sporttips.Bean;

public class SportBean {
    private int id;
    private String name;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private String sport;
    private int time;
    public SportBean(int id,String name,int year,int month,int day,int hour,int min,String sport,int time){
        this.id = id;
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.time = time;
        this.sport = sport;

    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getSport() {
        return sport;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }
}
