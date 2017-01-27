package com.example.ahmed.eventschedule;

import java.util.Calendar;

public class Event {
    private String name, place, color;
    private int id;
    private boolean Done = false;
    private Calendar startDate, endDate, reminderTime;

    public Event(String name, String place, Calendar startDate, Calendar endDate, Calendar reminderTime, String color) {
        this.name = name;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminderTime = reminderTime;
        this.color = color;

        if (!endDate.after(Calendar.getInstance()))
            Done = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    boolean isDone() {
        return Done;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public Calendar getReminderTime() {
        return reminderTime;
    }

    public String getColor() {
        return color;
    }
}
