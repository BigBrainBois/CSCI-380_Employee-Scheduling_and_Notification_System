package com.nyit.employee_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class SwitchSchedule extends AppCompatActivity {

    private TimePickerDialog timeDialogue;
    private DatePickerDialog dateDialogue;
    private Button dateButton;
    private int time;
    private String date;
    private Button timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_schedule);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        configureDateDialogue();
        configureTimeDialogue();
    }

    private void configureDateDialogue() {
        dateDialogue = new DatePickerDialog(this);
        dateDialogue.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = String.format("%d-%d-%d", year, month+1, dayOfMonth);
                dateButton.setText(date);
            }
        });
    }

    private void configureTimeDialogue(){
        timeDialogue = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = formatTime(hourOfDay,minute);
                timeButton.setText(time);
            }
        },12,0,false);

    }
}
