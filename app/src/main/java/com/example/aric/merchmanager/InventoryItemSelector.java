package com.example.aric.merchmanager;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class InventoryItemSelector extends ConstraintLayout {

    MerchItem myItem;
    ManageInventory parent;

    public int currentStock;

    public InventoryItemSelector(Context context) {
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
        Intent intent = new Intent(parent, CreateMerchItem.class);
        intent.putExtra("editMerchItem", true);
        intent.putExtra("merchItem", myItem);
        intent.putExtra("stock", parent.merchStockManager.stockDictionary.get(myItem.id));
        ArrayList<String> sets = new ArrayList<>();
        for (MerchSet set : parent.merchStockManager.setDiscounts.values()) {
            sets.add(set.name);
        }
        intent.putExtra("sets", sets);
        parent.startActivityForResult(intent, ManageInventory.EDIT_ITEM_RESULT);
    }

    private void InitializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.inventory_item_layout, this);
    }

    public void Initialize(MerchItem item, int stock, ManageInventory parent) {
        myItem = item;
        currentStock = stock;
        this.parent = parent;

        TextView name = findViewById(R.id.itemName);
        TextView stockTxt = findViewById(R.id.itemStock);
        TextView price = findViewById(R.id.itemPrice);
        name.setText(myItem.toString());
        stockTxt.setText(String.format("Number in stock: %d", stock));
        price.setText(String.format("$%.2f", myItem.price));
    }
}
