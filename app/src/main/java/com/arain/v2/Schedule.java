package com.arain.v2;

public class Schedule {
    private String date;
    private String time;
    private String duration;

    public Schedule() {}

    public Schedule(String date, String time, String duration) {
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}





