package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class EventSelector extends AppCompatActivity {
    ArrayList<String> events;
    public static final int EVENT_CREATE_ACTIVITY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddButtonPressed();
            }
        });

        events = new ArrayList<String>();
        //read all events from file
        SharedPreferences prefs = getSharedPreferences("EVENT_LIST", Context.MODE_PRIVATE);

        try {
            events = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("events", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String txt = "";

        for (String str : events) {
            txt += str + ", ";
        }

        TextView testText = findViewById(R.id.testText);

        if (events.size() > 0) testText.setText(txt);
    }

    public void CreateEvent(String name) {
        SharedPreferences prefs = getSharedPreferences("EVENT_LIST", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        events.add(name);

        try {
            editor.putString("events", ObjectSerializer.serialize(events));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();

        //create buttons and shit
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (EVENT_CREATE_ACTIVITY_RESULT) : {
                if (resultCode == Activity.RESULT_OK) {
                    String newEvent = data.getStringExtra(EventCreator.EVENT_CREATE_SUCCESS);

                    CreateEvent(newEvent);

                    SharedPreferences prefs = getSharedPreferences("EVENT_LIST", Context.MODE_PRIVATE);

                    try {
                        events = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("events", ObjectSerializer.serialize(new ArrayList<String>())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    String txt = "";

                    for (String str : events) {
                        txt += str + ", ";
                    }

                    TextView testText = findViewById(R.id.testText);

                    if (events.size() > 0) testText.setText(txt);
                }
                break;
            }
        }
    }

    public void AddButtonPressed() {
        Intent intent = new Intent(this, EventCreator.class);
        startActivityForResult(intent, EVENT_CREATE_ACTIVITY_RESULT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
