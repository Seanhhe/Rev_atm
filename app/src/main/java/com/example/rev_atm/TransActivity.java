package com.example.rev_atm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient(); // ch 11-2-2(P288)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
//        new TransTask().execute("https://atm201605.appspot.com/h"); // 在ch 11-2-2 被OkHttp取代

        // ch 11-2-2(P288) 在onCreate方法中實作連線程式碼
        /* 用Request.Builder設定一個連線必要的資訊，
         * 如網址則使用url()方法定義，完成後再呼叫build方法即產生
         * HTTP的請求(Request)，此時還未連線至主機。
         */
        //
        Request request = new Request.Builder()
                .url("https://atm201605.appspot.com/h")
                .build();
        // 使用OkHttpClient的newCall方法建立一個呼叫物件，此時仍尚未連線至主機。
        Call call = client.newCall(request);

        /* 呼叫 Call 類別的 enqueue 方法，進行排程連線，此時才連線至主機，
         * 並在方法中準備一個回報的物件，當連線至主機的工作完成後依照成功與否
         * 自動呼叫 onResponse (成功回應) 或是 onFailure (失敗)方法。
         */
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string(); // 取得伺服器的回應字串
                Log.d("OKHTTP", json); // 輸出訊息

                /* 解析JSON
                 * 執行自行定義的 parseJSON方法，傳入連線後伺服器的回應資料
                 */
                // parseJSON(json);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // 告知使用者連線失敗
                Log.d("onFailure", "onFailure");
                //e.printStackTrace(); // 連線成功居然會把連線失敗的printStackTrace()也執行。P.289
            }
        });

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