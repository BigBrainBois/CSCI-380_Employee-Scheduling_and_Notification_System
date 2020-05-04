package com.nyit.employee_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RequestsActivity extends AppCompatActivity {

    private Button changeSchedule;
    private Button switchSchedule;
    private Button daysOff;
    private TextView userNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        changeSchedule = findViewById(R.id.changeSchedule);
        switchSchedule = findViewById(R.id.switchSchedule);
        daysOff = findViewById(R.id.daysOff);
        userNameText = findViewById(R.id.username);
        userNameText.setText(getIntent().getStringExtra("UserName"));
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
                intent.putExtra("EmployeeID",getIntent().getIntExtra("EmployeeID",-1));
                startActivity(intent);
            }
        });
    }

}
