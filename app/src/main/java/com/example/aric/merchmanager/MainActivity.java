package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int SALE_MAKE_ACTIVITY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (SALE_MAKE_ACTIVITY_RESULT) : {
                break;
            }
        }
    }

    public void MakeSaleButtonPressed(View v) {
        Intent intent = new Intent(this, MerchSale.class);
        startActivityForResult(intent, SALE_MAKE_ACTIVITY_RESULT);
    }
}
