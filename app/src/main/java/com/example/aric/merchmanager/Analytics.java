package com.example.aric.merchmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import java.util.ArrayList;

public class Analytics extends AppCompatActivity {

    ArrayList<MerchTransaction> transactions;

    ArrayList<Pair<MerchItem, Integer>> itemTotalQuantity;

    TextView totalEarned;
    TextView mostPopular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        Intent intent = getIntent();
        transactions = (ArrayList<MerchTransaction>) intent.getSerializableExtra("transactionList");

        itemTotalQuantity = new ArrayList<>();

        float total = 0;
        for (MerchTransaction transaction : transactions) {
            transaction.CalculateTotalPrice();
            total += transaction.totalPrice;

            for (MerchTransactionItem item : transaction.itemList) {
                AddItemToTotalQuantityList(item.item, item.amount);
            }
        }

        totalEarned = findViewById(R.id.totalEarned);
        totalEarned.setText(String.format("$%.2f", total));

        mostPopular = findViewById(R.id.mostPopularList);
        mostPopular.setText(GetMostPopularString());
    }

    public String GetMostPopularString() {
        String retString = "";
        if (itemTotalQuantity.size() == 0) return retString;

        ArrayList<Pair<MerchItem, Integer>> mostPopularList = GetMostPopular();
        for (Pair<MerchItem, Integer> item : mostPopularList) {
            retString += item.first.name + ":  " + item.second + "x\n";
        }
        retString.trim();
        return retString;
    }

    private void AddItemToTotalQuantityList(MerchItem item, int quantity) {
        boolean found = false;
        for (int i = 0; i < itemTotalQuantity.size(); i++) {
            Pair<MerchItem, Integer> entry = itemTotalQuantity.get(i);
            if (entry.first.id == item.id) {
                Pair<MerchItem, Integer> newPair = new Pair<>(entry.first, entry.second + quantity);
                itemTotalQuantity.set(i, newPair);
                found = true;
            }
        }
        if (!found) {
            itemTotalQuantity.add(new Pair<>(item, quantity));
        }
    }

    private ArrayList<Pair<MerchItem, Integer>> GetMostPopular() {
        ArrayList<Pair<MerchItem, Integer>> retList = new ArrayList<>();

        Pair<MerchItem, Integer> max = itemTotalQuantity.get(0);
        int iterations = Math.max(itemTotalQuantity.size(), 5);

        for (int i = 0; i < iterations; i++) {
            for (Pair<MerchItem, Integer> pair : itemTotalQuantity) {
                if (!DoesListContainMerch(retList, pair.first) && pair.second > max.second) max = pair;
            }
            retList.add(max);
            max = GetFirstNotExists(retList);
            if (max == null) break;
        }
        return retList;
    }

    private Pair<MerchItem, Integer> GetFirstNotExists(ArrayList<Pair<MerchItem, Integer>> existList) {
        for (Pair<MerchItem, Integer> pair : itemTotalQuantity) {
            if (!DoesListContainMerch(existList, pair.first)) return pair;
        }
        return null;
    }

    private boolean DoesListContainMerch(ArrayList<Pair<MerchItem, Integer>> list, MerchItem item) {
        for (Pair<MerchItem, Integer> pair : list) {
            if (pair.first.id == item.id) return true;
        }
        return false;
    }
}
