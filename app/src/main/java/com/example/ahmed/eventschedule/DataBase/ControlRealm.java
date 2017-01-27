package com.example.ahmed.eventschedule.DataBase;

import android.content.Context;

import com.example.ahmed.eventschedule.Event;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ControlRealm {
    private Realm realm;

    public ControlRealm(Context context) {
        realm = Realm.getInstance(new RealmConfiguration.Builder(context)
                .name("Event.Realm").build());
    }

    public void addEvent(final Event event) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                EventRealm eve = realm.createObject(EventRealm.class, event.getId());
                eve.setName(event.getName());
                eve.setId(getNextPrimaryKey());
                eve.setEndDate(event.getEndDate().getTime());
                eve.setPlace(event.getPlace());
                eve.setStartDate(event.getStartDate().getTime());
                eve.setReminderDate(event.getReminderTime().getTime());
                eve.setColor(event.getColor());
            }
        });
    }

    public void clearEvents(final int page) {
        final RealmResults<EventRealm> result = realm.where(EventRealm.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (EventRealm res : result) {
                    if (page == 0 && res.getEndDate().after(Calendar.getInstance().getTime())
                            || page == 1 && !res.getEndDate().after(Calendar.getInstance().getTime()))
                        res.deleteFromRealm();
                }
            }
        });
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> events = new ArrayList<>();
        RealmResults<EventRealm> result = realm.where(EventRealm.class).findAll();
        for (EventRealm res : result) {
            //Converting Date to Calendar
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(res.getStartDate());
            Calendar endDate = Calendar.getInstance();
            endDate.setTime(res.getEndDate());
            Calendar reminderDate = Calendar.getInstance();
            reminderDate.setTime(res.getReminderDate());

            Event e = new Event(res.getName(), res.getPlace(), startDate, endDate, reminderDate, res.getColor());
            e.setId(res.getId());
            events.add(e);
        }
        return events;
    }

    public Event getEvent(int id) {
        EventRealm eve = realm.where(EventRealm.class).equalTo("id", id).findFirst();
        //Converting Date to Calendar
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(eve.getStartDate());
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(eve.getEndDate());
        Calendar reminderDate = Calendar.getInstance();
        reminderDate.setTime(eve.getReminderDate());
        return new Event(eve.getName(), eve.getPlace(), startDate, endDate, reminderDate, eve.getColor());
    }

    public void editEvent(final Event event, final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                EventRealm eve = realm.where(EventRealm.class).equalTo("id", id).findFirst();
                eve.setName(event.getName());
                eve.setPlace(event.getPlace());
                eve.setStartDate(event.getStartDate().getTime());
                eve.setEndDate(event.getEndDate().getTime());
                eve.setReminderDate(event.getReminderTime().getTime());
                eve.setColor(event.getColor());
            }
        });
    }

    public void deleteEvent(final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                EventRealm res = realm.where(EventRealm.class).equalTo("id", id).findFirst();
                res.deleteFromRealm();
            }
        });
    }

    private int getNextPrimaryKey() {
        return realm.where(EventRealm.class).max("id").intValue() + 1;
    }
}
