package com.example.rev_atm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncQueryHandler;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 填入已儲存帳號 (入門這樣學 P.168) - 需先有儲存登入帳號後才有效果
        /* 當LoginActivity開啟前，讀取儲存設定檔中的 PREF_USERID 鍵值，
         * 並將該值設定至帳號的輸入方塊中，請在 LoginActivity 的 onCreate方法
         * 加入以下程式碼。
         */
        EditText edUserid = findViewById(R.id.ed_userid);
        SharedPreferences setting = getSharedPreferences("atm", MODE_PRIVATE);
        edUserid.setText(setting.getString("PREF_USERID", ""));
    }

    public void login(View v){
        // 驗證登入功能，假裝有一ID:jack，PW:a1234
        EditText edUserid = findViewById(R.id.ed_userid);
        EditText edPassword = findViewById(R.id.ed_password);
        String uid = edUserid.getText().toString();
        String pw = edPassword.getText().toString();

        // 以下修改來自 ch 10-7-5 (p.275)，把if...else註解掉
        String url = new StringBuilder("https://atm201605.appspot.com/login?uid=")
                .append(uid)
                .append("&pw=")
                .append(pw)
                .toString();
        new LoginTask().execute(url); // 啟動LoginTask非同步工作並提供網址

//        if (uid.equals("jack") && pw.equals("a1234")){  // 登入成功
////            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
////            // 在功能Activity畫面結束之前，設定返回值 (Android入門這樣學 142頁)
////            getIntent().putExtra("LOGIN_USERID", uid);
////            getIntent().putExtra("LOGIN_PASSWORD", pw);
////            setResult(RESULT_OK, getIntent());  // 設定此功能畫面的結果為RESULT_OK
////            //  RESULT_OK = -1; RESULT_CANCELED = 0
////            //  RESULT_FIRST_USER = 1
////
////            // 儲存登入帳號 (Android入門這樣學 P.168)
////            SharedPreferences setting = getSharedPreferences("atm", MODE_PRIVATE);
////            setting.edit()
////                   .putString("PREF_USERID", uid)
////                   .apply(); // 最後apply方法，實際進行儲存資料，名為PREF_USERID。
////            finish();
////            /*  finish()只針對activity，當呼叫finish()時，
////             *  只是將 acitvity 拉至background，並無立即釋放
////             *  該 activity 佔用的 Memory，所以 Activity 的
////             *  資源並沒有被清理。
////             *
////             *  當調用 System.exit(0)時，整個 process 會被 kill，
////             *  這時候 Activity 所佔用的資源也會被釋放。
////             */
////        } else {    //  登入失敗
////            new AlertDialog.Builder(this)
////                    .setTitle("Atm")
////                    .setMessage("登入失敗")
////                    .setPositiveButton("OK", null)
////                    .show();
////        }
    }

    public void cancel(View v){
        // 暫時空空
    }

    /*  ch10-7-3 設計網路連線登入的內部類別 (執行緒) P.273
     *  在LoginActivity中新增一個內部類別，名稱為"LoginTask"，繼承
     *  Runnable或Callable，引入AsyncTask參數為(String, Void, Boolean)
     *  ，請記得實作必要的 doInBackground 方法
     */

    //  傳入型態，打算傳入網址，所以使用String。 P.274
    //  更新資料型態，因連線時間不長不需回傳資料，所以Void。
    //  回傳值型態，判斷網頁回應是1或0，回傳是否登入成功的布林值。
    //  因連線完成後仍需設計給使用者的回應，因此覆寫 onPostExecute方法
    class LoginTask extends AsyncTask<String, Void, Boolean> {
        boolean logon;

        @Override
        protected Boolean doInBackground(String... Strings) {
            logon = false;
            try {
                URL url = new URL(Strings[0]);
                //  openStream()是縮短的語法，原始=> openConnection().getInputStream()
                InputStream is = url.openStream();
                int data = is.read();
                Log.d("HTTP", String.valueOf(data));
                if (data == 49) {   // 代表登入成功，因1的 unicode值為49
                    logon = true;
                }
                is.close(); // 關閉連線。
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return logon;   // 回傳登入值
        }

        /*  在 doInBackground 方法中的回傳值在完成後，自動執行
         *  onPostExecute 方法並成為傳入的參數，接著在以下實作
         *  登入成功與失敗後，各自對應的程式碼
         */
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(logon);
            if (aBoolean) { // 登入成功
                //  儲存帳號至 SharedPreferences (省略待補)
                //  結束本畫面，回到MainActivity
                Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, getIntent());
                finish();
            } else {    // 登入失敗
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("ATM")
                        .setMessage("登入失敗")
                        .setPositiveButton("OK", null)
                        .show();
            }

            // 以下先接 ch 10-7-5 修改login方法 p.275
            /* 修改按下登入按鈕所執行的login方法，因為登入判斷已改由
             * LoginTask 類別實作，請先註解if...else判斷式的所有code
             * ，產生對應的連結字串後(url變數)，再產生LoginTask實例
             * 並直接執行 execute 方法啟動耗時工作的執行緒
             */


        }
    }

}