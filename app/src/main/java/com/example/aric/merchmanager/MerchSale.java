package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerchSale extends AppCompatActivity {
    public MerchTransaction transaction;
    private boolean checkedOut = false;

    public MerchStockManager merchStockManager;

    public Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch_sale);

        Intent intent = getIntent();

        if (intent.hasExtra("stockManager")) {
            merchStockManager = (MerchStockManager)intent.getSerializableExtra("stockManager");
        }

        transaction = new MerchTransaction(merchStockManager.merchDictionary.values().toArray());

        checkoutButton = findViewById(R.id.checkoutButton);

        UpdatePrice();
        PopulateWithItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkedOut) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                checkedOut = true;
            }
        }
    }

    public void Checkout(View v) {
        //start checkout activity
        Intent checkoutIntent = new Intent(this, Checkout.class);
        checkoutIntent.putExtra("transactionData", transaction);
        checkoutIntent.putExtra("stockManager", merchStockManager);

        startActivityForResult(checkoutIntent, 1);
    }

    public void UpdatePrice() {
        transaction.CalculateTotalPrice();
        String total = String.format("Total: $%.2f\nCheckout", transaction.totalPrice);
        checkoutButton.setText(total);
    }

    public void PopulateWithItems() {
        Object[] merchList = merchStockManager.merchDictionary.values().toArray();

        LinearLayout lst = findViewById(R.id.merch_list);
        for (Object item : merchList) {
            MerchItemSelector tmp = new MerchItemSelector(getApplicationContext());
            tmp.Initialize((MerchItem)item, transaction, this, merchStockManager.stockDictionary.get(((MerchItem) item).id));
            lst.addView(tmp);
        }
    }

    public boolean ValidItemQuantity(int id, int quantity) {
        if (merchStockManager.stockDictionary.get(id) >= quantity) return true;
        else return false;
    }
}
