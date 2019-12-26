package com.example.myproject.models;

public class Job {
    private int jobImgId;
    private String jobName;

    public int getJobImgId() {
        return jobImgId;
    }

    public void setJobImgId(int jobImgId) {
        this.jobImgId = jobImgId;
    }

    public String getJobName() {
        return jobName;
    }

    public Job(int jobImgId, String jobName) {
        this.jobImgId = jobImgId;
        this.jobName = jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
