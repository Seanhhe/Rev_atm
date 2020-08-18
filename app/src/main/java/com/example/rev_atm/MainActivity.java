package com.example.rev_atm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final int RC_LOGIN = 1;   // 宣告此常數代表登入功能
    boolean logon = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 偏好設定 SharedPreferences
        /*  SharedPreferences 是 Android 的一種介面，可在Activity中
         *  呼叫 getSharedPreferences(String, int) 方法得到物件。
         *
         *  參數一：檔案名稱 String
         *  是定義此設定檔的檔名，不用指定副檔名，如"atm"即寫入到atm.xml
         *
         *  參數二：存取權限 int
         *  設定此app設定檔的存取權限，有四種選擇(0,1,2,4)，不需背數值，
         *  使用 Activity 中已經定義的常數 MODE_XXX 即可。在設計時輸入
         *  大寫 M 後可快速選擇參數。
         *
         *  MODE_PRIVATE 參數代表此app喜好設定值只允許本app內存取。最常用。
         *  例如
         *  getSharedPreferences("atm", MODE_PRIVATE); // 建立物件
         */

        // 寫入偏好設定的資料
        //String user = "jack";
        //SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE); // 建立物件及設定檔
        //pref.edit().putString("USER", user).commit();
        /*  如果要多筆資料寫入設定檔，先put資料，最後再commit()一次寫入
         *  pref.edit()
         *      .putString("USER", user)
         *      .putInt("STAGE", 3)
         *      .putFloot("HP", 98.3)
         *      .commit();
         */

        //  讀取偏好設定檔內的資料
        /*  先取得 SharedPreference物件。
         *  再取得標籤"USER"的設定值。
         *  getString方法第二個參數是預設值(default)，當讀不到
         *  或設定檔內沒有數值時，會傳回此預設值。
         */
        //String userid = getSharedPreferences("test", MODE_PRIVATE)
        //        .getString("USER", "empty");


        // 測試 TestActivity
        //startActivity(new Intent(this, TestActivity.class));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 呼叫 getMenuInflater 方法取得 MenuInflater 物件
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //return super.onCreateOptionsMenu(menu);
        return true; // 回傳true以顯示，回傳false表不顯示
    }
    /* 按下選單項目時的事件處理
     * 當按下選單中的任一個item時，會自動呼叫 Activity 的
     * onOptoinsItemSelected 方法，並傳來被按下的項目 MenuItem物件，
     * 在方法中實作程式碼。
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // 如果有多個選單項目，應把if判斷改成 switch case改寫，
        // 增加程式可讀性與維護性。
        if (id == R.id.action_setting){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}