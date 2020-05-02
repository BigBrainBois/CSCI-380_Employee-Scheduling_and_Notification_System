package com.nyit.employee_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DaysOffRequest extends AppCompatActivity {

    private EditText fromDay;
    private EditText toDay;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daysoff_request);
        fromDay = findViewById(R.id.fromDay);
        toDay = findViewById(R.id.toDay);
        submitButton = findViewById(R.id.switchSubmitButton);

        configureRequests();

    }
//Days Off Request is creating a request to add a specific time to have days off based on vacation time and current schedule of employee (and/or other employees)
    private void configureRequests(){

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daysOffRequest(fromDay.getText().toString(), toDay.getText().toString());
            }
        });
    }

    private void daysOffRequest(final String fromDay, final String toDay) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {//request method might be changed depending on API
                    URL url = new URL("API FOR SCHEDULE");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("From Day", fromDay );
                    jsonParam.put("From Day", toDay);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    if (String.valueOf(conn.getResponseCode()).equals("whatever the response code ends up being")) {
                        //SUBMIT FROM DAY AND TO DAY DATES
                        Intent intent = new Intent( DaysOffRequest.this, RequestsActivity.class);
                        startActivity(intent);
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
}
