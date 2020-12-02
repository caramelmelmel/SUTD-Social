package com.example.sutd_social;

public class BulletinBoardPost {
    private String postTitle, postDescription, postdate;
    private String bulletin_url;

    public BulletinBoardPost(String postTitle, String postDescription) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
    }

    public BulletinBoardPost(String postTitle, String postDescription, String postdate) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postdate = postdate;
    }

    public BulletinBoardPost(String postTitle, String postDescription, String postdate, String bulletin_url) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postdate = postdate;
        this.bulletin_url = bulletin_url;
    }

    public String getBulletin_url() {
        return bulletin_url;
    }

    public void setBulletin_url(String bulletin_url) {
        this.bulletin_url = bulletin_url;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }
}
