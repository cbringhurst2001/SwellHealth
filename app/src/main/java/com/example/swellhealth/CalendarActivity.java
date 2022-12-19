package com.example.swellhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CalendarActivity extends AppCompatActivity {

    CustomCalendarView customCalendarView;

    @Override
    //Launch the activity with the custom calendar view layout
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        customCalendarView = (CustomCalendarView)findViewById(R.id.custom_calendar_view);
    }
}