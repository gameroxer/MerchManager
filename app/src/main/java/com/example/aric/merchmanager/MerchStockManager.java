package com.example.aric.merchmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MerchStockManager implements Serializable {
    public HashMap<Integer, Integer> stockDictionary;
    public HashMap<Integer, MerchItem> merchDictionary;
    public HashMap<Integer, MerchSet> setDiscounts;

    public MerchStockManager() {
        stockDictionary = new HashMap<Integer, Integer>();
        merchDictionary = new HashMap<Integer, MerchItem>();
        setDiscounts = new HashMap<Integer, MerchSet>();
    }

    public MerchSet GetSet(String name) {
        for (MerchSet set : setDiscounts.values()) {
            if (name.equals(set.name)) return set;
        }
        return null;
    }

    public void AddSet(MerchSet set) {
        setDiscounts.put(set.id, set);
    }

    public void EditSet(MerchSet set) {
        Integer id = set.id;
        setDiscounts.remove(id);
        setDiscounts.put(id, set);
    }

    public void AddStock(int itemId, Integer amount) {
        if (stockDictionary.containsKey(itemId)) {
            stockDictionary.put(itemId, stockDictionary.get(itemId) + amount);
        }
        else stockDictionary.put(itemId, amount);
    }

    public void CreateItem(int itemId, String name, float price, String type, String set) {
        MerchItem item = new MerchItem();
        item.id = itemId;
        item.name = name;
        item.price = price;
        item.type = type;
        item.set = set;
        merchDictionary.put(itemId, item);

        MerchSet merchSet = GetSet(set);
        merchSet.itemsInSet.add(item);
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

    public int GenerateSetId() {
        int retVal = setDiscounts.size();
        while (setDiscounts.containsKey(retVal)) {
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
    public void ChangeItemSet(int id, String set) {
        //if (set.equals("") || set.equals(null)) return;
        MerchItem item = merchDictionary.get(id);
        String oldSet = item.set;
        item.set = set;

        if ((!set.equals(null) || set.equals("")) && !oldSet.equals("")) {
            GetSet(oldSet).itemsInSet.remove(item);
        }
        else if (!oldSet.equals(set)) {
            GetSet(oldSet).itemsInSet.remove(item);
            GetSet(set).itemsInSet.add(item);
        }

        merchDictionary.remove(id);
        merchDictionary.put(id, item);
    }
    public void ChangeItemType(int id, String type) {
        MerchItem item = merchDictionary.get(id);
        item.type = type;
        merchDictionary.remove(id);
        merchDictionary.put(id, item);
    }
    public void ChangeItemStock(int id, int stock) {
        stockDictionary.remove(id);
        stockDictionary.put(id, stock);
    }
}
