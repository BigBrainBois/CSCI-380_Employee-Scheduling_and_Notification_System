package com.nyit.employee_scheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends Activity {

    private EditText username;
    private EditText password;
    private Button login;
    private Button resetPassword;
    private Intent currentIntent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        resetPassword = findViewById(R.id.resetPassword);
        currentIntent = this.getIntent();
        configureLogin();
        configureResetPassword();


    }


    private void configureLogin(){

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginRequest(username.getText().toString(),password.getText().toString());
            }
        });
    }
    private void configureResetPassword(){
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayErrorToast(){

    }

    private void sendLoginRequest(final String username, final String password){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://64.190.90.187/api/user/authenticate.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("Username", username );
                    jsonParam.put("Password", password);
                    jsonParam.put("Rank",  currentIntent.getStringExtra("Rank"));


                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    if(String.valueOf(conn.getResponseCode()).equals("200")){

                        //getting content from message
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();

                        //creating json object
                        AuthResponse response = new Gson().fromJson(sb.toString(), AuthResponse.class);

                        Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                        intent.putExtra("EmployeeID",response.getEmployeeID());
                        intent.putExtra("UserName",username);
                        startActivity(intent);
                    }
                    else{
                        LoginActivity.this.username.setText("");
                        LoginActivity.this.password.setText("");
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
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
