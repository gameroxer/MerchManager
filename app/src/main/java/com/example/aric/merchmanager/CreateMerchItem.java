package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CreateMerchItem extends AppCompatActivity {

    public boolean editMerchItem;

    public MerchItem merchItem;
    public int stock;
    public String type;

    EditText name;
    EditText set;
    EditText price;
    EditText quantity;

    Button buttonsButton;
    Button charmsButton;
    Button omanjuuButton;
    Button postcardsButton;
    Button printsButton;
    Button stationeryButton;
    Button stickersButton;
    Button otherButton;

    ArrayList<Button> typeButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_merch_item);

        name = (findViewById(R.id.name));
        set = (findViewById(R.id.set));
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);

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
        if (intent.hasExtra("editMerchItem")) {
            editMerchItem = true;
            merchItem = (MerchItem) (intent.getSerializableExtra("merchItem"));
            stock = intent.getIntExtra("stock", 0);
            name.setText(merchItem.name);
            price.setText(String.format("%.2f", merchItem.price));
            set.setText(merchItem.set);
            quantity.setText(Integer.toString(stock));
            type = merchItem.type;

            HighlightTypeButton(GetTypeButton(merchItem.type));
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

    public void TypeButtonPressed(View v) {
        Button btn = (Button)v;
        type = btn.getText().toString().toLowerCase();

        HighlightTypeButton(btn);
    }

    public void HighlightTypeButton(Button btn) {
        if (btn == null) return;
        for (Button button : typeButtons) {
            button.setBackgroundColor(getResources().getColor(R.color.button_color));
        }
        btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    public Button GetTypeButton(String type) {
        if (type.equals("buttons")) return buttonsButton;
        if (type.equals("charms")) return charmsButton;
        if (type.equals("omanjuu")) return omanjuuButton;
        if (type.equals("postcards")) return postcardsButton;
        if (type.equals("prints")) return printsButton;
        if (type.equals("stationery")) return stationeryButton;
        if (type.equals("stickers")) return stickersButton;
        if (type.equals("other")) return otherButton;
        return null;
    }

    private void CompleteMerchEdit(View v) {
        String name = this.name.getText().toString();
        String set = this.set.getText().toString();
        float price = Float.parseFloat(this.price.getText().toString());
        int quantity = Integer.parseInt(this.quantity.getText().toString());

        merchItem.name = name;
        merchItem.price = price;
        merchItem.set = set;
        merchItem.type = type;

        Intent merchDetails = new Intent();
        merchDetails.putExtra("merchItem", merchItem);
        merchDetails.putExtra("set", set);
        merchDetails.putExtra("quantity", quantity);
        merchDetails.putExtra("type", type);

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
        merchDetails.putExtra("type", type);

        setResult(Activity.RESULT_OK, merchDetails);

        finish();
    }
}
