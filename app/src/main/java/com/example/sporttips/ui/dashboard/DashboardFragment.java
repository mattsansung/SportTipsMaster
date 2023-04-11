package com.example.sporttips.ui.dashboard;

import static com.example.sporttips.Start.Login.LoginUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sporttips.Adapter.MyAdapter;
import com.example.sporttips.Bean.RichengBean;
import com.example.sporttips.Database.DBUtils;
import com.example.sporttips.MainActivity;
import com.example.sporttips.R;
import com.example.sporttips.databinding.FragmentDashboardBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private CalendarView calendarView;
    private int myear, mmonth, day;
    private String date;
    private FloatingActionButton floatingActionButton;
    private MyAdapter myAdapter;
    private DBUtils dbUtils;
    private ListView listView;
    private ImageView imageView;
    private List<RichengBean> mm;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dbUtils = new DBUtils();
        calendarView = root.findViewById(R.id.calendar);
        floatingActionButton = root.findViewById(R.id.add_plan);
        imageView = root.findViewById(R.id.list_null);
        listView = root.findViewById(R.id.list_plan);
        getTime();
        Show(date);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //Toast.makeText(getActivity(), i+"-"+i1+1+"-"+i2, Toast.LENGTH_SHORT).show();
                myear = i;
                mmonth = i1+1;
                day = i2;
                date = myear+"-"+mmonth+"-"+day;
                Show(date);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = myear+"-"+mmonth+"-"+day;
                Intent intent = new Intent(getActivity(),AddPlan.class);
                intent.putExtra("date",date);
                intent.putExtra("year",myear);
                intent.putExtra("month",mmonth);
                intent.putExtra("day",day);
                getActivity().startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {
                int i = mm.size() - j - 1;
                if (mm.get(i).getMonth()<Calendar.getInstance().get(Calendar.MONTH)+1||mm.get(i).getDay()<Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
                    Toast.makeText(getActivity(), "该日程已过期，无法进行修改", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("richeng",(Serializable)mm);
                    intent.putExtra("index",i);
                    intent.putExtra("intent","update");
                    intent.setClass(getActivity(),AddPlan.class);
                    getActivity().startActivity(intent);
                }
            }
        });
        return root;
    }
    public void Show(String time){
        try {
            if (dbUtils.QueryPlan(LoginUser,time)==null){
                Toast.makeText(getActivity(), "暂无计划", Toast.LENGTH_SHORT).show();
            }else {
                imageView.setVisibility(View.GONE);
                mm = dbUtils.QueryPlan(LoginUser,time);
                myAdapter = new MyAdapter(getActivity(),mm);
                listView.setAdapter(myAdapter);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        myear = calendar.get(Calendar.YEAR);
        mmonth = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = myear+"-"+mmonth+"-"+day;
        return date;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}