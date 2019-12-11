package com.rayamajs.cwapp;

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

    private EditText emailTxt, passwordTxt;
    private Button logBtn;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__form);
        getSupportActionBar().setTitle("Login Form");

        emailTxt = (EditText)findViewById(R.id.email_txt);
        passwordTxt = (EditText) findViewById(R.id.password_txt);
        logBtn = (Button) findViewById(R.id.log_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();

                if(email.isEmpty()) {
                    Toast.makeText(Log_Form.this,"Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()) {
                    Toast.makeText(Log_Form.this,"Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password)
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

                                // ...
                            }
                        });
            }
        });


    }

    public void btn_signForm(View view) {
        startActivity(new Intent(getApplicationContext(), Sign_Form.class));
    }
}
