package com.example.aric.merchmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class CreateMerchItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_merch_item);
    }

    void CompleteMerchCreation() {
        String name = ((EditText)findViewById(R.id.name)).getText().toString();
        String set = ((EditText)findViewById(R.id.set)).getText().toString();
        float price = Float.parseFloat(((EditText)findViewById(R.id.price)).getText().toString());
        int quantity = Integer.parseInt(((EditText)findViewById(R.id.quantity)).getText().toString());

        Intent merchDetails = new Intent();
        merchDetails.putExtra("name", name);
        merchDetails.putExtra("set", set);
        merchDetails.putExtra("price", price);
        merchDetails.putExtra("quantity", quantity);
    }
}
