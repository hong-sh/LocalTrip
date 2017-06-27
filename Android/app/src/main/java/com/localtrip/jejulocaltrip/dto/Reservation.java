package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 5. 21..
 */

public class Reservation {

    private int reservationIdx;
    private int userIdx;
    private int courseIdx;
    private int reservationPersonNumber;
    private String reservationDate;
    private String reservationRequest;
    private int reservationPrice;
    private String reservationPhone;
    private String userName;
    private String userEmail;
    private String courseImage;
    private String courseName;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getReservationIdx() {
        return reservationIdx;
    }

    public void setReservationIdx(int reservationIdx) {
        this.reservationIdx = reservationIdx;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public int getCourseIdx() {
        return courseIdx;
    }

    public void setCourseIdx(int courseIdx) {
        this.courseIdx = courseIdx;
    }

    public int getReservationPersonNumber() {
        return reservationPersonNumber;
    }

    public void setReservationPersonNumber(int reservationPersonNumber) {
        this.reservationPersonNumber = reservationPersonNumber;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationRequest() {
        return reservationRequest;
    }

    public void setReservationRequest(String reservationRequest) {
        this.reservationRequest = reservationRequest;
    }

    public int getReservationPrice() {
        return reservationPrice;
    }

    public void setReservationPrice(int reservationPrice) {
        this.reservationPrice = reservationPrice;
    }

    public String getReservationPhone() {
        return reservationPhone;
    }

    public void setReservationPhone(String reservationPhone) {
        this.reservationPhone = reservationPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
