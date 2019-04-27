package com.example.aric.merchmanager;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aric.merchmanager.R;

public class MerchItemSelector extends ConstraintLayout {

    MerchItem myItem;
    MerchTransaction parentTransaction;

    int currentQuantity;

    public MerchItemSelector(Context context) {
        super(context);
        InitializeViews(context);
    }

    public MerchItemSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitializeViews(context);
    }

    public MerchItemSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        InitializeViews(context);
    }

    private void InitializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.merch_item_layout, this);
    }

    public void AddQuantity(View v) {
        currentQuantity++;
    }

    public void RemoveQuantity(View v) {
        if (currentQuantity > 0) {
            currentQuantity--;
        }
    }

    public void Initialize(MerchItem item, MerchTransaction transaction) {
        myItem = item;
        parentTransaction = transaction;

        TextView name = findViewById(R.id.merch_name);
        TextView price = findViewById(R.id.merch_price);
        name.setText(myItem.name);
        price.setText(String.format("%.2f", myItem.price));
    }

}
