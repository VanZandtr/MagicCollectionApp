package com.example.rlv220.mtg_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class WebDownloadTask  extends AsyncTask<String, Void, Bitmap>{
    String result = "";

    @Override
    protected Bitmap doInBackground(String... strings) {


        try {
            //create a URL object
            URL url = new URL(strings[0]);
            //create a HTTP connection object
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //imputstream to read the data
            InputStream stream = urlConnection.getInputStream();
            //read data from input stream
            InputStreamReader streamReader = new InputStreamReader(stream);

            //for images bitmap object
            Bitmap img = BitmapFactory.decodeStream(stream);

            return img;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
