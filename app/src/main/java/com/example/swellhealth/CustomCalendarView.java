package com.example.swellhealth;

import static com.example.swellhealth.DBStructure.*;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swellhealth.Adapters.EventRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//This program formats the calendar and assigns some of its functionality
public class CustomCalendarView extends LinearLayout {
    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

    AlertDialog alertDialog;
    MyGridAdapter myGridAdapter;

    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();

    //Create a default constructor
    public CustomCalendarView(Context context){
        super(context);
    }

    //Create a consturctor for the calendar view
    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        //On activity launch, assign views and setup the layout
        super(context, attrs);
        this.context = context;
        InitializeLayout();

        //Assign click functions to the next and previous month buttons
        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, -1);
                SetUpCalendar();
            }
        });
        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, 1);
                SetUpCalendar();
            }
        });

        //Assign on click listeners to each day on the calendar
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                //Obtain the add new event layout and assign it to a button click
                View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_add_newevent, null);
                EditText EventName = addView.findViewById(R.id.eventname);
                Button AddEvent = addView.findViewById(R.id.addevent);

                //Assign the selected details to their string counterpart
                final String date = eventDateFormat.format(dates.get(position));
                final String month = monthFormat.format(dates.get(position));
                final String year = yearFormat.format(dates.get(position));

                //When the user presses add event, assign it to the selected day
                AddEvent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SaveEvent(EventName.getText().toString(), date, month, year);
                        SetUpCalendar();
                        alertDialog.dismiss();
                    }
                });

                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //When a user long clicks a day, show the stored recipes for that day
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String date = eventDateFormat.format(dates.get(position));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                //Set the layout to the show calendar events layout file
                View showView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_show_events, null);

                //Initialize the recyclerView to show recipes
                RecyclerView recyclerView = showView.findViewById(R.id.eventsRecycler);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                //Create an event recycler adapter to format the inputted data correctly
                EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext(),
                        CollectEventByDate(date));
                recyclerView.setAdapter(eventRecyclerAdapter);
                eventRecyclerAdapter.notifyDataSetChanged();

                builder.setView(showView);
                alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    //To collect the data for each day, we need to store it
    private ArrayList<Events> CollectEventByDate(String date){
        //Create an array list to store data for each day
        ArrayList<Events> arrayList = new ArrayList<>();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEvents(date, database);
        //When the cursor selects a day, store the data corresponding to it
        while(cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndex(EVENT));
            String Date = cursor.getString(cursor.getColumnIndex(DATE));
            String month = cursor.getString(cursor.getColumnIndex(EVENT));
            String Year = cursor.getString(cursor.getColumnIndex(EVENT));
            //Create a new Event object to store data for inputted event, then add it to the events array
            Events events = new Events(event, Date, month, Year);
            arrayList.add(events);
        }
        cursor.close();
        dbOpenHelper.close();

        return arrayList;

    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //>To save an event, we upload it to the SQLite database
    private void SaveEvent(String event, String date ,String month,String year){

        //Create a new DBOpenHelper to store event data
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveEvent(event, date, month, year, database);
        dbOpenHelper.close();
        Toast.makeText(context, "Recipe Saved", Toast.LENGTH_SHORT).show();


    }

    //Initialize calendar layout views
    private void InitializeLayout(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        NextButton = view.findViewById(R.id.nextMonthBtn);
        PreviousButton = view.findViewById(R.id.previousMonthBtn);
        CurrentDate = view.findViewById(R.id.currentDate);
        gridView = view.findViewById(R.id.gridview);
    }

    //Each month has different days, so we must format them
    private void SetUpCalendar() {
        //Set the header to the current date
        String currentDate2 = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDate2);
        dates.clear();

        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayofMonth);
        CollectEventsPerMonth(monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));


        while(dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        myGridAdapter = new MyGridAdapter(context,dates,calendar,eventsList);
        gridView.setAdapter(myGridAdapter);

    }

    private void CollectEventsPerMonth(String Month, String year){
        eventsList.clear();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEventsperMonth(Month, year, database);
        while(cursor.moveToNext()){;
            String event = cursor.getString(cursor.getColumnIndex(EVENT));
            String date = cursor.getString(cursor.getColumnIndex(DATE));
            String month = cursor.getString(cursor.getColumnIndex(EVENT));
            String Year = cursor.getString(cursor.getColumnIndex(EVENT));
            Events events = new Events(event, date, month, Year);
            eventsList.add(events);
            }
        cursor.close();
        dbOpenHelper.close();
    }

}
