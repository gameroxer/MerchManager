package com.example.aric.merchmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ViewSales extends AppCompatActivity {
    ArrayList<MerchTransaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sales);
        Intent intent = getIntent();

        if (intent.hasExtra("transactionList")) {
            transactionList = (ArrayList<MerchTransaction>) intent.getSerializableExtra("stockManager");
        }

        PopulateWithTransactions();
    }

    public void PopulateWithTransactions() {

    }
}
