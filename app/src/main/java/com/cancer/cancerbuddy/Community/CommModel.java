package com.cancer.cancerbuddy.Community;

public class CommModel {
        String Date,Desc,Image,PostId,User_ID,User_Name;

    public CommModel() {
    }

    public CommModel(String date, String desc, String image, String postId, String user_ID, String user_Name) {
        Date = date;
        Desc = desc;
        Image = image;
        PostId = postId;
        User_ID = user_ID;
        User_Name = user_Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }
}