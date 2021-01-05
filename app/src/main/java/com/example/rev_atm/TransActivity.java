package com.example.rev_atm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class TransActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        new TransTask().execute("https://atm201605.appspot.com/h");
    }

    class TransTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(url.openStream()));
                String line = br.readLine();
                while (line != null){
                    Log.d("HTTPS", line);
                    sb.append(line); // 把讀進來的資料加入stringBuffered物件內
                    line = br.readLine(); // 把讀進來的字串資料再次放入line字串物件
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("JSON", s);
            parseJSON(s);
        }

        // 為了解析JSON資料而自行設計的方法
        private void parseJSON(String s) {
        }
    }
}