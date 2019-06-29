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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SetManager extends AppCompatActivity {
    public static final int CREATE_SET_RESULT = 1;
    public static final int EDIT_SET_RESULT = 2;

    MerchStockManager merchStockManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_manager);
        Intent intent = getIntent();

        if (intent.hasExtra("stockManager")) {
            merchStockManager = (MerchStockManager)intent.getSerializableExtra("stockManager");
        }
        if (merchStockManager == null) merchStockManager = new MerchStockManager();

        PopulateWithSets();
    }

    public void PopulateWithSets() {
        LinearLayout lst = findViewById(R.id.set_list);
        HashMap<Integer, MerchSet> setMap = SortHashMap(merchStockManager.setDiscounts);
        for (Map.Entry<Integer, MerchSet> entry : setMap.entrySet()) {
            SetSelector tmp = new SetSelector(getApplicationContext());
            tmp.Initialize(entry.getValue(), this);
            lst.addView(tmp);
        }
    }

    public void RefreshSetList() {
        LinearLayout lst = findViewById(R.id.set_list);
        lst.removeAllViews();
        PopulateWithSets();
    }

    public void CreateSetButtonPressed(View v) {
        Intent intent = new Intent(this, CreateSet.class);
        startActivityForResult(intent, CREATE_SET_RESULT);
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
            case (CREATE_SET_RESULT) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle dataBundle = data.getExtras();
                    String name = dataBundle.getString("name");
                    String type = dataBundle.getString("type");
                    float discount = dataBundle.getFloat("discount");

                    MerchSet set = new MerchSet();
                    set.name = name;
                    set.type = type;
                    set.discount = discount;
                    set.id = merchStockManager.GenerateSetId();
                    merchStockManager.AddSet(set);

                    SaveStockManager();
                    RefreshSetList();
                }
                break;
            }
            case (EDIT_SET_RESULT) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle dataBundle = data.getExtras();
                    MerchSet merchSet = (MerchSet) dataBundle.getSerializable("merchSet");

                    merchStockManager.EditSet(merchSet);

                    SaveStockManager();
                    RefreshSetList();
                }
            }
        }
    }

    public HashMap<Integer, MerchSet> SortHashMap(HashMap<Integer, MerchSet> map) {
        List<Map.Entry<Integer, MerchSet>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, MerchSet>>() {
            @Override
            public int compare(Map.Entry<Integer, MerchSet> o1, Map.Entry<Integer, MerchSet> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        HashMap<Integer, MerchSet> tmp = new LinkedHashMap<>();
        for (Map.Entry<Integer, MerchSet> item : list) {
            tmp.put(item.getKey(), item.getValue());
        }
        return tmp;
    }
}
