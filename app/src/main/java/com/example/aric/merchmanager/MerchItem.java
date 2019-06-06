package com.example.aric.merchmanager;

import java.io.Serializable;

public class MerchItem  implements Serializable, Comparable<MerchItem> {
    public String name;
    public int id;
    public float price;
    public String type;
    public String set;

    @Override
    public String toString() {
        String retString = "";
        if (type != null) retString += "[" + type.substring(0, 1).toUpperCase() + type.substring(1) + "] ";
        if (set != null) retString +=  "[" + set + "] ";
        retString += name;

        return retString;
    }

    @Override
    public int compareTo(MerchItem o) {
        return this.toString().compareTo(o.toString());
    }
}
