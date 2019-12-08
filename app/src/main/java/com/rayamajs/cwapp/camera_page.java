package com.rayamajs.cwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class camera_page extends AppCompatActivity {

    Button btnCaptureImage;
    ImageView imageDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_page);

        btnCaptureImage = (Button) findViewById(R.id.btn_captureImage);
        imageDisplay = (ImageView) findViewById(R.id.imageCapture);

    }
}
