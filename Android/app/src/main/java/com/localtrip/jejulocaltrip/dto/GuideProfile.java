package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 5. 23..
 */

public class GuideProfile {

    private int userIdx;
    private String userName;
    private int guideIdx;
    private String guideIntroduce;
    private int guideFirstLanguage;
    private String guideAvailableLanguage;
    private String guideCapability;
    private float guideTotalRate;

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

    public int getGuideIdx() {
        return guideIdx;
    }

    public void setGuideIdx(int guideIdx) {
        this.guideIdx = guideIdx;
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

    public float getGuideTotalRate() {
        return guideTotalRate;
    }

    public void setGuideTotalRate(float guideTotalRate) {
        this.guideTotalRate = guideTotalRate;
    }
}
