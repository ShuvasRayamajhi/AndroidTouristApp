package com.rayamajs.cwapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MemberPage extends AppCompatActivity {

    private TextView outputText;
    private Button btnCamera;
    private Button btnReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_page);
        btnCamera = (Button) findViewById(R.id.camera_btn);
        btnReview = (Button) findViewById(R.id.review_btn);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), camera_page.class));
            }
        });
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), review.class));
            }
        });

    }

    public void btnLocation (View view) {

        startActivity(new Intent(this, MapsActivity.class));
    }

    public void btnSpeechToText(View view) {
        startActivity(new Intent(getApplicationContext(), SpeechToText.class));
    }

    public void textToSpeech(View view) {
        startActivity(new Intent(getApplicationContext(), com.rayamajs.cwapp.TextToSpeech.class));
    }
}
