package com.example.aric.merchmanager;

import java.util.ArrayList;

public class MerchTransaction {
    public ArrayList<MerchTransactionItem> itemList;
    public float totalPrice;

    public void CalculateTotalPrice() {
        float total = 0;
        for (MerchTransactionItem item : itemList) {
            total += item.price;
        }
        totalPrice = total;
        //set text to new total
    }
}
