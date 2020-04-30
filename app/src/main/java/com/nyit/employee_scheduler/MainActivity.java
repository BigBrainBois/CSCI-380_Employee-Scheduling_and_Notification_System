package com.nyit.employee_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button employeeButton;
    private Button managerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        employeeButton = findViewById(R.id.employeebutton);
        managerButton = findViewById(R.id.managerbutton);
        configureEmployeeButton();
        configureManagerButton();
    }

    private void configureManagerButton(){
        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, ManagerLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configureEmployeeButton(){
        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

//    public void managerLogIn(View v){    // Log In as Manager/Admin
//        Intent loginToManager = new Intent(this, LoginActivity.class);
//        startActivity(loginToManager);
//    }
//
//    public void employeeLogIn(View v){   //Log In as Employee
//        Intent loginToEmployee = new Intent(this, LoginActivity.class);
//        startActivity(loginToEmployee);
//
//    }
}
