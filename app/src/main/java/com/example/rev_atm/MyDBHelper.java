package com.example.rev_atm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// ch 9-2-1 建立應用程式的 SQLiteOpenHelper 類別
/* 在左側專案 app/java/com.example.rev_atm 點右鍵，New/Java Class
 * 名稱輸入 MyDBHelper，種類：class，然後按 Enter。
 * 在 public class MyDBHelper 後面輸入 extends SQLiteOpenHelper。
 *
 * 實作方法：
 * 因為 SQLiteOpenHelper 是抽象類別，其子類別需實作必要的方法。
 * 把游標停在紅線處，按下"Alt+Enter"後選擇"Implement methods" (或功能表/Code/Implement Methods)
 * 選擇OnCreate與onUpdate兩個方法，按下 Enter 即可。
 *
 * 實作建構式：
 * 因父類別 SQLiteOpenHelper 中並無實作參數的建構式，所以 MyDBHelper
 * 必須設計建構式，可將游標停在出現錯誤的類別定義的那一行，按下"Alt+Enter"
 * 選擇 "Create constructor matching super class"，建立與父類別相同參數
 * 的建構式。再選取第一個即可。
 *
 */
public class MyDBHelper extends SQLiteOpenHelper {
    // 建構 helper 物件以創立、開啟，或管理資料庫。
    // 資料庫只在接收 getWritableDatabase() 或 getReadableDatabase()
    // 指令才會實際執行創立或開啟DB。
    public MyDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // ch 9-2-3 建立資料表格的時機 onCreate
        /* 當應用程式中執行到有關任何存取資料庫的指令時，假如資料庫檔案不存在，
         * 就會立刻執行onCreate方法。因此，在這撰寫建立資料庫表格的程式碼，
         * 若表格中需要初始資料時，在建立表格完成後可繼續撰寫新增資料紀錄的
         * 程式碼。
         * 在範例中每一筆消費紀錄的欄位有：
         * _id (INTEGER、Primary Key)
         * cdate (DATETIME、Not Null)
         * info [VARCHAR(20)]
         * amount (INTEGER)
         * SQLite語法：CREATE TABLE exp (
         * _id INTEGER PRIMARY KEY NOT NULL,
         * cdate DATETIME NOT NULL, info VARCHAR, amount INTEGER);
         *
         * 在 onCreate 方法中，使用該方法的db物件(SQLiteDatabase)中的
         * execSQL 方法建立資料表 exp 表格如下：
         */
        db.execSQL("CREATE TABLE exp (_id INTEGER PRIMARY KEY NOT NULL, cdate DATETIME NOT NULL, INFO VARCHAR(20), amount INTEGER)");
        // 完成 MyDBHelper 類別設計後，至 AddActivity，準備設計新增消費紀錄功能
    }

    @Override // i代表oldVersion、i1代表newVersion
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

}
