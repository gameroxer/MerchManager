package com.example.aric.merchmanager;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SetSelector extends ConstraintLayout {

    MerchSet set;
    SetManager parent;


    public SetSelector(Context context) {
        super(context);
        InitializeViews(context);

        (findViewById(R.id.editButton)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditButtonPressed(v);
            }
        });
    }

    public void EditButtonPressed(View v) {
        Intent intent = new Intent(parent, CreateSet.class);
        intent.putExtra("editSet", true);
        intent.putExtra("merchSet", set);
        parent.startActivityForResult(intent, SetManager.EDIT_SET_RESULT);
    }

    private void InitializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.set_item_layout, this);
    }

    public void Initialize(MerchSet set, SetManager parent) {
        this.set = set;
        this.parent = parent;

        TextView name = findViewById(R.id.setName);
        TextView setType = findViewById(R.id.setType);
        TextView discount = findViewById(R.id.setDiscount);
        name.setText(set.toString());
        String str = set.type;
        setType.setText(String.format("Type: %s", str.substring(0, 1).toUpperCase() + str.substring(1)));

        float dc = set.discount * 100f;
        if (dc%1 != 0) discount.setText(String.format("Discount: %.2f%%", dc));
        else discount.setText(String.format("Discount: %.0f%%", dc));
    }
}
