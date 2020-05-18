package com.nyit.employee_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class SwitchSchedule extends AppCompatActivity {

    private String time1;
    private String time2;
    private String date1;
    private String date2;
    private Button dateButton1;
    private Button timeButton1;
    private Button dateButton2;
    private Button timeButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_schedule);
        dateButton1 = findViewById(R.id.dateButton1);
        timeButton1 = findViewById(R.id.timeButton1);
        dateButton2 = findViewById(R.id.dateButton2);
        timeButton2 = findViewById(R.id.timeButton2);
        configureDateButton1();
        configureDateButton2();
        configureTimeButton1();
        configureTimeButton2();
    }

    private void configureDateButton1(){
        final DatePickerDialog dateDialogue = new DatePickerDialog(this);
        dateDialogue.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date1 = String.format("%d-%d-%d", year, month+1, dayOfMonth);
                dateButton1.setText(date1);
            }
        });

        dateButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialogue.show();
            }
        });
    }


    private void configureDateButton2(){
        final DatePickerDialog dateDialogue = new DatePickerDialog(this);
        dateDialogue.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date2 = String.format("%d-%d-%d", year, month+1, dayOfMonth);
                dateButton2.setText(date2);
            }
        });

        dateButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialogue.show();
            }
        });
    }

    private void configureTimeButton1(){
        final TimePickerDialog timeDialogue = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time1 = formatTime(hourOfDay,minute);
                timeButton1.setText(time1);
            }
        },12,0,false);

        timeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialogue.show();
            }
        });
    }


    private void configureTimeButton2(){
        final TimePickerDialog timeDialogue = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time2 = formatTime(hourOfDay,minute);
                timeButton2.setText(time2);
            }
        },12,0,false);

        timeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialogue.show();
            }
        });
    }


    private String formatTime(int hour, int minute){
        String minuteFormatted = Integer.toString(minute);
        String hourFormatted = Integer.toString(hour);
        String ampm = "am";
        if(minute >= 0 && minute <= 9){
            minuteFormatted = "0"+minute;
        }

        if(hour == 0){
            hourFormatted = "12";
        }
        else if(hour >= 12 && hour <= 24){
            hourFormatted = Integer.toString((hour==12?hour:hour-12));
            ampm = "pm";
        }
        return String.format("%s:%s%s", hourFormatted,minuteFormatted,ampm);
    }


}
