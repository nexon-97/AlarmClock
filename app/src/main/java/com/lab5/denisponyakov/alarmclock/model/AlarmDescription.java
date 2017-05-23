package com.lab5.denisponyakov.alarmclock.model;

import java.util.Date;

public class AlarmDescription {

    private int hour;
    private int minute;
    private String name;
    private boolean isActive;

    public AlarmDescription(String name, int hour, int minute, boolean isActive) {
        setName(name);
        setHour(hour);
        setMinute(minute);
        setIsActive(isActive);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return this.name;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public boolean getIsActive() {
        return this.isActive;
    }
}
