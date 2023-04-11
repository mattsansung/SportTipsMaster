package com.example.sporttips;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sporttips.Database.DBUtils;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Start(View view) throws InterruptedException {
       new DBUtils().RegisterUser("ddd","ttttt","s",22,0,0);
    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }
    public void Show(View view){
        new DBUtils().Query("registeruser");
    }

}