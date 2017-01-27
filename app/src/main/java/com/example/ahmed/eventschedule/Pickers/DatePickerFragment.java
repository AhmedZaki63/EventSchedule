package com.example.ahmed.eventschedule.Pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.example.ahmed.eventschedule.EditOrAddActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Calendar calendar;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        calendar.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy", Locale.ENGLISH);
        EditOrAddActivity editOrAdd = (EditOrAddActivity) getActivity();
        switch (getTag()) {
            case "start_date":
                editOrAdd.startDate = calendar;
                editOrAdd.startDateButton.setText(dateFormat.format(calendar.getTime()));
                break;
            case "end_date":
                editOrAdd.endDate = calendar;
                editOrAdd.endDateButton.setText(dateFormat.format(calendar.getTime()));
                break;
            case "reminder_date":
                editOrAdd.reminderDate = calendar;
                editOrAdd.reminderDateButton.setText(dateFormat.format(calendar.getTime()));
                break;
        }
    }
}

