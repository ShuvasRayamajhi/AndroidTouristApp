package com.rayamajs.cwapp;
/*
this code authenticates users and lets them log in with their existing account so they can access the application
this requires user entered email and password from the linked xml file
by rayamajs for 303CEM Android Applications development
*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Log_Form extends AppCompatActivity {
    //creating objects
    private EditText emailTxt; //email object
    private EditText passwordTxt; //object for password
    private Button logBtn; //button object for log in button
    private FirebaseAuth mAuth; //FireBase object for authentication
    ProgressBar progressBar; //progress bar object to give visual idea when loading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__form);
        getSupportActionBar().setTitle("Login Form"); //link with the XML file

        //casting
        emailTxt = (EditText)findViewById(R.id.email_txt); //set appropriate object with appropriate xml entity
        passwordTxt = (EditText) findViewById(R.id.password_txt);
        logBtn = (Button) findViewById(R.id.log_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance(); //get firebase instance

        //set on cick listener on log in button
        logBtn.setOnClickListener(new View.OnClickListener() {
            //method
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString().trim(); //declare variable, get email text and trim
                String password = passwordTxt.getText().toString().trim();
                //check email isn't empty
                if(email.isEmpty()) {
                    Toast.makeText(Log_Form.this,"Enter Email", Toast.LENGTH_SHORT).show(); //alert user
                    return;
                }
                if(password.isEmpty()) {
                    Toast.makeText(Log_Form.this,"Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password) //sign users in with FireBase authorisation
                        .addOnCompleteListener(Log_Form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startActivity(new Intent(getApplicationContext(), MemberPage.class));
                                    Toast.makeText(Log_Form.this, "Hello Again", Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Log_Form.this, "Log In Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    //on click method to take users to register page
    public void btn_signForm(View view) {
        startActivity(new Intent(getApplicationContext(), Sign_Form.class)); //on click
    }
}
