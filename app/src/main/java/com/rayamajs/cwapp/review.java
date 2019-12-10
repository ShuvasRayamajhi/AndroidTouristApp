package com.rayamajs.cwapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class review extends AppCompatActivity {

    DatabaseHelper myDb;
    Button btn_Insert, btn_View;
    EditText text_PlaceName, text_Review, text_Rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        myDb = new DatabaseHelper(this);

        btn_Insert = (Button) findViewById(R.id.btn_insert);
        btn_View = (Button) findViewById(R.id.btn_view);//casting
        text_PlaceName = (EditText) findViewById(R.id.editPlaceName);
        text_Review = (EditText) findViewById(R.id.editReview);
        text_Rating = (EditText) findViewById(R.id.editRating);

        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted =  myDb.insertData(text_PlaceName.getText().toString(), text_Review.getText().toString(), text_Rating.getText().toString()); //insert data from the xml fle
                    if (isInserted == true) {
                        Toast.makeText(review.this, "Review has been added", Toast.LENGTH_SHORT).show();//let the user know their data has been added.
                    }
                    else {
                        Toast.makeText(review.this, "Review failed to add", Toast.LENGTH_SHORT).show(); //let use know when the insertion has failed

                    }

            }
        });
        //add functionality to view button
        btn_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur =  myDb.getAlldData();

                if(cur.getCount()==0){
                   showMessage("Error", "Data not found");
                }

                StringBuffer buffer = new StringBuffer();
                while (cur.moveToNext()){

                    buffer.append("ID: "+ cur.getString(0)+ "\n");
                    buffer.append("Place Name: "+ cur.getString(1)+ "\n");
                    buffer.append("Review: "+ cur.getString(2)+ "\n");
                    buffer.append("Rating: "+ cur.getString(3)+ "\n");


                }
                showMessage("Reviews:", buffer.toString());

            }
        });

    }
    public void showMessage (String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
