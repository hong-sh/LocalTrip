package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 6. 2..
 */

public class CourseThumbNail {

    private int courseIdx;
    private String courseImage;
    private String courseName;
    private String userImage;
    private String userName;
    private Float courseRate;
    private int coursePrice;


    public int getCourseIdx() {
        return courseIdx;
    }

    public void setCourseIdx(int courseIdx) {
        this.courseIdx = courseIdx;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Float getCourseRate() {
        return courseRate;
    }

    public void setCourseRate(Float courseRate) {
        this.courseRate = courseRate;
    }

    public int getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(int coursePrice) {
        this.coursePrice = coursePrice;
    }
}
