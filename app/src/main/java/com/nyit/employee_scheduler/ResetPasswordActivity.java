package com.nyit.employee_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText username;
    private EditText employeeID;
    private EditText rank;
    private EditText newPassword;
    private Button submitReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        username = findViewById(R.id.username);
        employeeID = findViewById(R.id.employeeID);
        rank = findViewById(R.id.rank);
        newPassword = findViewById(R.id.newPassword);
        submitReset = findViewById(R.id.submitReset);
        configureResetPassword();
    }

    private void configureResetPassword(){

        submitReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResetPasswordRequest(username.getText().toString(), employeeID.getText().toString(),
                                         rank.getText().toString(), newPassword.getText().toString());


            }
        });
    }

    private void sendResetPasswordRequest(final String username, final String employeeID, final String rank,
                                          final String newPassword ){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://64.190.90.187/api/user/resetPassword.php");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("Username", username );
                    jsonParam.put("Password", newPassword);
                    jsonParam.put("EmployeeID", employeeID);
                    jsonParam.put("Rank", rank);

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

                        Intent intent = new Intent(ResetPasswordActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                    else{
                        ResetPasswordActivity.this.username.setText("");
                        ResetPasswordActivity.this.newPassword.setText("");
                        ResetPasswordActivity.this.employeeID.setText("");
                        ResetPasswordActivity.this.rank.setText("");
                        ResetPasswordActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ResetPasswordActivity.this,"Make sure all fields are filled in.",Toast.LENGTH_SHORT).show();
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
