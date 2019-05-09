package com.example.aric.merchmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MerchStockManager implements Serializable {
    private HashMap<Integer, Integer> stockDictionary;
    private HashMap<Integer, MerchItem> merchDictionary;
    private ArrayList<MerchSet> setDiscounts;

    public MerchStockManager() {
        stockDictionary = new HashMap<Integer, Integer>();
        merchDictionary = new HashMap<Integer, MerchItem>();
        setDiscounts = new ArrayList<MerchSet>();
    }

    public void AddStock(int itemId, Integer amount) {
        if (stockDictionary.containsKey(itemId)) {
            stockDictionary.put(itemId, stockDictionary.get(itemId) + amount);
        }
        else stockDictionary.put(itemId, amount);
    }

    public void CreateItem(int itemId, String name, float price) {
        MerchItem item = new MerchItem();
        item.id = itemId;
        item.name = name;
        item.price = price;
        merchDictionary.put(itemId, item);
    }

    public void ResetStock(int nameOfItem) {
        stockDictionary.put(nameOfItem, 0);
    }

    public void RemoveStock(int nameOfItem, Integer amount) {
        Integer amt = stockDictionary.get(nameOfItem) - amount;
        if (amt < 0) amt = 0;
        stockDictionary.put(nameOfItem, amt);
    }

    public int GenerateNewId() {
        int retVal = merchDictionary.size();
        while (merchDictionary.containsKey(retVal)) {
            retVal++;
        }
        return retVal;
    }
}
