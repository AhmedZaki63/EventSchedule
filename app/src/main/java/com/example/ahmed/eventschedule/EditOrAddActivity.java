package com.example.ahmed.eventschedule;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ahmed.eventschedule.Pickers.ColorPickerActivity;
import com.example.ahmed.eventschedule.Pickers.DatePickerFragment;
import com.example.ahmed.eventschedule.Pickers.PlacePickerFragment;
import com.example.ahmed.eventschedule.Pickers.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditOrAddActivity extends AppCompatActivity {

    public static String eventColor;
    public Calendar startDate, endDate, eventStartTime, eventEndTime, reminderDate, reminderTime;
    public Button startDateButton, endDateButton, startTimeButton, endTimeButton, reminderDateButton, reminderTimeButton;
    public String title, location;
    public EditText nameText, locationText;
    private ImageView colorSelectionImage;
    private boolean inEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_add);

        //set default icon eventColor
        eventColor = "#2196F3";

        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        if (getIntent().getExtras() != null)
            inEdit = true;

        nameText = (EditText) findViewById(R.id.event_name_edit);
        locationText = (EditText) findViewById(R.id.event_location_edit);

        startDate = Calendar.getInstance();
        startDateButton = (Button) findViewById(R.id.start_date_button);
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "start_date");
            }
        });

        eventStartTime = Calendar.getInstance();
        startTimeButton = (Button) findViewById(R.id.start_time_button);
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), "start_time");
            }
        });

        endDate = Calendar.getInstance();
        endDateButton = (Button) findViewById(R.id.end_date_button);
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "end_date");
            }
        });

        eventEndTime = Calendar.getInstance();
        endTimeButton = (Button) findViewById(R.id.end_time_button);
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), "end_time");
            }
        });

        reminderDate = Calendar.getInstance();
        reminderDateButton = (Button) findViewById(R.id.reminder_date_button);
        reminderDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "reminder_date");
            }
        });

        reminderTime = Calendar.getInstance();
        reminderTimeButton = (Button) findViewById(R.id.reminder_time_button);
        reminderTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), "reminder_time");
            }
        });

        colorSelectionImage = (ImageView) findViewById(R.id.color_selector);
        colorSelectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditOrAddActivity.this, ColorPickerActivity.class);
                startActivity(intent);
            }
        });

        ImageView locationSelectionImage = (ImageView) findViewById(R.id.location_selector);
        locationSelectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePickerFragment placePickerFragment = new PlacePickerFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_edit_or_add, placePickerFragment, "").commit();
            }
        });

        if (inEdit) {

            int id = getIntent().getIntExtra("event_id", -1);
            Event currentEvent = MainFragment.controlRealm.getEvent(id);

            title = currentEvent.getName();
            nameText.setText(currentEvent.getName());
            location = currentEvent.getPlace();
            locationText.setText(currentEvent.getPlace());
            startDate = currentEvent.getStartDate();
            endDate = currentEvent.getEndDate();
            reminderDate = currentEvent.getReminderTime();
            eventStartTime = currentEvent.getStartDate();
            eventEndTime = currentEvent.getEndDate();
            reminderTime = currentEvent.getReminderTime();
            eventColor = currentEvent.getColor();
        }

        startDateButton.setText(dateFormat.format(startDate.getTime()));
        startTimeButton.setText(timeFormat.format(eventStartTime.getTime()));
        endDateButton.setText(dateFormat.format(endDate.getTime()));
        endTimeButton.setText(timeFormat.format(eventEndTime.getTime()));
        reminderDateButton.setText(dateFormat.format(reminderDate.getTime()));
        reminderTimeButton.setText(timeFormat.format(reminderTime.getTime()));
    }

    @Override
    protected void onResume() {
        colorSelectionImage.setColorFilter(Color.parseColor(eventColor));
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_or_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            onSaveEvent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveEvent() {
        title = nameText.getText().toString().trim();
        location = locationText.getText().toString().trim();
        Calendar currentDate = Calendar.getInstance();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(location))
            Toast.makeText(EditOrAddActivity.this, "Please Enter All Data", Toast.LENGTH_LONG).show();
        else {
            //Merge date and time and set seconds to 0
            startDate.set(Calendar.HOUR_OF_DAY, eventStartTime.get(Calendar.HOUR_OF_DAY));
            startDate.set(Calendar.MINUTE, eventStartTime.get(Calendar.MINUTE));
            startDate.set(Calendar.SECOND, 0);
            endDate.set(Calendar.HOUR_OF_DAY, eventEndTime.get(Calendar.HOUR_OF_DAY));
            endDate.set(Calendar.MINUTE, eventEndTime.get(Calendar.MINUTE));
            endDate.set(Calendar.SECOND, 0);
            reminderDate.set(Calendar.HOUR_OF_DAY, reminderTime.get(Calendar.HOUR_OF_DAY));
            reminderDate.set(Calendar.MINUTE, reminderTime.get(Calendar.MINUTE));
            reminderDate.set(Calendar.SECOND, 0);

            if (endDate.compareTo(startDate) != 1)
                Toast.makeText(getBaseContext(), "End Time is Before or Equal Start Time", Toast.LENGTH_LONG).show();
            else if (currentDate.compareTo(endDate) == 1)
                Toast.makeText(getBaseContext(), "End Time is Before or Equal Current Time", Toast.LENGTH_LONG).show();
            else if (currentDate.compareTo(reminderDate) == 1)
                Toast.makeText(getBaseContext(), "Reminder Time is Before or Equal Current Time", Toast.LENGTH_LONG).show();
            else {
                final Event event = new Event(title, location, startDate, endDate, reminderDate, eventColor);
                if (inEdit) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(EditOrAddActivity.this);
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int eventID = getIntent().getIntExtra("event_id", -1);
                            MainFragment.controlRealm.editEvent(event, eventID);
                            NavUtils.navigateUpFromSameTask(EditOrAddActivity.this);
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.setTitle("Do you Want To Save ?!");
                    dialog.show();
                } else {
                    MainFragment.controlRealm.addEvent(event);
                    scheduleNotification(event);
                    NavUtils.navigateUpFromSameTask(EditOrAddActivity.this);
                }
            }
        }
    }

    private void scheduleNotification(Event event) {
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(event.getName())
                .setContentText(event.getPlace())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND);

        Intent launchIntent = new Intent(Intent.makeMainActivity(new ComponentName(this, MainActivity.class)));
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        builder.setContentIntent(PendingIntent.getActivity(this, 0, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true);

        Intent notificationIntent = new Intent(this, NotificationBroadcast.class);
        notificationIntent.putExtra("title", event.getName());
        notificationIntent.putExtra("text", event.getPlace());
        notificationIntent.putExtra(NotificationBroadcast.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationBroadcast.NOTIFICATION, builder.build());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, event.getReminderTime().getTimeInMillis(), pendingIntent);
    }
}