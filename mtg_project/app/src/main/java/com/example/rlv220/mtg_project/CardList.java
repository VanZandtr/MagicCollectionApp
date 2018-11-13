package com.example.rlv220.mtg_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardList extends AppCompatActivity implements Serializable {
    public static ArrayList<JSONObject> cardList;
    public static JSONObject obj;
    public static ArrayList<String> collectionList = new ArrayList<String>();
    public static ArrayList<String> removedCollectionList = new ArrayList<String>();

    String name = "";
    String expansion = "";
    String format = "";
    String colors = "";
    String types = "";
    String cmc = "";
    String power = "";
    String toughness = "";
    String artist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        FloatingActionButton addToDeck = findViewById(R.id.fab4);
        addToDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Adding cards to deck: ","" + collectionList.toString());
                Intent intent = new Intent(CardList.this, DeckList.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("ADD", collectionList);
                bundle.putStringArrayList("REMOVE", removedCollectionList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("List of Cards Added: ","" + collectionList.toString());
                Intent intent = new Intent(CardList.this, MyCollectionList.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("ADD", collectionList);
                bundle.putStringArrayList("REMOVE", removedCollectionList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Log.d("CardList", "In Card List");

        Intent intent = getIntent();
        if(intent.hasExtra("name")) {
            name = intent.getStringExtra("name");
        }
        if(intent.hasExtra("expansion")) {
            expansion= intent.getStringExtra("expansion");
        }
        if(intent.hasExtra("format")) {
            format= intent.getStringExtra("format");
        }
        if(intent.hasExtra("colors")) {
            colors= intent.getStringExtra("colors");
        }
        if(intent.hasExtra("types")) {
            types= intent.getStringExtra("types");
        }
        if(intent.hasExtra("cmc")) {
            cmc= intent.getStringExtra("cmc");
        }
        if(intent.hasExtra("power")) {
            power= intent.getStringExtra("power");
        }
        if(intent.hasExtra("toughness")) {
            toughness= intent.getStringExtra("toughness");
        }
        if(intent.hasExtra("artist")) {
            artist= intent.getStringExtra("artist");
        }

            Cards cards = new Cards();
            try {
                if(intent.hasExtra("obj")){
                    //do nothing - only loads json once
                }
                else {
                    obj = new JSONObject(cards.loadJSONFromAsset(this));
                }
                if(intent.getExtras() != null || cardList == null) {
                    cardList = cards.getAllCardsWithField(obj, name, expansion, format, colors, types, cmc, power, toughness, artist);
                }

                } catch (Exception e) {
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
