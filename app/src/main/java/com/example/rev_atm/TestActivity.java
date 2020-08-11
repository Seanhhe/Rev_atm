package com.example.rev_atm;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "想要顯示的訊息字串", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null) // 設定點擊時加入事件處裡
                        .setAction("Action", new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                // 設計點擊訊息時的工作
                            }
                        }).show();
            }
        });
    }
}