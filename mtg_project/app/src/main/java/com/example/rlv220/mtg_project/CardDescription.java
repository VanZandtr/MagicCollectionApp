package com.example.rlv220.mtg_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CardDescription extends AppCompatActivity {
    ProgressDialog pd;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_description);

        JSONObject object = CardList.obj;

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String responseJSON;
        ImageView imageView;

        Cards cards = new Cards();
        try {
            JSONObject card = cards.getCard(object, name);
            Log.d("Card", "" + card);
            textView = findViewById(R.id.test);
            textView.setText("" + card.getString("name"));

            try {
                //TODO -- replace spaces with -
                responseJSON = "https://api.scryfall.com/cards/named?fuzzy=" + name;
                new JsonTask().execute(responseJSON);
                //String imgURL = currCard.getString("scryfall_uri");
                //imageView = findViewById(R.id.imageView);
                //imageView.setImageBitmap(getImg(imgURL));
            } catch (Exception exc) {
                exc.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImg(String text) {
        //create task object
        WebDownloadTask task = new WebDownloadTask();
        Bitmap res = null;
        //execute the task and store result in res
        try {
            res = task.execute(text).get();
            //Log.i("HTML CODE", res);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //iv.setImageBitmap(res);
        return res;
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(CardDescription.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            textView.setText(result);
        }
    }
}


