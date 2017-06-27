package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 5. 25..
 */

public class GuideSimple {

    private int userIdx;
    private String userName;
    private String userImage;
    private String guideIntroduce;
    private int guideIdx;

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

    public String getGuideIntroduce() {
        return guideIntroduce;
    }

    public void setGuideIntroduce(String guideIntroduce) {
        this.guideIntroduce = guideIntroduce;
    }

    public int getGuideIdx() {
        return guideIdx;
    }

    public void setGuideIdx(int guideIdx) {
        this.guideIdx = guideIdx;
    }
}
