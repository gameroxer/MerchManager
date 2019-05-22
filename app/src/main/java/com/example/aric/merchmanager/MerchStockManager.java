package com.example.aric.merchmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MerchStockManager implements Serializable {
    public HashMap<Integer, Integer> stockDictionary;
    public HashMap<Integer, MerchItem> merchDictionary;
    public ArrayList<MerchSet> setDiscounts;

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

    public void ResetStock(int itemId) {
        stockDictionary.put(itemId, 0);
    }

    public void RemoveStock(int itemId, Integer amount) {
        Integer amt = stockDictionary.get(itemId) - amount;
        if (amt < 0) amt = 0;
        stockDictionary.put(itemId, amt);
    }

    public int GenerateNewId() {
        int retVal = merchDictionary.size();
        while (merchDictionary.containsKey(retVal)) {
            retVal++;
        }
        return retVal;
    }

    public void ChangeItemName(int id, String name) {
        MerchItem item = merchDictionary.get(id);
        item.name = name;
        merchDictionary.remove(id);
        merchDictionary.put(id, item);
    }
    public void ChangeItemPrice(int id, float price) {
        MerchItem item = merchDictionary.get(id);
        item.price = price;
        merchDictionary.remove(id);
        merchDictionary.put(id, item);
    }
    public void ChangeItemStock(int id, int stock) {
        stockDictionary.remove(id);
        stockDictionary.put(id, stock);
    }
}
