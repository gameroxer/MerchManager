package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateMerchItem extends AppCompatActivity {

    public boolean editMerchItem;

    public MerchItem merchItem;
    public int stock;

    EditText name;
    EditText set;
    EditText price;
    EditText quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_merch_item);

        name = (findViewById(R.id.name));
        set = (findViewById(R.id.set));
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);

        Intent intent = getIntent();
        if (intent.hasExtra("editMerchItem")) {
            editMerchItem = true;
            merchItem = (MerchItem) (intent.getSerializableExtra("merchItem"));
            stock = intent.getIntExtra("stock", 0);
            name.setText(merchItem.name);
            price.setText(String.format("%.2f", merchItem.price));
            quantity.setText(Integer.toString(stock));
        }
        setResult(Activity.RESULT_CANCELED);
    }

    public void CreateItemPressed(View v) {
        if (editMerchItem) {
            CompleteMerchEdit(v);
        }
        else {
            CompleteMerchCreation(v);
        }
    }

    private void CompleteMerchEdit(View v) {
        String name = this.name.getText().toString();
        String set = this.set.getText().toString();
        float price = Float.parseFloat(this.price.getText().toString());
        int quantity = Integer.parseInt(this.quantity.getText().toString());

        merchItem.name = name;
        merchItem.price = price;

        Intent merchDetails = new Intent();
        merchDetails.putExtra("merchItem", merchItem);
        merchDetails.putExtra("set", set);
        merchDetails.putExtra("quantity", quantity);

        setResult(Activity.RESULT_OK, merchDetails);

        finish();
    }

    private void CompleteMerchCreation(View v) {
        String name = this.name.getText().toString();
        String set = this.set.getText().toString();
        float price = Float.parseFloat(this.price.getText().toString());
        int quantity = Integer.parseInt(this.quantity.getText().toString());

        Intent merchDetails = new Intent();
        merchDetails.putExtra("name", name);
        merchDetails.putExtra("set", set);
        merchDetails.putExtra("price", price);
        merchDetails.putExtra("quantity", quantity);

        setResult(Activity.RESULT_OK, merchDetails);

        finish();
    }
}
