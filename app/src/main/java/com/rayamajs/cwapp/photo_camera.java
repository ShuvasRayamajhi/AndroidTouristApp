package com.rayamajs.cwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class photo_camera extends AppCompatActivity {

    String currentImagePath = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_camera);
    }

    public void captureImage(View view) {

    }

    public void displayImage(View view) {
    }


}
