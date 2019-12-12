package com.rayamajs.cwapp;
/*
code for the member page, directs user to appropriate feature they want to access.
by rayamajs for 303CEM Android Applications development
*/

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MemberPage extends AppCompatActivity {
    //declare objects
    private Button btnReview; //button object for the review button
    private Button btnLocation; //button object for the location button
    private Button btnSpeech; //button object for text to speech button
    private Button btnImage; //button object for image to text button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_page); //connect with the xml layout

        btnReview = (Button) findViewById(R.id.review_btn); //cast, connect the object to the button in the xml
        btnLocation = (Button) findViewById(R.id.location_btn);
        btnSpeech = (Button) findViewById(R.id.speech_btn);
        btnImage = (Button) findViewById(R.id.btn_image);

        //on click listener
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), review.class)); //start activity based on the button clicked
            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });
        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TextToSpeech.class));
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ImageText.class));

            }
        });
    }

}
