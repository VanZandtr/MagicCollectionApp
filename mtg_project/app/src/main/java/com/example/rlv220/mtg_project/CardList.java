package com.example.rlv220.mtg_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardList extends AppCompatActivity {
    ArrayList<JSONObject> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);


        Intent intent = getIntent();
        String field = intent.getStringExtra("field");
        String tag = intent.getStringExtra("tag");

        Cards cards = new Cards();
        try {
            JSONObject obj = new JSONObject(cards.loadJSONFromAsset(this));
            cardList = cards.getCardAllCardsWithField(obj, field, tag);
        }catch(Exception e){
            e.printStackTrace();
        }


        RecyclerView rView = findViewById(R.id.theView);
        //create layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(layoutManager);

        //create the adapter
        MyAdapter adapter = new MyAdapter(cardList);
        //attach the adapter
        rView.setAdapter(adapter);
    }
}
