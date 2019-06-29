package com.example.aric.merchmanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class CreateSet extends AppCompatActivity {

    public boolean editSet;

    public MerchSet merchSet;
    public String type;

    EditText name;
    EditText discount;

    Button buttonsButton;
    Button charmsButton;
    Button omanjuuButton;
    Button postcardsButton;
    Button printsButton;
    Button stationeryButton;
    Button stickersButton;
    Button otherButton;

    ArrayList<Button> typeButtons;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_set);
        name = (findViewById(R.id.name));
        discount = (findViewById(R.id.discount));

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
        if (intent.hasExtra("editSet")) {
            editSet = true;
            merchSet = (MerchSet) (intent.getSerializableExtra("merchSet"));
            name.setText(merchSet.name);
            float dc = merchSet.discount * 100f;
            if (dc%1 != 0) discount.setText(String.format("%.2f", dc));
            else discount.setText(String.format("%.0f", dc));
            type = merchSet.type;

            HighlightTypeButton(GetTypeButton(merchSet.type));
        }
        else {
            discount.setText("20");
        }
        setResult(Activity.RESULT_CANCELED);
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

    private void CompleteSetEdit(View v) {
        String name = this.name.getText().toString();
        float discount = Float.parseFloat(this.discount.getText().toString()) / 100f;

        merchSet.name = name;
        merchSet.discount = discount;
        merchSet.type = type;

        Intent setDetails = new Intent();
        setDetails.putExtra("merchSet", merchSet);

        setResult(Activity.RESULT_OK, setDetails);

        finish();
    }

    private void CompleteSetCreation(View v) {
        String name = this.name.getText().toString();
        float discount = Float.parseFloat(this.discount.getText().toString()) / 100f;

        Intent setDetails = new Intent();
        setDetails.putExtra("name", name);
        setDetails.putExtra("discount", discount);
        setDetails.putExtra("type", type);

        setResult(Activity.RESULT_OK, setDetails);

        finish();
    }

    public void CreateSetPressed(View v) {
        if (editSet) {
            CompleteSetEdit(v);
        }
        else {
            CompleteSetCreation(v);
        }
    }
}
