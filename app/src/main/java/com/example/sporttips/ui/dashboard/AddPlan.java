package com.example.sporttips.ui.dashboard;

import static com.example.sporttips.Start.Login.LoginUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sporttips.Bean.RichengBean;
import com.example.sporttips.Database.DBUtils;
import com.example.sporttips.MainActivity;
import com.example.sporttips.R;

import java.util.ArrayList;
import java.util.List;

public class AddPlan extends AppCompatActivity implements View.OnClickListener {
    private TextView user,time;
    private EditText plan;
    private Spinner qiangdu,leixing;
    private Button button;
    private ImageView imageView1;
    private List<String> list,list2;
    private ArrayAdapter<String> adapter,adapter1;
    private String qiang,leibie;
    private int sum = 40;
    private DBUtils dbUtils;
    private String name,date;
    private int year,month,day;
    private int index;
    private List<RichengBean> ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        initView();
        try {
            Lei();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Dengji();
        if (getIntent().getStringExtra("intent")==null){

        }else{
            index = getIntent().getIntExtra("index",0);
            ll = (List<RichengBean>) getIntent().getSerializableExtra("richeng");
            plan.setText(ll.get(index).getJihua());
            for(int j = 0;j<adapter1.getCount();j++){
                if (ll.get(index).getQiangdu().equals(adapter1.getItem(j).toString())){
                    qiangdu.setSelection(j);
                    switch (j) {
                        case 0:
                            imageView1.setBackgroundColor(Color.RED);
                            break;
                        case 1:
                            imageView1.setBackgroundColor(Color.YELLOW);
                            break;
                        case 2:
                            imageView1.setBackgroundColor(Color.BLUE);
                            break;
                    }
                }
            }
            for(int j = 0;j<adapter.getCount();j++){
                if (ll.get(index).getSport().equals(adapter.getItem(j).toString())){
                    leixing.setSelection(j);
                }
            }
            button.setText("Update");
        }
    }
    public void Lei() throws InterruptedException {
        list = dbUtils.QueryLeibie(LoginUser);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leixing.setAdapter(adapter);
        leixing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                leibie = list.get(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                leibie = list.get(0).toString();
            }
        });
    }
    public void initView(){
        user = findViewById(R.id.name);
        time = findViewById(R.id.sumtime);
        plan = findViewById(R.id.shixiang);
        qiangdu = findViewById(R.id.dengji);
        leixing = findViewById(R.id.leibie);
        button = findViewById(R.id.Add);
        imageView1 = findViewById(R.id.color);
        button.setOnClickListener(this);
        dbUtils = new DBUtils();
        Intent intent = getIntent();
        year = intent.getIntExtra("year",2023);
        month = intent.getIntExtra("month",3);
        day = intent.getIntExtra("day",00);
        date = intent.getStringExtra("date");
        user.setText(LoginUser);
    }
    public void Dengji() {
        list2 = new ArrayList<String>();
        list2.add("剧烈");
        list2.add("一般");
        list2.add("轻松");
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qiangdu.setAdapter(adapter1);
        qiangdu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                qiang = list2.get(i).toString();
                switch (i) {
                    case 0:
                        imageView1.setBackgroundColor(Color.RED);
                        sum = 40;
                        break;
                    case 1:
                        imageView1.setBackgroundColor(Color.YELLOW);
                        sum = 20;
                        break;
                    case 2:
                        imageView1.setBackgroundColor(Color.BLUE);
                        sum = 10;
                        break;
                }
                time.setText(sum+"mins");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                qiang = "剧烈";
                imageView1.setBackgroundColor(Color.RED);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Add:
                name = plan.getText().toString();
                    if (name.length()==0||name==null){
                        Toast.makeText(this, "Please input your plan", Toast.LENGTH_SHORT).show();
                    }else {
                        if (button.getText().equals("Update")){
                            try {
                                if (dbUtils.UpdatePlan(qiang,leibie,name,sum,ll.get(index).getId())==0){
                                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
                                    button.setText("Add Plan");
                                    Intent intent = new Intent(AddPlan.this, MainActivity.class);
                                    AddPlan.this.startActivity(intent);
                                    AddPlan.this.finish();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }else {
                            try {
                                if (dbUtils.AddPlan(LoginUser,qiang,sum,leibie,name,year,month,day,date)==0){
                                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddPlan.this, MainActivity.class);
                                    AddPlan.this.startActivity(intent);
                                    AddPlan.this.finish();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                break;
        }
    }
}