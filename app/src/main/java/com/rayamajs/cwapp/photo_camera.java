package com.rayamajs.cwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.io.File.createTempFile;

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

    private File getImageFile () throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" +timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName, ".jpeg", storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }
}
