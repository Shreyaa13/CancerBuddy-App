package com.cancer.cancerbuddy.medicine;

public class MedicineModel {

    String txtA,txtB,txtId,txtTime,Alarm;

    public MedicineModel() {
    }

    public MedicineModel(String txtA, String txtB, String txtC, String txtId, String txtTime, String alarm) {
        this.txtA = txtA;
        this.txtB = txtB;

        this.txtId = txtId;
        this.txtTime = txtTime;
        Alarm = alarm;
    }

    public String getTxtA() {
        return txtA;
    }

    public void setTxtA(String txtA) {
        this.txtA = txtA;
    }

    public String getTxtB() {
        return txtB;
    }

    public void setTxtB(String txtB) {
        this.txtB = txtB;
    }



    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public String getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(String txtTime) {
        this.txtTime = txtTime;
    }

    public String getAlarm() {
        return Alarm;
    }

    public void setAlarm(String alarm) {
        Alarm = alarm;
    }
}
