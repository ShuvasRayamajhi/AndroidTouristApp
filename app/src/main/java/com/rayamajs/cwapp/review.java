package com.rayamajs.cwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

        btn_insert = (Button) findViewById(R.id.btn_insert); //casting
        text_PlaceName = (EditText) findViewById(R.id.editPlaceName);
        text_Review = (EditText) findViewById(R.id.editReview);
        text_Rating = (EditText) findViewById(R.id.editRating);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted =  myDb.insertData(text_PlaceName.getText().toString(), text_Review.getText().toString(), text_Rating.getText().toString()); //insert data from the xml fle

            }
        });
    }
}
