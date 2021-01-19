package com.example.rev_atm;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Transaction {
    // 屬性
    /* 在Transaction類別的第一層
     * 設計帳號、日期、金額與型態如下
     */
    String account;
    String date;
    int amount;
    int type;

    /* 建構式
     * 為Transaction設計一個無參數的建構式，
     * 再建立一個帶有四個參數的建構式。(alt+insert選單操作)
     */
    public Transaction() {
    }

    // 四參數建構式 (alt+insert 選單操作)(功能表Code\Generate)
    public Transaction(String account, String date, int amount, int type) {
        this.account = account;
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    /* 建立 getter/setter 方法
     * (alt+insert選單操作)
     * (功能表Code\Generate)
     */

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
//
//    /* ch 11-3-2 使用JSON.org解析 (P.291)
//     * 基於回傳的字串，以JSONArray建立物件 array，再使用迴圈取出陣列中
//     * 的JSON物件(使用JSONObject類別)，再利用JSONObject提供的方法取出
//     * 每一筆交易內的四項資料，依這四項資料產生Java 的 Transaction物件
//     * ，再將物件加入到 ArrayList 集合物件 trans 中。
//     * (以為要把parseJSON方法寫在此處，後來發現只留物件結構，parseJSON方法要放在 TransActivity.java)
//     */
//    private void parseJSON(String s) {
//        ArrayList<Transaction> trans = new ArrayList(); // 準備ArrayList物件 trans，裡頭只能放Transaction物件
//        try {
//            JSONArray array = new JSONArray(s); // 將傳入的字串s 交給JSONArray的建構式，產生array物件
//            for (int i = 0; i < array.length(); i++) {  // 以迴圈依序取出交易紀錄
//                JSONObject obj = array.getJSONObject(i);  // 以索引值取得JSONObject物件 obj
//                String account = obj.getString("account"); // 呼叫JSONObject類的getXXX方法取得各個屬性值
//                String date = obj.getString("date");
//                int amount = obj.getInt("amount");
//                int type = obj.getInt("type");
//                Log.d("JSON : ", account + "/" + date + "/" + amount + "/" + type);
//                Transaction t = new Transaction(account, date, amount, type); // 產生 Transaction 物件，代表一筆交易紀錄
//                trans.add(t); // 將t物件加入到集合物件中
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
