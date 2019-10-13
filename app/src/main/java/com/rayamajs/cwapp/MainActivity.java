
package com.rayamajs.cwapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //declaring variables
    private EditText User;
    private EditText Pass;
    private Button Log;
    private Button Restart;
    private TextView Attempt;
    private int counter =5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User = (EditText)findViewById(R.id.eUser);
        Pass = (EditText)findViewById(R.id.ePass);
        Log = (Button) findViewById(R.id.bLog);
        Attempt = (TextView)findViewById(R.id.tAttempt);
        Attempt.setText("Attempts left:  5");
        Restart = (Button) findViewById(R.id.bRestart);

        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation(User.getText().toString(), Pass.getText().toString());
            }
        });

        Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    private void validation (String uName, String uPassword) {
        if ((uName.equals("admin")) && (uPassword.equals("123"))){
            Intent intent = new Intent(MainActivity.this, MemberPage.class);
            startActivity(intent);
        }
        else {
            counter--;

            Attempt.setText("Attempts left: " + String.valueOf(counter));

            if (counter == 0) {
                Log.setEnabled(false);
            }
        }
    }
}
