package com.example.aric.merchmanager;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

public class CheckoutItemSelector extends ConstraintLayout {

    TextView price;

    MerchItem myItem;
    MerchTransaction parentTransaction;

    public CheckoutItemSelector(Context context) {
        super(context);
        InitializeViews(context);
    }

    public CheckoutItemSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitializeViews(context);
    }

    public CheckoutItemSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        InitializeViews(context);
    }

    private void InitializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.checkout_item_layout, this);
    }

    public void Initialize(MerchItem item, MerchTransaction transaction) {
        myItem = item;
        parentTransaction = transaction;

        TextView name = findViewById(R.id.merch_name);
        TextView quantity = findViewById(R.id.merch_stock);
        price = findViewById(R.id.total_price);

        name.setText(myItem.toString());
        quantity.setText(String.format("Quantity: %d", parentTransaction.GetMerchQuantity(myItem.id)));
        price.setText(String.format("$%.2f", parentTransaction.GetMerchTotal(myItem.id)));
    }
}
