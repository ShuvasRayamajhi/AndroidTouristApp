package com.rayamajs.cwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class review extends AppCompatActivity {

    DatabaseHelper myDb;
    Button btn_insert;
    EditText text_PlaceName, text_Review, text_Rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        myDb = new DatabaseHelper(this);

        btn_insert = (Button) findViewById(R.id.btn_insert);

    }
}
