package com.nyit.employee_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.nyit.employee_scheduler.ui.login.LoginActivity;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void managerLogIn(View v){    // Log In as Manager/Admin
        Intent loginToManager = new Intent(getContext(), LoginActivity.java);
        startActivity(loginToManager);
    }

    public void employeeLogIn(View v){   //Log In as Employee
        Intent loginToEmployee = new Intent(getContext(), LoginActivity.java);
        startActivity(loginToEmployee);

    }
}
