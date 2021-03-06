package com.example.rev_atm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final int RC_LOGIN = 1;   // 宣告此常數代表登入功能
    boolean logon = false;

    /* 清單式功能表
     * 先建立 String[]，把要顯示的清單項目放入。
     * 在 Activity 中使用 ListView 元件方式與其他元件相同
     * 先在xml新增、設定邊界、於.java中取得再設定。
     *
     */
    String[] func = {"餘額查詢", "交易明細", "最新消息", "投資理財", "離開"};
    // ch8-5-2 (P201)、ch8-5-4 設計版面配置(layout)檔[]
    int[] icons = {R.drawable.func_balance,
            R.drawable.func_history,
            R.drawable.func_news,
            R.drawable.func_finance,
            R.drawable.func_exit};
    // ch8-5-4 (P202)
    /* 設計新的版面layout檔，代表在GridView中的單一項目樣貌，
     * 上方為 ImageView 元件，下方為 TextView 元件。
     *
     * 點擊專案中的"app"，再到功能表的 File/New/Android resource file，在對話框中：
     * 1) 輸入檔名 item_row.xml
     * 2) Resource type 選"Layout"
     * 3) Root element 使用"LinearLayout"
     * 4) 在此新的版面配置.xml上，上方增加一個 ImageView 元件，id為"item_image"，
     *    下方增加一個 TextView 元件，id為"item_text"
     */
    // ch8-5-5 (P204) 繼承 BaseAdapter 類別
    /* 設計一個名稱為 IconAdapter 並繼承 BaseAdapter，在這類別中
     * 實作父類別的方法，使其能夠以圖示+文字方式顯示主功能畫面的GridView
     *
     * 以內部類別設計：
     *   以內部類別設計 IconAdapter，因為在這類別中會使用到如 Context、icons
     * 與 func 陣列資料，所以將其設計為 MainActivity 的內部類別可簡化許多
     * 繁雜的參數傳遞與建構式設計，再者，這個類別也只會在 MainActivity 中
     * 使用，其他類別並不會用到它。
     *   在 MainActivity 下的第一層級 (與 onCreate 方法同一級)設計一個
     * 新類別 IconAdapter，並繼承 BaseAdapter，因為 BaseAdapter 是抽象
     * 類別，因此必須實作四個方法，請使用 Alt+Enter 自動實作方法如下。
     */
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 程式中取得 ListView (RecycleView 更彈性更好用)
//        ListView list = findViewById(R.id.list);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, func);
//        // 使用 ListView (用 ListView 的 setAdapter 方法)
//        list.setAdapter(adapter);

        // 下拉選單 - Spinner  (分隔線 ch8-3)
//        /* 清單的資料除了在類別中定義之外，比較常用的方式是在專案的
//         * "res/values/" 資料夾下定義字串陣列。
//         * 優點 =>
//         * 方便未來修改資料，不須動到類別，直接改 xml 即可。
//         * 可實作多國語言，可定義不同語系使用的字串陣列。
//         */
//        // 使用 Spinner
//        /* 因為陣列放在 res 中，可使用 ArrayAdapter 的 createFromResource 方法，
//         * 直接產生一個 ArrayAdapter<CharSequence> 物件如下
//         * 參數一：Context，使用 this 即可
//         * 參數二：陣列資源的ID值，使用 R.array.notify_array
//         * 參數三：清單顯示時要使用的版面配置，使用android.R.layout.simple_spinner_item
//         */
//        final Spinner notify = findViewById(R.id.spinner);
//        final ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
//                this, R.array.notify_array, android.R.layout.simple_spinner_item);
//        nAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // 設定較美觀不擠的版面
//        notify.setAdapter(nAdapter);    // 將nAdapter物件設定入Spinner元件
//        // 選擇項目的事件處理Listener
//        /* 可以為 Spinner 元件加入 AdapterView.OnItemSelectedListener，
//         * 字面意思是"當項目被選擇時的Listener"
//         * 作法：
//         *      Spinner物件的 setOnItemSelectedListener 方法，
//         *      並在方法中 new AdapterView.OnItemSelectedListener(){}
//         */
//        notify.setOnItemSelectedListener(
//                new AdapterView.OnItemSelectedListener() {
//                    // 選擇了項目
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                        Toast.makeText(MainActivity.this, nAdapter.getItem(position).toString(), Toast.LENGTH_LONG).show();
//                        // 用 AdapterView 的 getSelectedItem 方法取得選取項目，回傳值為 Object。
//                        //String text = notify.getSelectedItem().toString();
//                    }
//                    // 未選擇 (清單出現時按下返回時)
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                }
//        );

        // --------分隔線--------下方為ch8-4

        // 使用 GridView
//        GridView grid = findViewById(R.id.grid);
//        ArrayAdapter gAdapter =
//                new ArrayAdapter(this, android.R.layout.simple_list_item_1, func);
//        grid.setAdapter(gAdapter);
        // ch8-5-7 使用 IconAdapter 在GridView呈現 (P.206)
        GridView grid = findViewById(R.id.grid);
        IconAdapter gAdapter = new IconAdapter();
        grid.setAdapter(gAdapter);

        //------分隔線------

        // 另外一種事件處理方式 - 在MainActivity實作傾聽者介面 P196
        /* 此例功能項目被點擊的事件由"OnItemClickListener"，所以用
         * MainActivity 實作傾聽者介面並實作必要的方法後，就可呼叫
         * grid 物件的 setOnItemClickListener(this)，傳入本類別即可。
         * 步驟：
         * 1) 先為 grid 物件設定 Listener，參數先用 this，並把游標留在括號內。
         *      grid.setOnItemClickListener(this);
         * 2) 游標留在括號內，按下 Alt+Enter，選擇第二個實作介面
         *      Make'MainActivity implements' implement'android.widget.AdapterView.OnItemClickListener'
         * 3) 自動完成實作介面與必要方法：
         *      選擇onItemClick(parent:AdapterView<?>, view:View,....
         */
        grid.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // ch8-4
//        switch (position){
//            case 0:
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//            case 4:
//                finish();
//                break;
//        }
        // ch8-5-8 功能項目處理事件
        switch ((int) id) {
            case R.drawable.func_balance:
                break;
            case R.drawable.func_history:
                startActivity(new Intent(this, TransActivity.class));
                break;
            case R.drawable.func_news:
                break;
            case R.drawable.func_finance:
                // ch9-1-3
                startActivity(new Intent(this, FinanceActivity.class));
                break;
            case R.drawable.func_exit:
                finish();
                break;
        }
    }

    // ch8-5-5 (P204) 設計 IconAdapter 繼承 BaseAdapter (功能表/Code/Override Methods)
    // ch8-5-6 (P205) 實作方法
    class IconAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return func.length; // 用 func 回傳 GridView 中 item 的數量。
        }

        @Override
        public Object getItem(int position) {
            return func[position]; // 回傳 func[] 內指定位置的item內容
        }

        @Override
        public long getItemId(int position) {
            return icons[position]; // 回傳 position 所對應的id值，此值可供辨識、不重複。
        }

        @Override   // 回傳處理後的 view 畫面，此處要謹慎處理如不小心會發生很多錯誤
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            // 當 GridView 或其他清單元件在畫面中要展示一個 item 給使用者時呼叫此方法。
            /* 傳入的第二個參數 view 即是目前呼叫手上有的 View 元件。
             * 在第一次呼叫時，傳入的 view 是 null 值，應在view是null時產生一個合適的
             * View 元件給呼叫方。
             *
             * 此 View 元件應依照傳入的position產生相對應的item，它的版面配置檔
             * 就是之前設計的 "res/layout/item_rox.xml"，程式碼如下：
             */
            View row = convertView; // 把第二個參數引入
            if (row == null) {
                // 使用呼叫 Activity的 getLayoutInflater 方法取得 LayoutInflater 物件
                // ，再使用它的 inflate 方法由版面配置檔 R.layout.item_row.xml 建立一實際 View 物件
                row = getLayoutInflater().inflate(R.layout.item_row, null);
                ImageView image = row.findViewById(R.id.item_image); // 引入配置
                TextView text = row.findViewById(R.id.item_text); // 引入配置
                image.setImageResource(icons[position]); // setImageResource方法設定圖示的圖檔資源
                text.setText(func[position]); // setText方法設定圖示上的文字
            }
            return row;
        }
    }
}