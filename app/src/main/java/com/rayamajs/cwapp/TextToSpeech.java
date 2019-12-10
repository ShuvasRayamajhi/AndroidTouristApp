package com.rayamajs.cwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class TextToSpeech extends AppCompatActivity {

    EditText txtSpeech;
    Button btnSpeech;
    android.speech.tts.TextToSpeech toSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        txtSpeech = (EditText) findViewById(R.id.speech_text);
        btnSpeech = (Button) findViewById(R.id.btn_speech);

        toSpeech = new android.speech.tts.TextToSpeech(getApplicationContext(), new android.speech.tts.TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != android.speech.tts.TextToSpeech.ERROR) {
                    toSpeech.setLanguage(Locale.UK);
                }
            }
        });

        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSpeech.speak(txtSpeech.getText().toString(), android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }
}
