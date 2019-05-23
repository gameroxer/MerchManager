package com.example.aric.merchmanager;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class TransactionItemSelector extends ConstraintLayout {

    MerchTransaction myTransaction;
    ViewSales parent;

    public TransactionItemSelector(Context context) {
        super(context);
        InitializeViews(context);
    }

    private void InitializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.transaction_item_layout, this);
    }

    public void Initialize(MerchTransaction item, ViewSales parent) {
        myTransaction = item;
        this.parent = parent;

        myTransaction.CalculateTotalPrice();

        String itemListString = "";
        for (MerchTransactionItem transactionItem : myTransaction.itemList) {
            itemListString += transactionItem.item.name + ": " + transactionItem.amount + "x\n";
        }
        itemListString = itemListString.trim();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm, E, MMM dd yyyy");

        TextView itemList = findViewById(R.id.transactionItemList);
        TextView date = findViewById(R.id.transactionDateTime);
        TextView price = findViewById(R.id.totalTransactionPrice);
        itemList.setText(itemListString);
        date.setText(formatter.format(myTransaction.date));
        price.setText(String.format("Total: $%.2f", myTransaction.totalPrice));
    }
}
