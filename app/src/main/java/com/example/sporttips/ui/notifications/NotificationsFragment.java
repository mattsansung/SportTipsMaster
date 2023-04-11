package com.example.sporttips.ui.notifications;

import static com.example.sporttips.Database.DBUtils.userid;
import static com.example.sporttips.Start.Login.LoginUser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sporttips.CheckPass;
import com.example.sporttips.Database.DBUtils;
import com.example.sporttips.Kaluli;
import com.example.sporttips.R;
import com.example.sporttips.Start.Login;
import com.example.sporttips.databinding.FragmentNotificationsBinding;


public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private AlertDialog.Builder builder;
    private FragmentNotificationsBinding binding;
    private TextView User,height,weight,Bmi,advice;
    private TextView Leibie,Update_pass,Exit,delete,month,Kaluli;
    private ImageView Imuser,input;
    private DBUtils dbUtils;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private float bmi,h,w;
    private String adv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initView(root);
        initListener();
        User.setText("Name:"+LoginUser);
        Ad(bmi);
        return root;
    }
    public void initView(View view){
        User = view.findViewById(R.id.user_name);
        height = view.findViewById(R.id.user_height);
        weight = view.findViewById(R.id.user_weight);
        Bmi = view.findViewById(R.id.user_bmi);
        advice = view.findViewById(R.id.user_advice);
        Leibie = view.findViewById(R.id.update_yundongleibie);
        Update_pass = view.findViewById(R.id.update_password);
        Exit = view.findViewById(R.id.exit_login);
        Kaluli = view.findViewById(R.id.kaluli);
        month = view.findViewById(R.id.month_baogao);
        input = view.findViewById(R.id.input);
        dbUtils = new DBUtils();
        sharedPreferences = getActivity().getSharedPreferences("Height", Context.MODE_PRIVATE);
        h = sharedPreferences.getFloat("height",0);
        w = sharedPreferences.getFloat("weight",0);
        bmi = sharedPreferences.getFloat("bmi",0);
        height.setText("Heigt:"+h+"m");
        weight.setText("Weight:"+w+"kg");
        Bmi.setText("BMI:"+String.format("%.2f",bmi));
    }
    public void initListener(){
        input.setOnClickListener(this);
        Leibie.setOnClickListener(this);
        Update_pass.setOnClickListener(this);
        month.setOnClickListener(this);
        Kaluli.setOnClickListener(this);
        Exit.setOnClickListener(this);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.input:
                try {
                    showMyStyle();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.update_yundongleibie:
                Intent intent = new Intent(getContext(),UpdateLeibie.class);
                getActivity().startActivity(intent);
                break;
            case R.id.update_password:
                try {
                    UpdatePassword();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.month_baogao:
                Intent intent1 = new Intent(getContext(),Month.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.kaluli:
                Intent intent2 = new Intent(getContext(), com.example.sporttips.Kaluli.class);
                getActivity().startActivity(intent2);
                break;
            case R.id.exit_login:
                Intent intent3 = new Intent(getContext(), Login.class);
                getActivity().startActivity(intent3);
                break;
        }
    }
    private void showMyStyle() throws InterruptedException {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getContext()).inflate(R.layout.height, null);
        final EditText textText = view.findViewById(R.id.setheight);
        final EditText editText = view.findViewById(R.id.setweight);
        textText.setText(String.valueOf(h));
        editText.setText(String.valueOf(w));
        builder = new AlertDialog.Builder(getActivity()).setView(view).setTitle("输入信息")
                .setPositiveButton("增加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (editText.getText().toString().isEmpty()||textText.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "请填写备注事项", Toast.LENGTH_SHORT).show();
                        } else {
                            final Float he,we,bm;
                            he = Float.valueOf(textText.getText().toString());
                            we = Float.valueOf(editText.getText().toString());
                            bm = getBmi(he,we);
                            try {
                                if (dbUtils.AddHWeight(userid,Double.valueOf(textText.getText().toString()),Double.valueOf(editText.getText().toString()))==0){
                                    Toast.makeText(getActivity(), "添加失败", Toast.LENGTH_SHORT).show();
                                }else {
                                    editor = sharedPreferences.edit();
                                    editor.putFloat("height",he);
                                    editor.putFloat("weight",we);
                                    editor.putFloat("bmi",bm);
                                    editor.commit();
                                    Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
                                    height.setText("Height:"+he+"m");
                                    weight.setText("Weight:"+we+"kg");
                                    Bmi.setText(String.valueOf("BMI:"+String.format("%.2f",bm)));
                                    Ad(bm);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ;

                        }

                    }
                });
        builder.create().show();
    }
    public float getBmi(float he,float we){
        return we/(he*he);
    }
    public void Ad(float bmi){
        if (bmi==0){
            adv = "暂无意见";
        }else if (bmi>0&&bmi<18.5){
            adv = "体重偏低";
        }else if (bmi==18.5||bmi>18.5&&bmi<24){
            adv = "体重适中";
        }else if (bmi==24||bmi>24){
            adv = "体重偏重";
        }else {
            adv = "暂无意见";
        }
        advice.setText(adv);
    }
    private void UpdatePassword() throws InterruptedException {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getContext()).inflate(R.layout.updatepass, null);
        final EditText old = view.findViewById(R.id.setheight);
        final EditText newpass = view.findViewById(R.id.setnewpass);
        final EditText againpass = view.findViewById(R.id.setweight);
        builder = new AlertDialog.Builder(getActivity()).setView(view).setTitle("Update Password")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (old.getText().toString().isEmpty()||newpass.getText().toString().isEmpty()||againpass.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please input password", Toast.LENGTH_SHORT).show();
                        } else if (newpass.getText().toString().equals(againpass.getText().toString())){
                           if(old.getText().toString().equals(dbUtils.getLoginpassword())){
                               if (new CheckPass(getActivity()).checkPassword(newpass.getText().toString())){
                                   try {
                                       if (dbUtils.UpdatePassword(newpass.getText().toString(),userid)==0){
                                           Toast.makeText(getActivity(), "密码修改失败", Toast.LENGTH_SHORT).show();
                                       }else {
                                           Toast.makeText(getActivity(), "密码修改成功", Toast.LENGTH_SHORT).show();
                                       }
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               }else {

                               }

                           }else {
                               Toast.makeText(getActivity(), "原密码错误", Toast.LENGTH_SHORT).show();
                           }

                        }else {
                            Toast.makeText(getActivity(), "两次密码不一致", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        builder.create().show();
    }
}