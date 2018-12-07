package com.example.jason.laborbuddy;

public class Contraction {
    private String startTime;
    private String duration;
    private String frequency;

    public Contraction(String time, String dur, String freq) {

        this.startTime = time;
        this.duration = dur;
        this.frequency = freq;

    }

    public String getStartTime() {

        return startTime;

    }

    public void setStartTime(String time) {

        this.startTime = time;

    }

    public String getDuration() {

        return duration;

    }

    public void setDuration(String dur) {

        this.duration = dur;

    }

    public String getFrequency() {

        return frequency;

    }

    public void setFrequency(String freq) {

        this.frequency = freq;

    }



}