package com.rayamajs.cwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Log_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__form);
        getSupportActionBar().setTitle("Login Form");
    }

    public void btn_signForm(View view) {

        startActivity(new Intent(getApplicationContext(), Sign_Form.class));
    }
}
