package com.example.sporttips.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sporttips.CheckPass;
import com.example.sporttips.Database.DBUtils;
import com.example.sporttips.R;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText Et_name,Et_age,Et_password,Et_passwordCheck;
    private Button Bt_add_user;
    private String name,age,password,passwordCheck,sex="male";
    private RadioGroup radioGroup;
    private DBUtils dbUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
    }
    public void initView(){
        Et_name = findViewById(R.id.register_name);
        Et_age = findViewById(R.id.age);
        Et_password = findViewById(R.id.password);
        Et_passwordCheck = findViewById(R.id.password_check);
        Bt_add_user = findViewById(R.id.add_user);
        radioGroup = findViewById(R.id.rg_sex);

    }
    public void getText(){
        name = Et_name.getText().toString();
        age = Et_age.getText().toString();
        password = Et_password.getText().toString();
        passwordCheck = Et_passwordCheck.getText().toString();
    }
    public void initListener(){
        Bt_add_user.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.male:
                        sex = "male";
                        break;
                    case R.id.female:
                        sex = "female";
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_user:
                getText();
                if (name.isEmpty() || age.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please intput full information", Toast.LENGTH_SHORT).show();
                } else if (password.equals(passwordCheck)) {
                        if (new CheckPass(getApplicationContext()).checkPassword(password)){
                            dbUtils = new DBUtils();
                            try {
                                if (dbUtils.RegisterUser(name,password,sex,Integer.valueOf(age),0,0)==1){
                                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, Login.class);
                                    Register.this.startActivity(intent);
                                    Register.this.finish();
                                }else {
                                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else {

                        }
                    //Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "The two password entries are inconsistent", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}