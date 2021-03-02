package com.example.rev_atm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    private List<Transaction> trans; // 設計一個集合屬性，名稱為 trans

    // 建構式
    public TransactionAdapter(List<Transaction> trans) {
        this.trans = trans; // 將傳入的trans參數儲存在屬性中
    }

    /* 設計 ViewHolder 內部類別 (CH 11-4-3 Page.302)
     * 在 TransactionAdapter 內設計一個類別層級的 ViewHolder 類別，
     * 繼承 RecyclerView.ViewHolder，在此類別中設計一筆交易在畫面上
     * 的元件屬性，分別為 dateTextView、amountTextView 與
     * typeTextView 三個 TextView 元件，並在它的建構子中取得元件並
     * 轉型，如下：
     */

    // 內部類別
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateTextView; // 設計 TextView 屬性
        private final TextView amountTextView;
        private final TextView typeTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.col_date); // 取得itemView中欲顯示日期的TextView物件，儲存為屬性
            amountTextView = itemView.findViewById(R.id.col_amount); // 取得itemView中欲顯示金額的TextView物件，儲存為屬性
            typeTextView = itemView.findViewById(R.id.col_type); // 取得itemView中欲顯示型態的TextView物件，儲存為屬性
        }
    }

    /* 繼承 RecyclerView.Adapter [ CH 11-4-3 (P.303) ]
     * 為 TransactionAdapter 加上繼承 RecyclerView.Adapter 類別，
     * 宣告其內使用的 ViewHolder 為上一步驟所設計完成的
     * TransactionAdapter.ViewHolder，語法如下：
     * (請參考page.303下半)
     * public class TransactionAdapter extends
     *     RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
     * }
     *
     * (Page.304)
     * 且Adapter類別是抽象類別，需實作三個必要方法(Implement methods)
     */


    /* 當RecyclerView需要顯示一列紀錄，會呼叫Adapter內的這個方法
     * 先取得一個 ViewHolder 物件，此方法必須提供一個可以展示該列紀錄
     * 的View物件，可以使用程式碼自行產生，或設計版面配置檔(layout XML)
     * 再使用LayoutInflater產生View。這個產生的 ViewHolder 將會在
     * 下一個實作方法 onBindViewHolder 中使用。
     *
     * 由於以獨立的類別設計檔 TransactionAdapter，因此得要透過此方法的
     * ViewGroup parent 參數取得 LayoutInflater 來產生 View。
     * (假設是以內部類別的方式將其設計在 TransActivity 內時，可以直接呼
     * 叫 Activity 的 getLayoutInflater 方法快速取得，程式碼會較簡潔)
     */
    @NonNull
    @Override // 系統呼叫，創造一個 ViewHolder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_trans, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /* 實作 onBindViewHolder 方法 (Page.305)
     * 當 RecyclerView 準備要展示一列特定位置(position)的紀錄時，
     * 會呼叫此方法，在呼叫此方法前已自動執行了上一個onCreateViewHolder
     * 方法得到 ViewHolder 物件了，也就是下列參數中的 holder 物件。
     * 因此，在這個方法中只需要將 holder 物件中的元件設定為想要的內容，
     * 如該列的日期、金額等 TextView 物件。
     */
    @Override // 傳入參數有 ViewHolder 物件 holder，與所要展示的 position
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("TRANS", position + ""); // 除錯訊息
        Transaction tran = trans.get(position); // 設定該列紀錄的三個TextView元件的內容
        holder.dateTextView.setText(tran.getDate());
        holder.amountTextView.setText(tran.getAmount() + "");
        holder.typeTextView.setText(tran.getType() + "");
    }
    // 完成了上述的方法時，TransactionAdapter類別的設計完成了，
    // 將在下個步驟產生它，並設定在 RecyclerView 中。(Page.306)

    /* 使用 Adapter (Page.306)
     * 最後，請回到 TransActivity 活動，自行設計一個新方法
     * setupRecyclerView，傳入參數為 List<Transaction>
     */

    @Override // 取得目前資料筆數，應傳回 List<Transaction> trans 集合的筆數
    public int getItemCount() {
        if (trans != null){
            return trans.size();
        }else {
            return 0;
        }
    }
}
