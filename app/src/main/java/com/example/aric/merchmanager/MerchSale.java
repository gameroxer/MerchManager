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
import java.util.List;

public class MerchSale extends AppCompatActivity {
    private MerchTransaction transaction;
    private boolean checkedOut = false;

    public ArrayList<MerchItem> merchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch_sale);

        Intent intent = getIntent();
        if (intent.hasExtra("merchList")) {
            merchList = (ArrayList<MerchItem>)intent.getSerializableExtra("merchList");
            Log.d("tag", merchList.get(1).name);
//            for (MerchItem item : merchList) {
//                Log.d("tag", item.name);
//            }
        }

        transaction = new MerchTransaction();
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

        startActivityForResult(checkoutIntent, 1);
    }

    public void UpdatePrice() {
        String total = getString(R.string.button_checkout, transaction.totalPrice);
        ((Button)findViewById(R.id.checkoutButton)).setText(total);
    }

    public void PopulateWithItems() {
        LinearLayout lst = findViewById(R.id.merch_list);
        for (MerchItem item : merchList) {
            MerchItemSelector tmp = new MerchItemSelector(getApplicationContext());
            tmp.Initialize(item, transaction);
            lst.addView(tmp);
        }
    }
}
