package com.example.aric.merchmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Checkout extends AppCompatActivity {
    private MerchTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Intent intent = getIntent();
        if (intent.hasExtra("transactionData")) {
            transaction = (MerchTransaction)intent.getSerializableExtra("transactionData");
        }
    }

    public void MakeSale(View v) {
        // save info of transaction

        finish();
    }
}
