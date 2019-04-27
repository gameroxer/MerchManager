package com.example.aric.merchmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {
    private MerchTransaction transaction;
    private ArrayList<MerchItem> merchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Intent intent = getIntent();
        if (intent.hasExtra("transactionData")) {
            transaction = (MerchTransaction)intent.getSerializableExtra("transactionData");
        }
        if (intent.hasExtra("merchList")) {
            merchList = (ArrayList<MerchItem>) intent.getSerializableExtra("merchList");
        }
        setResult(RESULT_CANCELED);
    }

    public void MakeSale(View v) {
        // save info of transaction
        setResult(RESULT_OK);
        finish();
    }
}
