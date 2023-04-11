package com.example.sporttips.ui.notifications;

import static com.example.sporttips.Start.Login.LoginUser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sporttips.Adapter.MonthAdapter;
import com.example.sporttips.Database.DBUtils;
import com.example.sporttips.R;

import java.util.Calendar;

public class Month extends AppCompatActivity {
private ListView listView;
private MonthAdapter monthAdapter;
private DBUtils dbUtils;
private int month = Calendar.getInstance().get(Calendar.MONTH)+1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
        listView = findViewById(R.id.list_month);
        dbUtils = new DBUtils();
        try {
            monthAdapter = new MonthAdapter(this,dbUtils.QueryMonthPlan(LoginUser,month));
            listView.setAdapter(monthAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}