package com.example.aric.merchmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MerchTransaction implements Serializable {
    public ArrayList<MerchTransactionItem> itemList;
    public float totalPrice;
    public boolean priceOverride = false;
    public Date date;

    public void CalculateTotalPrice() {
        float total = 0;
        for (MerchTransactionItem item : itemList) {
            if (item.hasPriceOverride) {
                total += item.overridePrice;
            }
            else {
                total += item.item.price * item.amount;
            }

        }
        totalPrice = total;
        //set text to new total
    }

    public void OverridePrice(float price) {
        priceOverride = true;
        totalPrice = price;
    }
}
