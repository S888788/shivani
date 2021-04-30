package com.elation.myapplication.Modal;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.Serializable;

public class MobileNumberModal implements Serializable {

    @SerializedName("id")
    public String id;
    @SerializedName("mobile_number")
    public String mobile_number;

    @SerializedName("iswhatapp")
    public  boolean iswhatapp;
    public boolean isIswhatapp() {
        return iswhatapp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String status;


    public String getMobile_number() {
        return mobile_number;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public void setIswhatapp(boolean iswhatapp) {
        this.iswhatapp = iswhatapp;
    }

    public String getId() {
        return id;
    }


    private void writeObject(java.io.ObjectOutputStream out)throws IOException {

        out.writeObject(id);
        out.writeObject(mobile_number);
        out.writeObject(iswhatapp);
    }

    private void readObject(java.io.ObjectInputStream in)throws IOException, ClassNotFoundException {
        id=(String)in.readObject();
        mobile_number = (String)in.readObject();
        iswhatapp=(Boolean)in.readObject();

    }

}
