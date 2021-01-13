package com.example.rev_atm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
                parseJSON(json);
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
    }
    // 為了解析JSON資料而自行設計的方法
    /* ch 11-3-2 使用JSON.org解析 (P.291)
     * 基於回傳的字串，以JSONArray建立物件 array，再使用迴圈取出陣列中
     * 的JSON物件(使用JSONObject類別)，再利用JSONObject提供的方法取出
     * 每一筆交易內的四項資料，依這四項資料產生Java 的 Transaction物件
     * ，再將物件加入到 ArrayList 集合物件 trans 中。
     */
    private void parseJSON(String s) {
        ArrayList<Transaction> trans = new ArrayList(); // 準備ArrayList物件 trans，裡頭只能放Transaction物件
        try {
            JSONArray array = new JSONArray(s); // 將傳入的字串s 交給JSONArray的建構式，產生array物件
            for (int i = 0; i < array.length(); i++) {  // 以迴圈依序取出交易紀錄
                JSONObject obj = array.getJSONObject(i);  // 以索引值取得JSONObject物件 obj
                String account = obj.getString("account"); // 呼叫JSONObject類的getXXX方法取得各個屬性值
                String date = obj.getString("date");
                int amount = obj.getInt("amount");
                int type = obj.getInt("type");
                Log.d("JSON : ", account + "/" + date + "/" + amount + "/" + type);
                Transaction t = new Transaction(account, date, amount, type); // 產生 Transaction 物件，代表一筆交易紀錄
                trans.add(t); // 將t物件加入到集合物件中
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}