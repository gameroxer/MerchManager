package com.example.aric.merchmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Checkout extends AppCompatActivity {
    private MerchTransaction transaction;
    private MerchStockManager stockManager;
    private ArrayList<MerchItem> merchList;

    private Button makeSaleButton;

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
        if (intent.hasExtra("stockManager")) {
            stockManager = (MerchStockManager) intent.getSerializableExtra("stockManager");
        }
        setResult(RESULT_CANCELED);

        makeSaleButton = findViewById(R.id.saleButton);
        transaction.CalculateTotalPrice();
        makeSaleButton.setText(String.format("Total: $%.2f\nMake Sale!", transaction.totalPrice));

        PopulateWithItems();
    }

    public void PopulateWithItems() {
        LinearLayout lst = findViewById(R.id.checkoutItemList);
        for (MerchTransactionItem item : transaction.itemList) {
            CheckoutItemSelector tmp = new CheckoutItemSelector(getApplicationContext());
            tmp.Initialize(item.item, transaction);
            lst.addView(tmp);
        }
    }

    public void MakeSale(View v) {
        //remove stock
        for (MerchTransactionItem item : transaction.itemList) {
            stockManager.RemoveStock(item.item.id, item.amount);
        }

        // save info of transaction
        SharedPreferences stockPrefs = getSharedPreferences("MERCH_STOCK", Context.MODE_PRIVATE);
        SharedPreferences.Editor stockEditor = stockPrefs.edit();

        SharedPreferences transactionPrefs = getSharedPreferences("MERCH_TRANSACTIONS", Context.MODE_PRIVATE);
        SharedPreferences.Editor transactionEditor = transactionPrefs.edit();

        ArrayList<MerchTransaction> transactions = null;

        try {
            transactions = (ArrayList<MerchTransaction>)ObjectSerializer.deserialize(
                    transactionPrefs.getString("conTransactionList", ObjectSerializer.serialize(new ArrayList<>())));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (transactions == null) {
            finish();
        }
        else {
            transaction.date = new Date();
            transactions.add(transaction);

            try {
                stockEditor.putString("conStockManager", ObjectSerializer.serialize(stockManager));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                transactionEditor.putString("conTransactionList", ObjectSerializer.serialize(transactions));
            } catch (IOException e) {
                e.printStackTrace();
            }

            stockEditor.commit();
            transactionEditor.commit();

            setResult(RESULT_OK);
            finish();
        }
    }
}
