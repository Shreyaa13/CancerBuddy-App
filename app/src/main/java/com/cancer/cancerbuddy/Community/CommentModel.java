package com.cancer.cancerbuddy.Community;

public class CommentModel {
    String UserId,Date,Comm,PostId;

    public CommentModel() {
    }

    public CommentModel(String userId, String date, String comm, String postId) {
        UserId = userId;
        Date = date;
        Comm = comm;
        PostId = postId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getComm() {
        return Comm;
    }

    public void setComm(String comm) {
        Comm = comm;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }
}
