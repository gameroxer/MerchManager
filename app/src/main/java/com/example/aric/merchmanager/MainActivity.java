package com.example.aric.merchmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    MerchStockManager conStockManager;

    ArrayList<MerchTransaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrieveStockAndTransactions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RetrieveStockAndTransactions();
    }

    public void MakeSaleButtonPressed(View v) {
        Intent intent = new Intent(this, MerchSale.class);
        intent.putExtra("stockManager", conStockManager);
        startActivity(intent);
    }

    public void ManageInventoryButtonPressed(View v) {
        Intent intent = new Intent(this, ManageInventory.class);
        intent.putExtra("stockManager", conStockManager);
        startActivity(intent);
    }

    public void ManageSetsButtonPressed(View v) {
        Intent intent = new Intent(this, SetManager.class);
        intent.putExtra("stockManager", conStockManager);
        startActivity(intent);
    }

    public void ViewSalesButtonPressed(View v) {
        Collections.sort(transactionList, Collections.<MerchTransaction>reverseOrder());

        Intent intent = new Intent(this, ViewSales.class);
        intent.putExtra("transactionList", transactionList);
        startActivity(intent);
    }

    public void AnalyticsButtonPressed(View v) {
        Intent intent = new Intent(this, Analytics.class);
        intent.putExtra("transactionList", transactionList);
        startActivity(intent);
    }

    private void RetrieveStockAndTransactions() {
        SharedPreferences prefs = getSharedPreferences("MERCH_STOCK", Context.MODE_PRIVATE);

        try {
            conStockManager = (MerchStockManager) ObjectSerializer.deserialize(prefs.getString("conStockManager", ObjectSerializer.serialize(new MerchStockManager())));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        prefs = getSharedPreferences("MERCH_TRANSACTIONS", Context.MODE_PRIVATE);

        try {
            transactionList = (ArrayList<MerchTransaction>) ObjectSerializer.deserialize(prefs.getString("conTransactionList", ObjectSerializer.serialize(new ArrayList<>())));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
