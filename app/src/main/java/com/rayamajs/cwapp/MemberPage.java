package com.rayamajs.cwapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MemberPage extends AppCompatActivity {

    TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_page);
        outputText = (TextView) findViewById(R.id.txt_output);
    }

    public void btnLocation (View view) {

        startActivity(new Intent(this, MapsActivity.class));
    }



    public void btn_camera(View view) {

        startActivity(new Intent(getApplicationContext(), camera_page.class));
    }

    public void btn_review(View view) {
        startActivity(new Intent(getApplicationContext(), review.class));
    }

    public void btnAccessbility(View view) {
        startActivity(new Intent(getApplicationContext(), Accessibility.class));
    }
}
