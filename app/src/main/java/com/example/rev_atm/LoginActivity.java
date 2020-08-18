package com.example.rev_atm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        if (uid.equals("jack") && pw.equals("a1234")){  // 登入成功
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
            // 在功能Activity畫面結束之前，設定返回值 (Android入門這樣學 142頁)
            getIntent().putExtra("LOGIN_USERID", uid);
            getIntent().putExtra("LOGIN_PASSWORD", pw);
            setResult(RESULT_OK, getIntent());  // 設定此功能畫面的結果為RESULT_OK
            //  RESULT_OK = -1; RESULT_CANCELED = 0
            //  RESULT_FIRST_USER = 1

            // 儲存登入帳號 (Android入門這樣學 P.168)
            SharedPreferences setting = getSharedPreferences("atm", MODE_PRIVATE);
            setting.edit()
                   .putString("PREF_USERID", uid)
                   .apply(); // 最後apply方法，實際進行儲存資料，名為PREF_USERID。
            finish();
            /*  finish()只針對activity，當呼叫finish()時，
             *  只是將 acitvity 拉至background，並無立即釋放
             *  該 activity 佔用的 Memory，所以 Activity 的
             *  資源並沒有被清理。
             *
             *  當調用 System.exit(0)時，整個 process 會被 kill，
             *  這時候 Activity 所佔用的資源也會被釋放。
             */
        } else {    //  登入失敗
            new AlertDialog.Builder(this)
                    .setTitle("Atm")
                    .setMessage("登入失敗")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    public void cancel(View v){
        // 暫時空空
    }
}