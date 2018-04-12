package com.example.tolulopeontop.news;

/**
 * Created by Tolulopeontop on 3/29/2018.
 */

public class newsitem {

    private String newsHeading;
    private String newsDesc;
    private String newsDescSmall;
    private String time;
    private String date;
    private String url;
    private String imageId;

    public newsitem(String newsHeading, String newsDesc, String newsDescSmall, String time, String date, String url, String imageId) {
        this.newsHeading = newsHeading;
        this.newsDesc = newsDesc;
        if(newsDesc.length()>50){
        this.newsDescSmall = this.newsDesc.substring(0, 50) + "...";}
        else {this.newsDescSmall = newsDesc;}
        this.time = time;
        this.date = date;
        this.url = url;
        this.imageId = imageId;
    }



    public String getNewsHeading() {
        return newsHeading;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public String getNewsDescSmall() {
        return newsDescSmall;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getimageId() {
        return imageId;
    }
}
