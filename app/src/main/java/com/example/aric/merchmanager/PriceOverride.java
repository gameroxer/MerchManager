package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PriceOverride extends AppCompatActivity {

    MerchTransaction transaction;

    float oldPrice;

    EditText priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_override);

        Intent intent = getIntent();
        transaction = (MerchTransaction) intent.getSerializableExtra("merchTransaction");
        transaction.CalculateTotalPrice();

        oldPrice = transaction.totalPrice;

        priceText = (findViewById(R.id.newPrice));
        priceText.setText(String.format("%.2f", transaction.totalPrice));
    }

    public void ChangePriceButtonPressed(View v) {
        float price = Float.parseFloat(priceText.getText().toString());

        Intent intent = new Intent();

        if (price == oldPrice) {
            setResult(Activity.RESULT_CANCELED, intent);
        }
        else {
            intent.putExtra("newPrice", price);
            setResult(Activity.RESULT_OK, intent);
        }

        finish();
    }
}
