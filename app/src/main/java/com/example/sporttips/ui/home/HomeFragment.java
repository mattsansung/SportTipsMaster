package com.example.sporttips.ui.home;

import static com.example.sporttips.Start.Login.LoginUser;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sporttips.Adapter.SportAdapter;
import com.example.sporttips.Database.DBUtils;
import com.example.sporttips.MainActivity;
import com.example.sporttips.R;
import com.example.sporttips.databinding.FragmentHomeBinding;

import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private Button button;
    private SeekBar seekBar;
    private TextView textView;
    private CountDownTimer countDownTimer;
    private ArrayAdapter adapter;
    private DBUtils dbUtils;
    private List list;
    private Spinner leixing;
    private String leibie;
    private Boolean click = true;
    private int myear, mmonth, day,hour,min,time;
    private AlertDialog.Builder builder;
    private ListView listView;
    private SportAdapter sportAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initView(root);
        try {
            Lei();
            sportAdapter = new SportAdapter(getActivity(),dbUtils.QuerySport(LoginUser));
            listView.setAdapter(sportAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText("运动时间:" + i + "mins");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return root;
    }

    public void initView(View view) {
        button = view.findViewById(R.id.start_sport);
        seekBar = view.findViewById(R.id.sport_time);
        textView = view.findViewById(R.id.time_tv);
        leixing = view.findViewById(R.id.dengjii);
        listView = view.findViewById(R.id.list_sport);
        button.setOnClickListener(this);
        seekBar.setMax(40);
        textView.setText("运动时间:" + seekBar.getProgress() + "mins");
        dbUtils = new DBUtils();
        myear = Calendar.getInstance().get(Calendar.YEAR);
        mmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    }

    public void Lei() throws InterruptedException {
        list = dbUtils.QueryLeibie(LoginUser);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_sport:
                if (seekBar.getProgress()==0){
                    Toast.makeText(getActivity(), "Please set the sport time", Toast.LENGTH_SHORT).show();
                }else {
                    if (click) {
                        setTime(seekBar.getProgress() * 1000 * 60, 1000);
                        countDownTimer.start();
                        click = false;
                    } else {
                        Stop();

                        //countDownTimer.s
                    }
                }
                break;
        }
    }

    public void setTime(int sum, int start) {
        countDownTimer = new CountDownTimer(sum, start) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = (int) ((sum/1000) - (millisUntilFinished / 1000));
                button.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                try {
                    button.setText("Done");
                    Add(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void Add(int time) throws InterruptedException {
        hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        min = Calendar.getInstance().get(Calendar.MINUTE);
        if (dbUtils.AddSport(LoginUser,myear,mmonth,day,hour,min,leibie,time) == 0) {
            Toast.makeText(getActivity(), "记录失败", Toast.LENGTH_SHORT).show();
        } else {

        }
    }
    public void Stop(){
        builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.stop)
                .setTitle("Stop Sport")
                .setMessage("Do you want to stop sport")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                countDownTimer.onFinish();
                                countDownTimer.cancel();
                                click = true;
                            }
                        });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        click = false;
                    }
                });
        builder.show();
    }
}