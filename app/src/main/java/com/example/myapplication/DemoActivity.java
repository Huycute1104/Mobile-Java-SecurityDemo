package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.Sqlite.DatabaseHelper;
import com.example.myapplication.Database.Sqlite.DatabaseManager;

public class DemoActivity extends AppCompatActivity {

    EditText idtxt;
    EditText usernametxt;
    EditText passwordtxt;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Button backtohomebtn = findViewById(R.id.backtohomebtn);
        idtxt = findViewById(R.id.idtxt);
        usernametxt = findViewById(R.id.usernametxt);
        passwordtxt = findViewById(R.id.passwordtxt);

        databaseManager = new DatabaseManager(this);

        try{
            databaseManager.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        backtohomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    public void btnInsertPressed(View v){
        databaseManager.insert(usernametxt.getText().toString(), passwordtxt.getText().toString());
        Toast.makeText(this, "insert new user", Toast.LENGTH_SHORT).show();

    }

    public void btnFetchPressed(View v){
        Cursor cursor = databaseManager.fetch();
        Toast.makeText(this, Boolean.toString(cursor.moveToFirst()), Toast.LENGTH_SHORT).show();
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_ID));
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_NAME));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_PASSWORD));
                Log.i("DATABASE TAG", "i have read id: " + id + " username: " + username + " password: " + password);

            }while (cursor.moveToNext());
        }
    }

    public void btnUpdatePressed(View v){
        databaseManager.update(Long.parseLong(idtxt.getText().toString()), usernametxt.getText().toString(), passwordtxt.getText().toString());
    }

    public void btnDeletePressed(View v){
        databaseManager.delete(Long.parseLong(idtxt.getText().toString()));
    }

}
