package com.rayamajs.cwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
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

       btnCaptureImage.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(intent, 0);
           }
       });
    }


}
