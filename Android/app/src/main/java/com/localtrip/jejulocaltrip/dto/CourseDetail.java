package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 5. 21..
 */

public class CourseDetail {

    private int locationIdx;
    private String locationName;
    private float locationTotalRate;
    private int detailCourseTime;
    private String detailCourseInfo;
    private String detailCourseImage;

    public int getLocationIdx() {
        return locationIdx;
    }

    public void setLocationIdx(int locationIdx) {
        this.locationIdx = locationIdx;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public float getLocationTotalRate() {
        return locationTotalRate;
    }

    public void setLocationTotalRate(float locationTotalRate) {
        this.locationTotalRate = locationTotalRate;
    }

    public int getDetailCourseTime() {
        return detailCourseTime;
    }

    public void setDetailCourseTime(int detailCourseTime) {
        this.detailCourseTime = detailCourseTime;
    }

    public String getDetailCourseInfo() {
        return detailCourseInfo;
    }

    public void setDetailCourseInfo(String detailCourseInfo) {
        this.detailCourseInfo = detailCourseInfo;
    }

    public String getDetailCourseImage() {
        return detailCourseImage;
    }

    public void setDetailCourseImage(String detailCourseImage) {
        this.detailCourseImage = detailCourseImage;
    }
}
