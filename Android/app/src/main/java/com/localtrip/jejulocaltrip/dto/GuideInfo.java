package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 5. 23..
 */

public class GuideInfo {

    private int userIdx;
    private String userName;
    private String userImage;
    private String guideImage;
    private String guideIntroduce;
    private int guideFirstLanguage;
    private String guideAvailableLanguage;
    private String guideCapability;
    private Float guideTotalRate;
    private int guideTotalReview;
    private int guideTotalCourse;

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getGuideImage() {
        return guideImage;
    }

    public void setGuideImage(String guideImage) {
        this.guideImage = guideImage;
    }

    public String getGuideIntroduce() {
        return guideIntroduce;
    }

    public void setGuideIntroduce(String guideIntroduce) {
        this.guideIntroduce = guideIntroduce;
    }

    public int getGuideFirstLanguage() {
        return guideFirstLanguage;
    }

    public void setGuideFirstLanguage(int guideFirstLanguage) {
        this.guideFirstLanguage = guideFirstLanguage;
    }

    public String getGuideAvailableLanguage() {
        return guideAvailableLanguage;
    }

    public void setGuideAvailableLanguage(String guideAvailableLanguage) {
        this.guideAvailableLanguage = guideAvailableLanguage;
    }

    public String getGuideCapability() {
        return guideCapability;
    }

    public void setGuideCapability(String guideCapability) {
        this.guideCapability = guideCapability;
    }

    public Float getGuideTotalRate() {
        return guideTotalRate;
    }

    public void setGuideTotalRate(Float guideTotalRate) {
        this.guideTotalRate = guideTotalRate;
    }

    public int getGuideTotalReview() {
        return guideTotalReview;
    }

    public void setGuideTotalReview(int guideTotalReview) {
        this.guideTotalReview = guideTotalReview;
    }

    public int getGuideTotalCourse() {
        return guideTotalCourse;
    }

    public void setGuideTotalCourse(int guideTotalCourse) {
        this.guideTotalCourse = guideTotalCourse;
    }
}
