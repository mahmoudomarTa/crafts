package com.example.myproject.models;

public class Request {
    private String name, addressDetails , moreDetails , uid,key;
    private Long date;
    private boolean isFinished , withRow;


    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getMoreDetails() {
        return moreDetails;
    }

    public void setMoreDetails(String moreDetails) {
        this.moreDetails = moreDetails;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isWithRow() {
        return withRow;
    }

    public void setWithRow(boolean withRow) {
        this.withRow = withRow;
    }


    public Request(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Request(String address, Long date, boolean Finished, String more , String name, boolean row, String uid, String key) {
        this.uid=uid;
        this.key=key;
        this.name = name;
        this.addressDetails = address;
        this.moreDetails = more;
        this.date = date;
        this.isFinished = Finished;
        this.withRow = row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
