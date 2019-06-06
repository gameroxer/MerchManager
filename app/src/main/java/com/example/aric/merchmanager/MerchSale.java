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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MerchSale extends AppCompatActivity {
    public MerchTransaction transaction;
    private boolean checkedOut = false;

    public MerchStockManager merchStockManager;

    public Button checkoutButton;

    Button buttonsButton;
    Button charmsButton;
    Button omanjuuButton;
    Button postcardsButton;
    Button printsButton;
    Button stationeryButton;
    Button stickersButton;
    Button otherButton;

    ArrayList<Button> typeButtons;

    String filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch_sale);

        buttonsButton = findViewById(R.id.buttonsButton);
        charmsButton = findViewById(R.id.charmsButton);
        omanjuuButton = findViewById(R.id.omanjuuButton);
        postcardsButton = findViewById(R.id.postcardsButton);
        printsButton = findViewById(R.id.printsButton);
        stationeryButton = findViewById(R.id.stationeryButton);
        stickersButton = findViewById(R.id.stickersButton);
        otherButton = findViewById(R.id.otherButton);

        typeButtons = new ArrayList<>();
        typeButtons.add(buttonsButton);
        typeButtons.add(charmsButton);
        typeButtons.add(omanjuuButton);
        typeButtons.add(postcardsButton);
        typeButtons.add(printsButton);
        typeButtons.add(stationeryButton);
        typeButtons.add(stickersButton);
        typeButtons.add(otherButton);

        for (Button btn : typeButtons) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TypeButtonPressed(v);
                }
            });
        }

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

    public void TypeButtonPressed(View v) {
        Button btn = (Button)v;
        filter = btn.getText().toString().toLowerCase();

        HighlightTypeButton(btn);
        RefreshMerchList();
    }

    public void HighlightTypeButton(Button btn) {
        if (btn == null) return;
        for (Button button : typeButtons) {
            button.setBackgroundColor(getResources().getColor(R.color.button_color));
        }
        btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    public void UpdatePrice() {
        transaction.CalculateTotalPrice();
        String total = String.format("Total: $%.2f\nCheckout", transaction.totalPrice);
        checkoutButton.setText(total);
    }

    public void PopulateWithItems() {
        ArrayList<MerchItem> merchList = FilterList(merchStockManager.merchDictionary.values().toArray(), filter);
        Collections.sort(merchList);

        LinearLayout lst = findViewById(R.id.merch_list);
        for (MerchItem item : merchList) {
            MerchItemSelector tmp = new MerchItemSelector(getApplicationContext());
            tmp.Initialize(item, transaction, this, merchStockManager.stockDictionary.get(item.id));
            lst.addView(tmp);
        }
    }

    public void RefreshMerchList() {
        LinearLayout lst = findViewById(R.id.merch_list);
        lst.removeAllViews();
        PopulateWithItems();
    }

    public ArrayList<MerchItem> FilterList(Object[] merchList, String filter) {
        ArrayList<MerchItem> retArray = new ArrayList<>();

        if (filter == null) {
            for (Object item : merchList) {
                retArray.add((MerchItem) item);
            }
            return retArray;
        }

        for (Object item : merchList) {
            if (((MerchItem)item).type.equals(filter)) retArray.add((MerchItem)item);
        }

        return retArray;
    }

    public boolean ValidItemQuantity(int id, int quantity) {
        if (merchStockManager.stockDictionary.get(id) >= quantity) return true;
        else return false;
    }
}
