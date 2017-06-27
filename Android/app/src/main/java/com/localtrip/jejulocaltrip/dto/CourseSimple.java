package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 5. 23..
 */

public class CourseSimple {

    private int courseIdx;
    private String courseName;
    private String courseImage;
    private int coursePriceMin;

    public int getCourseIdx() {
        return courseIdx;
    }

    public void setCourseIdx(int courseIdx) {
        this.courseIdx = courseIdx;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public int getCoursePriceMin() {
        return coursePriceMin;
    }

    public void setCoursePriceMin(int coursePriceMin) {
        this.coursePriceMin = coursePriceMin;
    }
}
