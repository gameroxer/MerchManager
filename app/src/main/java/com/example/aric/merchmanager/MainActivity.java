package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<MerchItem> merchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        merchList = new ArrayList<>();
        CreateSampleItems();
    }

    public void MakeSaleButtonPressed(View v) {
        Intent intent = new Intent(this, MerchSale.class);
        intent.putExtra("merchList", merchList);
        startActivity(intent);
    }

    void CreateSampleItems() {
        MerchItem item1 = new MerchItem();
        item1.id = 1;
        item1.name = "Sample Item 1";
        item1.price = 3;
        MerchItem item2 = new MerchItem();
        item2.id = 2;
        item2.name = "Sample Item 2";
        item2.price = 5;
        MerchItem item3 = new MerchItem();
        item3.id = 3;
        item3.name = "Sample Item 3";
        item3.price = 1;
        merchList.add(item1);
        merchList.add(item2);
        merchList.add(item3);
    }
}
