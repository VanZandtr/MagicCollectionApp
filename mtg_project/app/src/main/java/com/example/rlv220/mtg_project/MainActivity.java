package com.example.rlv220.mtg_project;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.rlv220.mtg_project.CardList.obj;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText expansion;
    EditText format;
    EditText colors;
    EditText types;
    EditText cmc;
    EditText power;
    EditText toughness;
    EditText artist;
    Boolean test = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.Name);
        expansion = findViewById(R.id.Expansion);
        format = findViewById(R.id.Format);
        colors = findViewById(R.id.Colors);
        types = findViewById(R.id.Types);
        cmc = findViewById(R.id.CMC);
        power = findViewById(R.id.Power);
        toughness = findViewById(R.id.Toughness);
        artist = findViewById(R.id.Artist);



        Button search = findViewById(R.id.button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardList.class);
                if(!name.getText().toString().isEmpty()) {
                    intent.putExtra("name", name.getText().toString());
                    Log.d("naem", "True");
                }
                if(!expansion.getText().toString().isEmpty()) {
                    intent.putExtra("expansion", expansion.getText().toString());
                    Log.d("exp", "True");

                }
                if(!format.getText().toString().isEmpty()) {
                    intent.putExtra("format", format.getText().toString());
                    Log.d("Format", "True");

                }
                if(!colors.getText().toString().isEmpty()) {
                    intent.putExtra("colors", colors.getText().toString());
                    Log.d("Colors", "True");

                }
                if(!types.getText().toString().isEmpty()) {
                    intent.putExtra("types", types.getText().toString());
                    Log.d("Types", "True");

                }
                if(!cmc.getText().toString().isEmpty()) {
                    intent.putExtra("cmc", cmc.getText().toString());
                    Log.d("cmc", "True");
                }
                if(!power.getText().toString().isEmpty()) {
                    intent.putExtra("power", power.getText().toString());
                    Log.d("power", "True");
                }
                if(!toughness.getText().toString().isEmpty()) {
                    intent.putExtra("toughness", toughness.getText().toString());
                    Log.d("tough", "True");
                }
                if(!artist.getText().toString().isEmpty()) {
                    intent.putExtra("artist", artist.getText().toString());
                    Log.d("artist", "True");
                }
                if(obj != null){
                    intent.putExtra("obj", true);
                    Log.d("obj", "True");
                }
                startActivity(intent);
            }
        });

        if(test == true){
            name.setText("Snapcaster Mage");
            search.performClick();
        }

    }
    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
        Toast.makeText(this, "Version 1.0. All search are OR operations. Searches may take a while to load. It is recommended to run search by name or via a desktop", Toast.LENGTH_LONG);
    }
}
