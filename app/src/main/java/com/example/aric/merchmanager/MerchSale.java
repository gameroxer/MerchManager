package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MerchSale extends AppCompatActivity {
    private MerchTransaction transaction;
    private boolean checkedOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch_sale);

        transaction = new MerchTransaction();
        UpdatePrice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkedOut) {
            finish();
        }
    }

    public void Checkout(View v) {
        //start checkout activity
        Intent checkoutIntent = new Intent(this, Checkout.class);
        checkoutIntent.putExtra("transactionData", transaction);

        startActivity(checkoutIntent);
        checkedOut = true;
    }

    public void UpdatePrice() {
        String total = getString(R.string.button_checkout, transaction.totalPrice);
        ((Button)findViewById(R.id.checkoutButton)).setText(total);
    }

    public void PopulateWithItems() {
        //used to create all the MerchItems
    }
}
