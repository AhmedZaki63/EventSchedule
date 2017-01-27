package com.example.ahmed.eventschedule.Pickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.example.ahmed.eventschedule.EditOrAddActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private Calendar calendar;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        EditOrAddActivity editOrAdd = (EditOrAddActivity) getActivity();
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        switch (getTag()) {
            case "start_time":
                editOrAdd.eventStartTime = calendar;
                editOrAdd.startTimeButton.setText(dateFormat.format(calendar.getTime()));
                break;
            case "end_time":
                editOrAdd.eventEndTime = calendar;
                editOrAdd.endTimeButton.setText(dateFormat.format(calendar.getTime()));
                break;
            case "reminder_time":
                editOrAdd.reminderTime = calendar;
                editOrAdd.reminderTimeButton.setText(dateFormat.format(calendar.getTime()));
                break;
        }
    }
}
