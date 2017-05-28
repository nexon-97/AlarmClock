package com.lab5.denisponyakov.alarmclock.model;

import android.net.Uri;

public class AlarmDescription implements CrudObject<AlarmDescription> {

    private int hour;
    private int minute;
    private String name;
    private Uri songUri;
    private boolean isActive;

    public AlarmDescription() {
        setName("");
        setHour(0);
        setMinute(0);
        setSongUri(null);
        setIsActive(false);
    }

    public AlarmDescription(String name, int hour, int minute, Uri songUri, boolean isActive) {
        setName(name);
        setHour(hour);
        setMinute(minute);
        setSongUri(songUri);
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

    public void setSongUri(Uri songUri) {
        this.songUri = songUri;
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

    public Uri getSongUri() {
        return this.songUri;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    @Override
    public void update(AlarmDescription tempObject) {
        setName(tempObject.getName());
        setHour(tempObject.getHour());
        setMinute(tempObject.getMinute());
        setSongUri(tempObject.getSongUri());
        setIsActive(tempObject.getIsActive());
    }
}
