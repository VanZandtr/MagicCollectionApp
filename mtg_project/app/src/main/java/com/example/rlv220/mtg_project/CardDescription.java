package com.example.rlv220.mtg_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
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
    TextView cardPricePaper;
    TextView cardPriceOnline;
    ImageView imageView;
    Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_description);
        JSONObject object = CardList.obj;
        back_button = findViewById(R.id.back_button);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String responseJSON;

        Cards cards = new Cards();
        try {
            JSONObject card = cards.getCard(object, name);
            Log.d("Card", "" + card);

            if(card.has("rulings")){
                textView = findViewById(R.id.test);
                JSONArray rulings = card.getJSONArray("rulings");
                for(int i = 0; i < rulings.length(); i ++) {
                    String rulesDate = rulings.getJSONObject(i).getString("date");
                    textView.append("" + rulesDate + ": ");
                    String rulesText = rulings.getJSONObject(i).getString("text");
                    textView.append("" + rulesText + "\n");

                }
                textView.setMovementMethod(new ScrollingMovementMethod());

            }

            try {
                Log.d("name", "" + name);
                name = name.replace(" ", "-");
                Log.d("name", "" + name);
                responseJSON = "https://api.scryfall.com/cards/named?fuzzy=" + name;
                new JsonTask().execute(responseJSON);
            } catch (Exception exc) {
                exc.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardDescription.this, CardList.class);
                startActivity(intent);
            }
        });
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
                    Log.d("Response: ", "> " + line);

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
            try {
                Log.d("result", "" + result);
                JSONObject card = new JSONObject(result);
                Log.d("card", "" + card);
                JSONObject cardImages = new JSONObject(card.getString("image_uris"));
                Log.d("cardImages", "" + cardImages);
                String imgURL = cardImages.getString("large");
                Log.d("imgUrl", "" + imgURL);
                imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap(getImg(imgURL));

                //TODO --- IF STATMENTS FOR PRICE
                cardPriceOnline = findViewById(R.id.online_price);
                cardPricePaper = findViewById(R.id.paper_price);

                cardPriceOnline.setText("Online(tix): " + card.get("tix").toString());
                cardPricePaper.setText("Paper(USD): " + card.get("usd").toString());
                //JSONObject buy_urls = new JSONObject(card.getString("purchase_uris")); -- //"purchase_uris":{"amazon":"https://www.amazon.com/gp/search?ie=UTF8\u0026index=toys-and-games\u0026keywords=Spellstutter+Sprite\u0026tag=scryfall-20","ebay":"http://rover.ebay.com/rover/1/711-53200-19255-0/1?campid=5337966903\u0026icep_catId=19107\u0026icep_ff3=10\u0026icep_sortBy=12\u0026icep_uq=Spellstutter+Sprite\u0026icep_vectorid=229466\u0026ipn=psmain\u0026kw=lg\u0026kwid=902099\u0026mtid=824\u0026pub=5575230669\u0026toolid=10001","tcgplayer":"https://scryfall.com/s/tcgplayer/68335","magiccardmarket":"https://scryfall.com/s/mcm/262030","cardhoarder":"https://www.cardhoarder.com/cards/48678?affiliate_id=scryfall\u0026ref=card-profile\u0026utm_campaign=affiliate\u0026utm_medium=card\u0026utm_source=scryfall","card_kingdom":"https://www.cardkingdom.com/catalog/item/190088?partner=scryfall\u0026utm_campaign=affiliate\u0026utm_medium=scryfall\u0026utm_source=scryfall","mtgo_traders":"http://www.mtgotraders.com/deck/ref.php?id=48678\u0026referral=scryfall","coolstuffinc":"https://scryfall.com/s/coolstuffinc/2331666"}}

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}


