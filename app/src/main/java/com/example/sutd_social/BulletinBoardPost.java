package com.example.sutd_social;

public class BulletinBoardPost {
    private String postTitle, postDescription,postdate;

    public BulletinBoardPost(String postTitle, String postDescription) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
    }

    public BulletinBoardPost(String postTitle, String postDescription, String postdate) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postdate = postdate;
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
