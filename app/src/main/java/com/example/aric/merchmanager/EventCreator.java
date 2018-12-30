package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;

public class EventCreator extends AppCompatActivity {
    public static String EVENT_CREATE_SUCCESS = "EVENT_CREATE_SUCCESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator);

        Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEvent();
            }
        });
    }

    public void CreateEvent() {
        String eventName = ((EditText)findViewById(R.id.eventName)).getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EVENT_CREATE_SUCCESS, eventName);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();

    }
}
