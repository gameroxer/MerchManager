package com.example.aric.merchmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MerchTransaction implements Serializable, Comparable<MerchTransaction> {
    public ArrayList<MerchItem> items;
    public ArrayList<MerchTransactionItem> itemList;
    public float totalPrice;
    public boolean priceOverride = false;
    public Date date;

    public MerchTransaction(Object[] items) {
        this.itemList = new ArrayList<MerchTransactionItem>();
        this.items = new ArrayList<MerchItem>();
        for (Object item : items) {
            this.items.add((MerchItem) item);
        }
    }

    public void CalculateTotalPrice() {
        if (priceOverride) return;
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
    }

    public void OverridePrice(float price) {
        priceOverride = true;
        totalPrice = price;
    }

    public void AddItem(int id) {
        //increment proper item in itemList
        MerchTransactionItem currItem = null;
        for (MerchTransactionItem item : itemList) {
            if (item.GetId() == id) {
                item.amount++;
                return;
            }
        }
        if (currItem == null) {
            MerchItem item = GetMerchItem(id);
            MerchTransactionItem transactionItem = new MerchTransactionItem();
            transactionItem.item = item;
            transactionItem.amount = 1;
            itemList.add(transactionItem);
        }
        CalculateTotalPrice();
    }

    public void SubtractItem(int id) {
        //decrement proper item in itemList
        MerchTransactionItem currItem = null;
        for (MerchTransactionItem item : itemList) {
            if (item.GetId() == id) {
                item.amount--;
                if (item.amount == 0) itemList.remove(item);
                return;
            }
        }
        CalculateTotalPrice();
    }

    public MerchItem GetMerchItem(int id) {
        for (MerchItem item : items) {
            if (item.id == id) return item;
        }
        return null;
    }

    public float GetMerchTotal(int id) {
        for (MerchTransactionItem item : itemList) {
            if (item.GetId() == id) {
                return item.amount * item.item.price;
            }
        }
        return -1f;
    }

    public int GetMerchQuantity(int id) {
        for (MerchTransactionItem item : itemList) {
            if (item.GetId() == id) {
                return item.amount;
            }
        }
        return -1;
    }

    @Override
    public int compareTo(MerchTransaction o) {
        return this.date.compareTo(o.date);
    }
}
