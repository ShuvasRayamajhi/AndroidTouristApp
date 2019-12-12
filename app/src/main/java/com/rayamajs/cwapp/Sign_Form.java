package com.rayamajs.cwapp;
/*
this code authenticates users and lets them register an account so they can access the application.
It's gets the required user data from the xml file and connects that with FireBase.
by rayamajs for 303CEM Android Applications development
*/
//importing required packages
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Form extends AppCompatActivity {
    //creating objects
    private EditText emailTxt; //email object
    private EditText passwordTxt; //object for password
    private EditText confirmTxt; //object for confirm password text
    private Button buttonReg; //button object for register button
    private ProgressBar progressBar; //progress bar object to give visual idea when loading
    private FirebaseAuth mAuth;  //FireBase object for authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__form); //link with the XML file
        getSupportActionBar().setTitle("Register"); //set action bar on the screen

        //casting with XML
        emailTxt = (EditText) findViewById(R.id.email_txt); //set the email java object with the XML object.
        passwordTxt = (EditText) findViewById(R.id.password_txt); //set the password java object with the XML object.
        confirmTxt = (EditText) findViewById(R.id.confirmPass_txt); //set the confirm password java object with the XML object.
        buttonReg = (Button) findViewById(R.id.register_btn); //set the email register button object with the XML object.
        progressBar = (ProgressBar) findViewById(R.id.progressBar); //set the progress bar java object with the XML object.

        mAuth = FirebaseAuth.getInstance(); //get FireBase's instance object for authentication

        //on click listener for the register button that's casted above
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            //auto generated on click method
            public void onClick(View view) {
                //declaring variables
                String email = emailTxt.getText().toString().trim(); //declare a string variable called email and trim and store data from xml
                String password = passwordTxt.getText().toString().trim(); //declare a string a variable for password and get data from xml object
                String confirmPassword = confirmTxt.getText().toString().trim(); //declare variable and get data from xml object

                //condition to check that email field isn't empty
                if(email.isEmpty()) {
                    Toast.makeText(Sign_Form.this,"Enter Email", Toast.LENGTH_SHORT).show(); //toast to alert user about error
                    return;
                }
                //condition to check that password field isn't empty
                if(password.isEmpty()) {
                    Toast.makeText(Sign_Form.this,"Enter Password", Toast.LENGTH_SHORT).show(); //toast to let user about error
                    return;
                }
                //condition to check confirm pass field isn't empty
                if(confirmPassword.isEmpty()) {
                    Toast.makeText(Sign_Form.this,"Confirm Your Email", Toast.LENGTH_SHORT).show(); //toast to alert user about error
                    return;
                }
                //condition to check that password length isn't too short
                if (password.length()<6) {
                    Toast.makeText(Sign_Form.this,"Password is too short", Toast.LENGTH_SHORT).show(); //toast to alert user about error
                    return;
                }
                //condition to ensure that password and confirm password match
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(Sign_Form.this,"Passwords are not equal", Toast.LENGTH_SHORT).show(); //toast to alert user about error
                    return;
                }
                progressBar.setVisibility(View.VISIBLE); //let users see the progress bar by changing visibility
                //if statement to ensure password is equal to confirm password
                if (password.equals(confirmPassword)) {
                    mAuth.createUserWithEmailAndPassword(email, password) //object created above with parameters
                            //pass our activity on complete listener
                            .addOnCompleteListener(Sign_Form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE); //hide the visibility bar
                                    if (task.isSuccessful()) {
                                        //sign in successful
                                        startActivity(new Intent(getApplicationContext(), Log_Form.class)); //take user to the log in page
                                        Toast.makeText(Sign_Form.this, "Registered", Toast.LENGTH_SHORT).show(); //toast to alert users they were successfully
                                    } else {
                                        //sign in failed
                                        Toast.makeText(Sign_Form.this, "Failed to Authenticate", Toast.LENGTH_SHORT).show(); //alert users that they couldn't register
                                    }
                                }
                            });
                }
            }

        });
    }
}
