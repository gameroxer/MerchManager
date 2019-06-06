package com.example.aric.merchmanager;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MerchItemSelector extends ConstraintLayout {

    TextView quantity;
    TextView stock;

    MerchItem myItem;
    MerchTransaction parentTransaction;
    MerchSale parentSale;

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
        if (!parentSale.ValidItemQuantity(myItem.id, currentQuantity + 1)) return;

        currentQuantity++;
        quantity.setText(String.valueOf(currentQuantity));

        parentTransaction.AddItem(myItem.id);
        parentSale.UpdatePrice();
    }

    public void RemoveQuantity(View v) {
        if (currentQuantity > 0) {
            currentQuantity--;
            parentTransaction.SubtractItem(myItem.id);
        }
        quantity.setText(String.valueOf(currentQuantity));
        parentSale.UpdatePrice();
    }

    public void Initialize(MerchItem item, MerchTransaction transaction, MerchSale merchSale, int stock) {
        myItem = item;
        parentTransaction = transaction;
        parentSale = merchSale;

        Button plusButton = findViewById(R.id.button_plus);
        Button minusButton = findViewById(R.id.button_minus);

        plusButton.setTag(myItem.id);
        minusButton.setTag(myItem.id);
        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AddQuantity(v);
            }
        });

        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveQuantity(v);
            }
        });

        TextView name = findViewById(R.id.merch_name);
        TextView price = findViewById(R.id.total_price);
        quantity = findViewById(R.id.merch_quantity);
        this.stock = findViewById(R.id.merch_stock);

        name.setText(myItem.toString());
        price.setText(String.format("$%.2f", myItem.price));
        this.stock.setText(String.format("Stock: %d", stock));
        quantity.setTag(myItem.id + "Quantity");
    }

}
