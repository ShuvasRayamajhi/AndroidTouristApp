package com.rayamajs.cwapp;

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

    EditText emailTxt, passwordTxt, confirmTxt;
    Button buttonReg;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__form);
        getSupportActionBar().setTitle("Register (Test)");

        emailTxt = (EditText) findViewById(R.id.email_txt);
        passwordTxt = (EditText) findViewById(R.id.password_txt);
        confirmTxt = (EditText) findViewById(R.id.confirmPass_txt);
        buttonReg = (Button) findViewById(R.id.register_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MemberPage.class));
                String email = emailTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();
                String confirmPassword = confirmTxt.getText().toString().trim();

                if(email.isEmpty()) {
                    Toast.makeText(Sign_Form.this,"Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()) {
                    Toast.makeText(Sign_Form.this,"Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(confirmPassword.isEmpty()) {
                    Toast.makeText(Sign_Form.this,"Confirm Your Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length()<6) {
                    Toast.makeText(Sign_Form.this,"Password is too short", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(Sign_Form.this,"Passwords are not equal", Toast.LENGTH_SHORT).show();
                    return;

                }

                progressBar.setVisibility(View.VISIBLE);

                if (password.equals(confirmPassword)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Sign_Form.this, new OnCompleteListener<AuthResult>() {
                                @Override

                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        //sign in successful

                                        Toast.makeText(Sign_Form.this, "Registered", Toast.LENGTH_SHORT).show();

                                    } else {
                                        //sign in failed
                                        Toast.makeText(Sign_Form.this, "Failed to Authenticate", Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });

                }

            }

        });


    }



}
