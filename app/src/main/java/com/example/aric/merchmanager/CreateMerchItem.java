package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateMerchItem extends AppCompatActivity {

    public boolean editMerchItem;

    public MerchItem merchItem;
    public int stock;
    public String type;

    EditText name;
    EditText price;
    EditText quantity;

    Spinner setDropdown;

    String merchSet = "";

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
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);

        setDropdown = findViewById(R.id.setDropdown);
        //setDropdown = new Spinner(this);

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

        quantity.setText("0");

        Intent intent = getIntent();
        if (intent.hasExtra("editMerchItem")) {
            editMerchItem = true;
            merchItem = (MerchItem) (intent.getSerializableExtra("merchItem"));
            stock = intent.getIntExtra("stock", 0);
            name.setText(merchItem.name);
            price.setText(String.format("%.2f", merchItem.price));
            quantity.setText(Integer.toString(stock));
            type = merchItem.type;
            merchSet = merchItem.set;

            HighlightTypeButton(GetTypeButton(merchItem.type));
        }
        ArrayList<String> sets;
        if (intent.hasExtra("sets")) {
            sets = (ArrayList<String>)intent.getSerializableExtra("sets");
            sets.add("None");
        }
        else {
            sets = new ArrayList<>();
        }

        //populate spinner with set names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_layout, sets);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        setDropdown.setAdapter(adapter);

        setDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                if (text != "None") merchSet = text;
                else merchSet = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //if the merchSet has been set already (edit) then set the dropdown item to the right one, else set it to "None"
        int index = 0;
        if (merchSet != "") {
            for (int i = 0; i < setDropdown.getCount(); i++) {
                if (setDropdown.getItemAtPosition(i).equals(merchSet)) {
                    index = i;
                    break;
                }
            }
        }
        else index = sets.size() - 1;

        setDropdown.setSelection(index);

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
        float price = Float.parseFloat(this.price.getText().toString());
        int quantity = Integer.parseInt(this.quantity.getText().toString());

        merchItem.name = name;
        merchItem.price = price;
        merchItem.set = merchSet;
        merchItem.type = type;

        Intent merchDetails = new Intent();
        merchDetails.putExtra("merchItem", merchItem);
        merchDetails.putExtra("set", merchSet);
        merchDetails.putExtra("quantity", quantity);
        merchDetails.putExtra("type", type);

        setResult(Activity.RESULT_OK, merchDetails);

        finish();
    }

    private void CompleteMerchCreation(View v) {
        String name = this.name.getText().toString();
        float price = Float.parseFloat(this.price.getText().toString());
        int quantity = Integer.parseInt(this.quantity.getText().toString());

        Intent merchDetails = new Intent();
        merchDetails.putExtra("name", name);
        merchDetails.putExtra("set", merchSet);
        merchDetails.putExtra("price", price);
        merchDetails.putExtra("quantity", quantity);
        merchDetails.putExtra("type", type);

        setResult(Activity.RESULT_OK, merchDetails);

        finish();
    }
}
