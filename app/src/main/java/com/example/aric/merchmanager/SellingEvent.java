package com.example.aric.merchmanager;

import java.util.ArrayList;

public class SellingEvent {
    public String eventName;
    public MerchStockManager merchStockManager;
    public ArrayList<MerchItem> merchItems;

    public SellingEvent(String name) {
        eventName = name;
        merchStockManager = new MerchStockManager();
        merchItems = new ArrayList<MerchItem>();
    }

}
