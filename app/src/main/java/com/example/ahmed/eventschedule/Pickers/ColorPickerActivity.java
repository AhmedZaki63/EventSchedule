package com.example.ahmed.eventschedule.Pickers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.ImageView;

import com.example.ahmed.eventschedule.EditOrAddActivity;
import com.example.ahmed.eventschedule.R;

public class ColorPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        String[] colorsArray = getResources().getStringArray(R.array.colors);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setTag(colorsArray[i]);
            imageView.setColorFilter(Color.parseColor(colorsArray[i]));
        }
    }

    public void onClick(View view) {
        EditOrAddActivity.eventColor = view.getTag().toString();
        finish();
    }
}
