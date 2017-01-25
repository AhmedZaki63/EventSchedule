package com.example.ahmed.eventschedule.Pickers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ahmed.eventschedule.EditOrAddActivity;
import com.example.ahmed.eventschedule.R;

public class ColorPickerFragment extends AppCompatActivity {

    static String selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_selection);
    }

    public void onClick(View view) {
        selectedColor = view.getTag().toString();
        EditOrAddActivity.eventColor = view.getTag().toString();
        finish();
    }
}
