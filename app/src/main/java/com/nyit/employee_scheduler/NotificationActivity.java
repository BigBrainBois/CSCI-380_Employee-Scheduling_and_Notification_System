package com.nyit.employee_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ListView notificationListView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationListView = findViewById(R.id.list_view);

        viewNotifications();

    }

    private void viewNotifications() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://64.190.90.187/api/request/readRequest.php");
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
                                String message = object.getString("Message");
                                String date = object.getString("DateRequested");
                                String status = object.getString("Status");
                                arrayList.add( message + " made for " + date + " is " + status);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        arrayAdapter = new ArrayAdapter<>(NotificationActivity.this, android.R.layout.simple_list_item_1,arrayList);

                        notificationListView.setAdapter(arrayAdapter);



                        if (String.valueOf(conn.getResponseCode()).equals(null)) {
                            NotificationActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(NotificationActivity.this, "Notifications could not be accessed", Toast.LENGTH_SHORT).show();
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

