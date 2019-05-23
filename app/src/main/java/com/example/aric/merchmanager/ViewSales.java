package com.example.aric.merchmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;

public class ViewSales extends AppCompatActivity {
    ArrayList<MerchTransaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sales);
        Intent intent = getIntent();

        if (intent.hasExtra("transactionList")) {
            transactionList = (ArrayList<MerchTransaction>) intent.getSerializableExtra("transactionList");
        }

        PopulateWithTransactions();
    }

    public void PopulateWithTransactions() {
        LinearLayout lst = findViewById(R.id.transactionItems);
        for (MerchTransaction transaction : transactionList) {
            TransactionItemSelector tmp = new TransactionItemSelector(getApplicationContext());
            tmp.Initialize(transaction, this);
            lst.addView(tmp);
        }
    }
}
