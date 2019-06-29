package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ManageInventory extends AppCompatActivity {
    public static final int CREATE_ITEM_RESULT = 1;
    public static final int EDIT_ITEM_RESULT = 2;

    MerchStockManager merchStockManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_inventory);
        Intent intent = getIntent();

        if (intent.hasExtra("stockManager")) {
            merchStockManager = (MerchStockManager)intent.getSerializableExtra("stockManager");
        }
        if (merchStockManager == null) merchStockManager = new MerchStockManager();

        PopulateWithMerch();
    }

    public void CreateMerchButtonPressed(View v) {
        Intent intent = new Intent(this, CreateMerchItem.class);
        ArrayList<String> sets = new ArrayList<>();
        for (MerchSet set : merchStockManager.setDiscounts.values()) {
            sets.add(set.name);
        }
        intent.putExtra("sets", sets);
        startActivityForResult(intent, CREATE_ITEM_RESULT);
    }

    public void PopulateWithMerch() {
        LinearLayout lst = findViewById(R.id.inventory_list);
        HashMap<Integer, MerchItem> merchMap = SortHashMap(merchStockManager.merchDictionary);
        for (Map.Entry<Integer, MerchItem> entry : merchMap.entrySet()) {
            InventoryItemSelector tmp = new InventoryItemSelector(getApplicationContext());
            tmp.Initialize(entry.getValue(), merchStockManager.stockDictionary.get(entry.getKey()), this);
            lst.addView(tmp);
        }
    }

    public HashMap<Integer, MerchItem> SortHashMap(HashMap<Integer, MerchItem> map) {
        List<Map.Entry<Integer, MerchItem>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, MerchItem>>() {
            @Override
            public int compare(Map.Entry<Integer, MerchItem> o1, Map.Entry<Integer, MerchItem> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        HashMap<Integer, MerchItem> tmp = new LinkedHashMap<>();
        for (Map.Entry<Integer, MerchItem> item : list) {
            tmp.put(item.getKey(), item.getValue());
        }
        return tmp;
    }

    public void RefreshMerchList() {
        LinearLayout lst = findViewById(R.id.inventory_list);
        lst.removeAllViews();
        PopulateWithMerch();
    }

    public void SaveStockManager() {
        SharedPreferences prefs = getSharedPreferences("MERCH_STOCK", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        try {
            editor.putString("conStockManager", ObjectSerializer.serialize(merchStockManager));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (CREATE_ITEM_RESULT) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle dataBundle = data.getExtras();
                    String name = dataBundle.getString("name");
                    String set = dataBundle.getString("set");
                    String type = dataBundle.getString("type");
                    float price = dataBundle.getFloat("price");
                    int quantity = dataBundle.getInt("quantity");
                    int id = merchStockManager.GenerateNewId();

                    merchStockManager.CreateItem(id, name, price, type, set);
                    merchStockManager.AddStock(id, quantity);

                    SaveStockManager();
                    RefreshMerchList();
                }
                break;
            }
            case (EDIT_ITEM_RESULT) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle dataBundle = data.getExtras();
                    MerchItem merchItem = (MerchItem) dataBundle.getSerializable("merchItem");
                    String set = dataBundle.getString("set");
                    String type = dataBundle.getString("type");
                    int quantity = dataBundle.getInt("quantity");

                    merchStockManager.ChangeItemName(merchItem.id, merchItem.name);
                    merchStockManager.ChangeItemPrice(merchItem.id, merchItem.price);
                    merchStockManager.ChangeItemStock(merchItem.id, quantity);
                    merchStockManager.ChangeItemSet(merchItem.id, set);
                    merchStockManager.ChangeItemType(merchItem.id, type);

                    SaveStockManager();
                    RefreshMerchList();
                }
            }
        }
    }
}
