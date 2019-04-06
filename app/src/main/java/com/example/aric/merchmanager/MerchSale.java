package com.example.aric.merchmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MerchSale extends AppCompatActivity {
    public static String SALE_SUCCESS = "SALE_SUCCESS";
    private MerchTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch_sale);
    }



    public void MakeSale(View v) {
        //add sale to data

        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);

        finish();

    }
}
