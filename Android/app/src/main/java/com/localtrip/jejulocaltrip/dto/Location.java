package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 5. 23..
 */

public class Location {

    private int locationIdx;
    private String locationName;
    private String locationInfo;
    private String locationImage1;
    private String locationImage2;
    private String locationAddr;
    private String locationTel;
    private float locationTotalRate;
    private int locationTotalReivew;
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

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getLocationImage1() {
        return locationImage1;
    }

    public void setLocationImage1(String locationImage1) {
        this.locationImage1 = locationImage1;
    }

    public String getLocationImage2() {
        return locationImage2;
    }

    public void setLocationImage2(String locationImage2) {
        this.locationImage2 = locationImage2;
    }

    public String getLocationAddr() {
        return locationAddr;
    }

    public void setLocationAddr(String locationAddr) {
        this.locationAddr = locationAddr;
    }

    public String getLocationTel() {
        return locationTel;
    }

    public void setLocationTel(String locationTel) {
        this.locationTel = locationTel;
    }

    public float getLocationTotalRate() {
        return locationTotalRate;
    }

    public void setLocationTotalRate(float locationTotalRate) {
        this.locationTotalRate = locationTotalRate;
    }

    public int getLocationTotalReivew() {
        return locationTotalReivew;
    }

    public void setLocationTotalReivew(int locationTotalReivew) {
        this.locationTotalReivew = locationTotalReivew;
    }

    public String getDetailCourseImage() {
        return detailCourseImage;
    }

    public void setDetailCourseImage(String detailCourseImage) {
        this.detailCourseImage = detailCourseImage;
    }
}
