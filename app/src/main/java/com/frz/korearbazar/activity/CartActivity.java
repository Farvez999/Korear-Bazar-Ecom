package com.frz.korearbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.frz.korearbazar.Database.CartDB;
import com.frz.korearbazar.R;
import com.frz.korearbazar.adapter.CartAdapter;
import com.frz.korearbazar.model.CartModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView orderRecyclerView;
    Button orderButton;

    CartDB cartDB;
    List<CartModel> cartModelList;

    CartAdapter cartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        orderRecyclerView = findViewById(R.id.orderRecycler);
        orderButton = findViewById(R.id.order);

        cartDB=new CartDB(this);
        cartModelList=new ArrayList<>();
        if (cartDB.getAllData().size()>0){
            cartModelList= cartDB.getAllData();
            cartAdapter =  new CartAdapter(this,cartModelList);

            orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            orderRecyclerView.setAdapter(cartAdapter);

        }
        else {
            Toast.makeText(this, "Cart is Empty", Toast.LENGTH_SHORT).show();
        }


    }
}