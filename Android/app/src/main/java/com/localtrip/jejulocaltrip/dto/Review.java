package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 5. 24..
 */

public class Review {

    private String userName;
    private String userImage;
    private float reviewRate;
    private String reviewContent;
    private String reviewRegDate;

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

    public float getReviewRate() {
        return reviewRate;
    }

    public void setReviewRate(float reviewRate) {
        this.reviewRate = reviewRate;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewRegDate() {
        return reviewRegDate;
    }

    public void setReviewRegDate(String reviewRegDate) {
        this.reviewRegDate = reviewRegDate;
    }
}
