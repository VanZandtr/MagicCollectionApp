package com.example.rlv220.mtg_project;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.test);
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this));
            //Log.d("tag", "" + obj.names());
            String[] names = obj.names();
            JSONArray array = obj.names();
            for(int i = 0; i < array.length(); i++){

                JSONObject newobj = obj.getJSONObject(array.getJSONObject(i).toString());
                Log.d("card", "" + newobj);
            }
            String jsonString = obj.toString();
            String[] split = jsonString.split(",");

            //JSONObject card = obj.getJSONObject("Adorable Kitten");
            //String cmc = card.getString("cmc");
            //String test = obj.getString("name");
            //tv.setText("" + split[0]);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("AllCards-x.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
