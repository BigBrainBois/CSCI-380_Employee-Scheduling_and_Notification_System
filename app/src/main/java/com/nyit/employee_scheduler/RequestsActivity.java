package com.nyit.employee_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RequestsActivity extends AppCompatActivity {

    private Button changeSchedule;
    private Button switchSchedule;
    private Button daysOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        changeSchedule = findViewById(R.id.changeSchedule);
        switchSchedule = findViewById(R.id.switchSchedule);
        daysOff = findViewById(R.id.daysOff);
        configureChanges();
        configureSwitches();
        configureDaysOff();
    }

    private void configureChanges(){
        changeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestsActivity.this, ChangeAvailability.class);
                startActivity(intent);
            }
        });
    }

    private void configureSwitches(){
        switchSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestsActivity.this, SwitchSchedule.class);
                startActivity(intent);
            }
        });
    }

    private void configureDaysOff(){
        daysOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestsActivity.this, DaysOffRequest.class);
                startActivity(intent);
            }
        });
    }

}
