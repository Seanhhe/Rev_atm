package com.example.rev_atm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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