package com.nyit.employee_scheduler;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DaysOffRequest extends AppCompatActivity {

        private Button submitDaysOffButton;
        private DatePickerDialog dateDialogue;
        private Button selectOffButton;
        private String date;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_daysoff_request);
            submitDaysOffButton = findViewById(R.id.submitDaysOffButton);
            selectOffButton = findViewById(R.id.selectOffButton);
            configureRequests();
            configureDateDialogue();
            configureOffButton();
        }
        //Days Off Request is creating a request to add a specific time to have days off based on vacation time and current schedule of employee (and/or other employees)
        private void configureRequests(){

            submitDaysOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    daysOffRequest();
                }
            });
        }

        private void configureOffButton(){
            selectOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dateDialogue.show();
                }
            });
        }

        private void daysOffRequest() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {//request method might be changed depending on API
                        URL url = new URL("http://64.190.90.187/api/request/createRequest.php");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);

                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("DateRequested", date);
                        jsonParam.put("EmployeeID", "1234556");
                        jsonParam.put("Status", "unapproved");
                        jsonParam.put("RequestType","vacation");
                        jsonParam.put("Message","hello!");
                        jsonParam.put("RequestID","12345");


                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        os.writeBytes(jsonParam.toString());

                        os.flush();
                        os.close();

                        if (String.valueOf(conn.getResponseCode()).equals("200")) {
                            finish();
                        } else {
                            DaysOffRequest.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DaysOffRequest.this, "Request was not submitted, please try again. ", Toast.LENGTH_SHORT).show();
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


        private void configureDateDialogue() {
            dateDialogue = new DatePickerDialog(this);
            dateDialogue.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date = String.format("%d-%d-%d", year, month+1, dayOfMonth);
                    selectOffButton.setText(date);
                }
            });

        }

    }


