package com.example.ahmed.eventschedule.DataBase;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventRealm extends RealmObject {
    @PrimaryKey
    private int id;

    private String name;

    private String place;

    private Date startDate;

    private Date endDate;

    private Date reminderDate;

    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getPlace() {
        return place;
    }

    void setPlace(String place) {
        this.place = place;
    }

    Date getStartDate() {
        return startDate;
    }

    void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    Date getEndDate() {
        return endDate;
    }

    void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    Date getReminderDate() {
        return reminderDate;
    }

    void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
