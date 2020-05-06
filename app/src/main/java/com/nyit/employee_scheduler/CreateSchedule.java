package com.nyit.employee_scheduler;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;


import org.json.JSONObject;


import java.io.DataOutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

public class CreateSchedule extends Activity {

    private String time1, time2, date1;
    private Button timeButton1, timeButton2, dateButton1, submit;
    private TextInputEditText employeeIDInput;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);
        timeButton1 = findViewById(R.id.createScheduleStartTime);
        timeButton2 = findViewById(R.id.createScheduleEndTime);
        dateButton1 = findViewById(R.id.createScheduleDate);
        submit = findViewById(R.id.submitSchedule);
        employeeIDInput = findViewById(R.id.employeeID);
        configureSubmitButton();
        configureTimeButton1();
        configureTimeButton2();
        configureDateButton1();
    }

    private void configureSubmitButton(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginRequest();
            }
        });
    }

    private void sendLoginRequest(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://64.190.90.187/api/schedule/createSchedule.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("Date",date1);
                    jsonParam.put("EmployeeID",employeeIDInput.getText().toString());
                    jsonParam.put("StartTime",time1);
                    jsonParam.put("EndTime",time2);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();
                    if(String.valueOf(conn.getResponseCode()).equals("200")){

                        finish();
                    }
                    else{
                        CreateSchedule.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CreateSchedule.this,"Unable to create schedule",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

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
