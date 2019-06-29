package com.example.aric.merchmanager;

import android.os.Debug;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MerchTransaction implements Serializable, Comparable<MerchTransaction> {
    public ArrayList<MerchItem> items;
    public ArrayList<MerchTransactionItem> itemList;
    public ArrayList<MerchSet> discounts;
    public float totalPrice;
    public boolean priceOverride = false;
    public boolean usingSquare = false;
    public static float squarePercentage = 0.0265f;
    public Date date;

    public MerchTransaction(Object[] items, Object[] discounts) {
        this.itemList = new ArrayList<>();
        this.items = new ArrayList<>();
        this.discounts = new ArrayList<>();
        for (Object item : items) {
            this.items.add((MerchItem) item);
        }
        for (Object item : discounts) {
            this.discounts.add((MerchSet)item);
        }
    }

    public float CalculateTotalPrice() {
        //for checking set discounts:
        // go through each item and grab its set
        // for each set, check if it is a subset of the list of merch items
        // if it is, add each item to a separate list and remove them from the main list
        // for each valid set, add it to total price, adn then add price of each remaining item

        if (priceOverride) return totalPrice;

        ArrayList<MerchSet> fullSets = new ArrayList<>();
        HashMap<String, MerchSet> sets = new HashMap<>();
        HashMap<MerchItem, Integer> quantities = new HashMap<>();
        for (MerchTransactionItem item : itemList) {
            String set = item.item.set;
            if (!sets.containsKey(set)) sets.put(set, GetSet(set));
            quantities.put(item.item, item.amount);
        }

        for (MerchSet set : sets.values()) {
            ArrayList<MerchItem> setItems = set.itemsInSet;
            //if the items in the set is a subset of the list of items in the transaction,
            //  remove one quantity from the quantities hashmap and add the set to fullSets
            while (quantities.keySet().containsAll(setItems)) {
                fullSets.add(set);
                for (MerchItem item : set.itemsInSet) {
                    Integer temp = quantities.get(item) - 1;
                    quantities.remove(item);
                    if (temp > 0) quantities.put(item, temp);
                }
            }
        }

        float total = 0;

        for (MerchSet set : fullSets) {
            total += set.TotalPrice();
        }
        for (MerchItem item : quantities.keySet()) {
            total += (item.price * quantities.get(item));
        }
//        float total = 0;
//        for (MerchTransactionItem item : itemList) {
//            if (item.hasPriceOverride) {
//                total += item.overridePrice;
//            }
//            else {
//                total += item.item.price * item.amount;
//            }
//
//        }

        if (usingSquare) total = total * (1f + squarePercentage);

        totalPrice = total;

        return total;
    }

    public void ToggleSquare() {
        usingSquare = !usingSquare;
        CalculateTotalPrice();
    }

    public void OverridePrice(float price) {
        priceOverride = true;
        totalPrice = price;
    }

    public MerchSet GetSet(String name) {
        for (MerchSet set : discounts) {
            if (set.name.equals(name)) return set;
        }
        return null;
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
