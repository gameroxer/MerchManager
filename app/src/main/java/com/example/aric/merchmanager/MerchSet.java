package com.example.aric.merchmanager;

import java.io.Serializable;
import java.util.ArrayList;

public class MerchSet implements Serializable, Comparable<MerchSet> {
    public Integer id;
    public String name;
    public ArrayList<MerchItem> itemsInSet;
    public String type;
    public float discount = 0;

    public MerchSet() {
        itemsInSet = new ArrayList<>();
    }

    public float TotalPrice() {
        float retVal = 0;
        for (MerchItem item : itemsInSet) {
            retVal += item.price;
        }
        retVal -= retVal * discount;
        return retVal;
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(MerchSet o) {
        return this.toString().compareTo(o.toString());
    }
}
