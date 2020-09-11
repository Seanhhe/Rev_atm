package com.example.rev_atm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FinanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        // ch9-6-1 使用 simpleCursorAdapter (P232)
        ListView list = findViewById(R.id.list); // 先取得 FinanceActivity 中的 ListView (id為 list)
//      MyDBHelper helper = new MyDBHelper(this, "expense.db", null, 1); // 產生設計的MyDBHelper物件，並查詢exp表格

        // ch 9-6-3 SQLiteOpenHelper 改為 Singleton 設計 (P241)
        MyDBHelper helper = MyDBHelper.getInstance(this);

        Cursor c = helper.getReadableDatabase().query("exp", null, null, null, null, null, null);
        /*
         先呼叫 MyDBHelper 建構式得到物件再查詢，最後得到 Cursor 物件。
            在此須小心在不同類別 (如 Activity) 中建構 SQLiteOpenHelper
            物件的問題，應在 MyDBHelper 類別設計中採用單一物件的設計模式
            (Singleton)，在第9章最後將說明重構及設計方式。
         */

        // 最後產生 simpleCursorAdapter 物件
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
//                android.R.layout.simple_expandable_list_item_2,
//                c,
//                new String[] {"info", "amount"},
//                new int[] {android.R.id.text1, android.R.id.text2},
//                0);

        // ch9-6-2 更換 SimpleCursorAdapter 建構程式碼 (P239)
        /* 回到FinanceActivity的 onCreate 方法中，將上頭原本使用兩欄位的程式碼
           更改如下
           若未來有其他字型、顏色、間距等設計需求，直接修改 finance_row.xml 即可得到效果。
         */
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.finance_row,
                c,
                new String[] {"cdate", "info", "amount"},
                new int[] {R.id.item_cdate, R.id.item_info, R.id.item_amount},
                0);

        /* 建構式參數說明
        1) Context參數，正確寫法為 FinanceActivity.java 類別，若是自己可簡寫 this。
        2) 依 android.R.layout.simple_expandable_list_item_2 定義的layout格式，
        3) c 為查詢資料庫得到的Cursor
        4) new String[] {"info", "amount"} 陣列為要顯示的欄位，
           必須要對應 c 查詢時所定義的欄位名稱。
        5) new int[] {android.R.id.text1, android.R.id.text2}
           代表 4) 的欄位內容要擺放的物件為何，也就是 layout 裡面放的物件。
         */
        list.setAdapter(adapter); // 將adapter設定給list清單元件

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(FinanceActivity.this, AddActivity.class)); // ch9-1-3
            }
        });

    }
}