package com.cancer.cancerbuddy.Record_Sys;

public class TrackRecordModel {

    String Id,Title;

    public TrackRecordModel() {
    }

    public TrackRecordModel(String id, String title) {
        Id = id;
        Title = title;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
