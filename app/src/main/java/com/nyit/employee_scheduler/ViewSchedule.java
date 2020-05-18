package com.nyit.employee_scheduler;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ViewSchedule extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        listView = findViewById(R.id.list_view);

        viewScheduleRequest();
    }

        private void viewScheduleRequest() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        URL url = new URL("http://64.190.90.187/api/schedule/readMySchedule.php?id="+getIntent().getIntExtra("EmployeeID",-1));
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);

                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        br.close();
                        try {
                            JSONObject jsonObject = new JSONObject(sb.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("records");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String date = object.getString("Date");
                                String StartTime = object.getString("StartTime");
                                String EndTime = object.getString("EndTime");
                                arrayList.add(date + " : " + StartTime + " - " + EndTime);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        arrayAdapter = new ArrayAdapter<>(ViewSchedule.this, android.R.layout.simple_list_item_1,arrayList);

                        listView.setAdapter(arrayAdapter);



                        if (String.valueOf(conn.getResponseCode()).equals(null)) {
                            ViewSchedule.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ViewSchedule.this, "Was not able to access schedule", Toast.LENGTH_SHORT).show();
                                }
                            });

                            conn.disconnect();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

        }

    }



