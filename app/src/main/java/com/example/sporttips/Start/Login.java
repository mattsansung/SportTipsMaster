package com.example.sporttips.Start;

import static com.example.sporttips.Database.DBUtils.getLoginName;
import static com.example.sporttips.Database.DBUtils.getLoginpassword;
import static com.example.sporttips.Database.DBUtils.userid;
import static com.example.sporttips.Database.DbOpenHelper.getConnection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sporttips.Database.DBUtils;
import com.example.sporttips.Database.DbOpenHelper;
import com.example.sporttips.Database.SP;
import com.example.sporttips.MainActivity;
import com.example.sporttips.R;
import com.example.sporttips.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText Et_login_name,Et_login_pass;
    private Button Bt_login;
    private String sc_num,sc_password;
    private CheckBox cb_pass;
    private boolean aBoolean,bBoolean;
    private RadioGroup rg_user;
    private RadioButton rb_stu,rb_tea,rb_admin;
    private String user,password;
    private TextView textView;
    private SP sp;
    private Boolean login,rem;
    private DBUtils dbUtils;
    public static String LoginUser;
    private TextView tvip;
    private AlertDialog.Builder builder;
    public static String ip;
    private Boolean connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
        user = sp.getUserName();
        password = sp.getUserPassword();
        login = sp.getLogin();
        rem = sp.getRem();
        if (rem == true){
            Intent intent = new Intent();
            intent.setClass(Login.this, Test.class);
            Login.this.startActivity(intent);
            Login.this.finish();
        }else {
            if (login == true) {
                Et_login_name.setText(user);
                Et_login_pass.setText(password);
                cb_pass.setChecked(true);
                aBoolean = true;
            } else {

            }
            if (getIntent().getStringExtra("name") == null) {

            } else {
                user = getIntent().getStringExtra("name");
                password = getIntent().getStringExtra("password");
                Et_login_name.setText(user);
                Et_login_pass.setText(password);
            }
        }
    }
    public void initView(){
        Et_login_name = findViewById(R.id.login_name);
        Et_login_pass = findViewById(R.id.login_password);
        Bt_login = findViewById(R.id.login_button);
        cb_pass = findViewById(R.id.cb_password);
        textView = findViewById(R.id.register);
        tvip = findViewById(R.id.changeip);
        sp = new SP(getApplicationContext());
        aBoolean = false;
        bBoolean = false;
    }
    public void initListener() {
        textView.setOnClickListener(this);
        Bt_login.setOnClickListener(this);
        tvip.setOnClickListener(this);
        cb_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_pass.isChecked()){
                    aBoolean = true;
                }else {
                    aBoolean = false;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                getText();
                if (sc_num.isEmpty()||sc_password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please intput full information",Toast.LENGTH_SHORT).show();
                }else {
                    ip =  sp.getIp("192.168.20.106");
                    dbUtils = new DBUtils();
                    try {
                        dbUtils.QueryUser(sc_num);
                        if (sc_num.equals(getLoginName())){
                            if (sc_password.equals(getLoginpassword())){
                                sp.SaveUser(sc_num,sc_password,aBoolean);
                                sp.setRem(bBoolean);
                                LoginUser = sc_num;
                                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                Login.this.startActivity(intent);
                                Login.this.finish();
                            }else {
                                Toast.makeText(this, "The password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(this, "Please register first", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case R.id.register:
                Intent intent = new Intent();
                intent.setClass(Login.this, Register.class);
                Login.this.startActivity(intent);
                Login.this.finish();
                break;
            case R.id.changeip:
                ChangeIP();
                break;
        }

    }

    public void getText(){
        sc_num = Et_login_name.getText().toString();
        sc_password = Et_login_pass.getText().toString();
    }
    private void ChangeIP() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.item_ip, null);
        final EditText textText = view.findViewById(R.id.ed_ip);
        builder = new AlertDialog.Builder(this).setView(view).setTitle("Ip")
                .setPositiveButton("Connect Test", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (textText.getText().toString().isEmpty()) {
                            Toast.makeText(Login.this, "填写ip", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                ConnectTest(textText.getText().toString());
                                if (connection) {
                                    sp.setIp(textText.getText().toString());
                                    ip =  sp.getIp("192.168.20.106");
                                    Toast.makeText(Login.this, "you can login now", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "Ip is incorrect", Toast.LENGTH_SHORT).show();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                });
        builder.create().show();
    }
    public void ConnectTest(String ip) throws InterruptedException {
       Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                Connection  conn =(Connection)getConnection(ip);
                Statement st;
                try {
                    if (conn ==null){
                        connection = false;
                    }else {
                        connection = true;
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
       thread.start();
        thread.join();

    }

}