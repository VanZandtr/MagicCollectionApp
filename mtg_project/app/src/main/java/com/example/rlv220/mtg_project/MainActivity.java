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
                if(name.getText().toString().isEmpty() || name.getText().toString() != "") {
                    intent.putExtra("name", name.getText().toString());
                }
                if(expansion.getText().toString().isEmpty() || expansion.getText().toString() != "") {
                    intent.putExtra("expansion", expansion.getText().toString());
                }
                if(format.getText().toString().isEmpty() || format.getText().toString() != "") {
                    intent.putExtra("format", format.getText().toString());
                }
                if(colors.getText().toString().isEmpty() || colors.getText().toString() != "") {
                    intent.putExtra("colors", colors.getText().toString());
                }
                if(types.getText().toString().isEmpty() || types.getText().toString() != "") {
                    intent.putExtra("types", types.getText().toString());
                }
                if(cmc.getText().toString().isEmpty() || cmc.getText().toString() != "") {
                    intent.putExtra("cmc", cmc.getText().toString());
                }
                if(power.getText().toString().isEmpty() || power.getText().toString() != "") {
                    intent.putExtra("power", power.getText().toString());
                }
                if(toughness.getText().toString().isEmpty() || toughness.getText().toString() != "") {
                    intent.putExtra("toughness", toughness.getText().toString());
                }
                if(artist.getText().toString().isEmpty() || artist.getText().toString() != "") {
                    intent.putExtra("artist", artist.getText().toString());
                }
                if(obj != null){
                    intent.putExtra("obj", true);
                }
                startActivity(intent);
            }
        });

    }
}
