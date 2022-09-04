package com.cancer.cancerbuddy.Record_Sys;

public class TModel {

    String Date,Desc,Infection,Title;

    public TModel() {
    }

    public TModel(String date, String desc, String infection, String title) {
        Date = date;
        Desc = desc;
        Infection = infection;
        Title = title;
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

    public String getInfection() {
        return Infection;
    }

    public void setInfection(String infection) {
        Infection = infection;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
