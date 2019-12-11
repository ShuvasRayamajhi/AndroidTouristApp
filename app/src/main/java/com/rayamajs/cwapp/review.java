package com.rayamajs.cwapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class review extends AppCompatActivity {

    private DatabaseHelper myDb;
    private Button btnInsert; //button to insert data on the database
    private Button btnView;  //button to view data
    private Button btnDelete; //button to delete data
    private EditText txtName; //user input of place name
    private EditText txtReview; //users review
    private EditText txtRating; //stores rating of the user
    private EditText txtID;

    private Button insertNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        myDb = new DatabaseHelper(this);
        btnInsert = (Button) findViewById(R.id.btn_insert);//casting
        btnView = (Button) findViewById(R.id.btn_view);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        txtName = (EditText) findViewById(R.id.editPlaceName);
        txtReview = (EditText) findViewById(R.id.txt_output);
        txtRating = (EditText) findViewById(R.id.editRating);
        txtID = (EditText) findViewById(R.id.edit_id);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted =  myDb.insertData(txtName.getText().toString(), txtReview.getText().toString(), txtRating.getText().toString()); //insert data from the xml fle

                    if (isInserted == true) {
                        Toast.makeText(review.this, "Review has been added", Toast.LENGTH_SHORT).show();//let the user know their data has been added.
                    }
                    else {
                        Toast.makeText(review.this, "Review failed to add", Toast.LENGTH_SHORT).show(); //let use know when the insertion has failed
                    }
            }
        });
        //add functionality to view button
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur =  myDb.getAllData();

                if(cur.getCount()==0){
                   showMessage("Error", "Data not found"); //if there is no data existing in the database.
                }
                StringBuffer buffer = new StringBuffer();
                while (cur.moveToNext()){ //keep looping until all data is found

                    buffer.append("ID: "+ cur.getString(0)+ "\n");
                    buffer.append("Place Name: "+ cur.getString(1)+ "\n");
                    buffer.append("Review: "+ cur.getString(2)+ "\n");
                    buffer.append("Rating: "+ cur.getString(3)+ "\n");

                }
                showMessage("Reviews:", buffer.toString()); //display data on the xml

            }
        });
        //delete data
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
              Integer deletedRow = myDb.deleteData(txtID.getText().toString());
              if(deletedRow>0) {
                  Toast.makeText(review.this, "Deleted data", Toast.LENGTH_SHORT).show();
              }
              else {
                  Toast.makeText(review.this, "Deleted data", Toast.LENGTH_SHORT).show();
              }
            }
        });

    }
    public void btnSpeech(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say Something");
        try {
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode==RESULT_OK && null!= data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtReview.setText(result.get(0));
                }
                break;
        }

    }
    public void showMessage (String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
