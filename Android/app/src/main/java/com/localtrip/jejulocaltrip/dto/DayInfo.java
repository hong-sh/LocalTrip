package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 6. 1..
 */

public class DayInfo {

    private String day;
    private String year;
    private String month;
    private boolean inMonth;
    private boolean inCourse;
    private boolean selected;

    public DayInfo(){
        inCourse = false;
        selected = false;
    }

    public DayInfo(String day, String year, String month, boolean inMonth, boolean inCourse) {
        this.day = day;
        this.year = year;
        this.month = month;
        this.inCourse = inCourse;
        this.inMonth = inMonth;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public boolean isInMonth() {
        return inMonth;
    }

    public void setInMonth(boolean inMonth) {
        this.inMonth = inMonth;
    }

    public boolean isInCourse() {
        return inCourse;
    }

    public void setInCourse(boolean inCourse) {
        this.inCourse = inCourse;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
