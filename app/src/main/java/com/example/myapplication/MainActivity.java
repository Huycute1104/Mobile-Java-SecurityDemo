
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.SqlServer.SqlConnection;
import com.example.myapplication.Email.GMailSender;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Connection connection;
    String connectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView hellowordtxt = findViewById(R.id.hellowordtxt);
        Button demobtn = findViewById(R.id.demobtn);
        Button tologinbtn = findViewById(R.id.tologinbtn);
        hellowordtxt.setTextColor(Color.parseColor("purple"));
        demobtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                hellowordtxt.setText("redirect to demo activity");
                Intent demoIntent = new Intent(getApplicationContext(), DemoActivity.class);
                startActivity(demoIntent);
            }
        });
        tologinbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
//                hellowordtxt.setText("redirect to login activity");
//                Intent demoIntent = new Intent(getApplicationContext(), LoginActivity.class);
                try {
                    GMailSender sender = new GMailSender("exceptionkindom@gmail.com", "blccnbhlmncpfkhr");
                    String result = sender.sendMail();
                    Log.i("SendMail", result);

                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
//                startActivity(demoIntent);
            }
        });
        SqlConnection sqlConnection = new SqlConnection();
        connection = sqlConnection.CONN();
        connect();

    }
    public void connect(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try{
                if(connection == null){
                    connectionResult = "Error";
                }
                else{
                    connectionResult = "Connected with sql server";
                }
            }catch(Exception e){
                throw new RuntimeException(e);
            }

            runOnUiThread(() -> {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                Toast.makeText(this, connectionResult, Toast.LENGTH_SHORT).show();
                TextView txt = findViewById(R.id.hellowordtxt);
                txt.setText(connectionResult);
            });
        });
    }
}