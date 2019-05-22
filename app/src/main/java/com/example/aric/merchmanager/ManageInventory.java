package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;

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

        PopulateWithMerch();
    }

    public void CreateMerchButtonPressed(View v) {
        Intent intent = new Intent(this, CreateMerchItem.class);
        startActivityForResult(intent, CREATE_ITEM_RESULT);
    }

    public void PopulateWithMerch() {
        LinearLayout lst = findViewById(R.id.inventory_list);
        for (Integer key : merchStockManager.merchDictionary.keySet()) {
            InventoryItemSelector tmp = new InventoryItemSelector(getApplicationContext());
            tmp.Initialize(merchStockManager.merchDictionary.get(key), merchStockManager.stockDictionary.get(key), this);
            lst.addView(tmp);
        }
    }

    public void RefreshMerchList() {
        LinearLayout lst = findViewById(R.id.inventory_list);
        lst.removeAllViews();
        PopulateWithMerch();
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
                    float price = dataBundle.getFloat("price");
                    int quantity = dataBundle.getInt("quantity");
                    int id = merchStockManager.GenerateNewId();

                    merchStockManager.CreateItem(id, name, price);
                    merchStockManager.AddStock(id, quantity);

                    SharedPreferences prefs = getSharedPreferences("MERCH_STOCK", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    try {
                        editor.putString("conStockManager", ObjectSerializer.serialize(merchStockManager));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    editor.commit();

                    RefreshMerchList();
                }
                break;
            }
            case (EDIT_ITEM_RESULT) : {
                Bundle dataBundle = data.getExtras();
                MerchItem merchItem = (MerchItem) dataBundle.getSerializable("merchItem");
                String set = dataBundle.getString("set");
                int quantity = dataBundle.getInt("quantity");

                merchStockManager.ChangeItemName(merchItem.id, merchItem.name);
                merchStockManager.ChangeItemPrice(merchItem.id, merchItem.price);
                merchStockManager.ChangeItemStock(merchItem.id, quantity);

                SharedPreferences prefs = getSharedPreferences("MERCH_STOCK", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                try {
                    editor.putString("conStockManager", ObjectSerializer.serialize(merchStockManager));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                editor.commit();

                RefreshMerchList();
            }
        }
    }
}
