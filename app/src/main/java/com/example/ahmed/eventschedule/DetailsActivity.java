package com.example.ahmed.eventschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    int position;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        position = getIntent().getIntExtra("position", -1);

        if (MainActivity.page == 0)
            event = MainFragment.upEvents.get(position);
        else
            event = MainFragment.doneEvents.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);

        TextView title = (TextView) findViewById(R.id.details_event_title);
        title.setText(event.getName());

        TextView location = (TextView) findViewById(R.id.details_event_location);
        location.setText(event.getPlace());

        TextView startDate = (TextView) findViewById(R.id.details_start_date);
        startDate.setText(dateFormat.format(event.getStartDate().getTime()));

        TextView startTime = (TextView) findViewById(R.id.details_start_time);
        startTime.setText(timeFormat.format(event.getStartDate().getTime()));

        TextView endDate = (TextView) findViewById(R.id.details_end_date);
        endDate.setText(dateFormat.format(event.getEndDate().getTime()));

        TextView endTime = (TextView) findViewById(R.id.details_end_time);
        endTime.setText(timeFormat.format(event.getEndDate().getTime()));

        TextView reminderDate = (TextView) findViewById(R.id.details_reminder_date);
        reminderDate.setText(dateFormat.format(event.getReminderTime().getTime()));

        TextView reminderTime = (TextView) findViewById(R.id.details_reminder_time);
        reminderTime.setText(timeFormat.format(event.getReminderTime().getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        if (event.isDone()) {
            MenuItem edit = menu.findItem(R.id.action_edit);
            edit.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit:
                Intent intent = new Intent(this, EditOrAddActivity.class)
                        .putExtra("position", position);
                startActivity(intent);
                return true;
            case R.id.action_delete:
                MainFragment.controlRealm.deleteEvent(event.getId());
                NavUtils.navigateUpFromSameTask(DetailsActivity.this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
