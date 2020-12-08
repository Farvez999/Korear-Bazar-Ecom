package com.frz.korearbazar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.frz.korearbazar.R;

public class CartActivity extends AppCompatActivity {

    WebView buyNowWbViw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        buyNowWbViw = findViewById(R.id.buyNowWbViw);

    }
}