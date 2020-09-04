package com.example.rev_atm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static java.lang.Integer.parseInt;

public class AddActivity extends AppCompatActivity {
    // ch 9-1-3

    private EditText edDate, edInfo, edAmount;
    // ch 9-2-5 使用SQLiteOpenHelper
    /*
    完成 MyDBHelper 類別設計後，至 AddActivity，準備設計新增消費紀錄功能
    在 onCreate 方法，呼叫MyDBHelper的建構式建立名為"helper"物件，
    並將 helper 定義為屬性。
     */
    private MyDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();
        /*
        MyDBHelper建構式與SQLiteOpenHelper相同，參數用法如下：
        Context context > 使用this關鍵字，即代表傳入 AddActivity 本身
        String name > name為自訂的資料庫檔案名稱，可以用副檔名。
        CursorFactory factory > 在此用null，代表以標準模式SQLiteCursor處理Cursor。
        int version > 此應用程式目前的資料庫版本，在此用數字1代表第一版。
         */
        helper = new MyDBHelper(this, "expense.db", null, 1);
    }

    // 按鈕功能：新增
    public void add(View view) {
        /* ch 9-3 新增資料表資料 (P224)
        用 SQLiteDatabase 的 insert 方法新增資料至表格，
        第一個參數為表格名稱，
        第三個是"資料包"，使用 ContentValues 類別，就像是java的 Map 集合類別，
        專門儲存 Key-Value 的一組對應資料組，其中 Key 鍵值使用欄位的名稱，
        Value 則是該欄位的值。
            請在 AddActivity 的 add 方法中實作，將一筆消費紀錄儲存在一個
        ContentValues 物件中，最後再呼叫 SQLiteDatabase 的 insert 方法
        新增紀錄。
         */
        String cdate = edDate.getText().toString();
        String info = edInfo.getText().toString();
        int amount = Integer.parseInt(edAmount.getText().toString());
        ContentValues values = new ContentValues();
        values.put("cdate", cdate);
        values.put("info", info);
        values.put("amount", amount);
        // 取得 SQLiteDatabase 資料庫物件後呼叫 insert 方法，傳入表格名稱與
        // values 集合物件以新增這筆紀錄，若成功會回傳新增紀錄的 id 值，
        // 最後使用Log印出除錯資訊。
        long id = helper.getWritableDatabase()
                .insert("exp", null, values);
        /*
        insert方法的 nullColumnHack  (P225)
            假如資料表格中的所有欄位允許空值時，一般的SQL語法可使用
            "INSERT INTO 資料表名稱"新增一筆欄位全是空值得記錄。
            但 SQLite 不可用這樣的語法，因此選擇一個欄位給予空值。
            insert 方法中的第二個參數可填入一個欄位名稱，當第三個參數
            values 內無任何資料時，會在該欄位上給予空值。
            此情況不多見，可直接給予第二個參數null即可。
         */
        Log.d("ADD", id + "");
    }

    private void findViews() {
        edDate = findViewById(R.id.ed_date);
        edInfo = findViewById(R.id.ed_info);
        edAmount = findViewById(R.id.ed_amount);
    }
}