package com.example.aric.merchmanager;

import java.util.ArrayList;
import java.util.HashMap;

public class MerchStockManager {
    private HashMap<String, Integer> stockDictionary;
    private ArrayList<MerchSet> setDiscounts;

    public MerchStockManager() {
        stockDictionary = new HashMap<String, Integer>();
        setDiscounts = new ArrayList<MerchSet>();
    }

    public void AddStock(String nameOfItem, Integer amount) {
        if (stockDictionary.containsKey(nameOfItem)) {
            stockDictionary.put(nameOfItem, stockDictionary.get(nameOfItem) + amount);
        }
        else stockDictionary.put(nameOfItem, amount);
    }

    public void ResetStock(String nameOfItem) {
        stockDictionary.put(nameOfItem, 0);
    }

    public void RemoveStock(String nameOfItem, Integer amount) {
        Integer amt = stockDictionary.get(nameOfItem) - amount;
        if (amt < 0) amt = 0;
        stockDictionary.put(nameOfItem, amt);
    }
}
