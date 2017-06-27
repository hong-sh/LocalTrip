package com.localtrip.jejulocaltrip.dto;

/**
 * Created by hong on 2017. 6. 3..
 */

public class WriteReview {

    private int type; //0 = course , 1=guide , 2=location
    private int idx;
    private String title;
    private String content;
    private Float rate;

    public WriteReview() {
        this.content = "";
        this.rate = 0.0f;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
