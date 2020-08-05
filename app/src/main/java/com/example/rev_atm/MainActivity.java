package com.example.rev_atm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final int RC_LOGIN = 1;   // 宣告此常數代表登入功能
    boolean logon = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!logon) {   // 若未登入，則開啟LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            //startActivity(intent);  //  正確適當應使用->startActivityForResult()
            startActivityForResult(intent, RC_LOGIN); // 第二參數是requestCode
            // 參考 Android 入門這樣學 PDF 第141頁
        }
    }
    //  從功能畫面回到主畫面的 onActivityResult 方法 (功能表Code/Override Methods)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_LOGIN) {
            if (resultCode == RESULT_OK) {
                // 取得回傳 intent 中的資料
                String uid = data.getStringExtra("LOGIN_USERID");
                String pw = data.getStringExtra("LOGIN_PASSWORD");
                Log.d("RESULT", uid + "/" + pw);
            } else {
                finish();
            }
        }
    }
}