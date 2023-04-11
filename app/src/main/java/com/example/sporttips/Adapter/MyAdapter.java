package com.example.sporttips.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sporttips.Bean.RichengBean;
import com.example.sporttips.Database.DBUtils;
import com.example.sporttips.R;

import java.util.Calendar;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<RichengBean> mList;
    private LayoutInflater layoutInflater;
    private DBUtils dbUtils;
    private Context context;
    private int month = Calendar.getInstance().get(Calendar.MONTH)+1;
    private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    public MyAdapter(Context context, List<RichengBean> list){
        this.context = context;
        mList = list;
        layoutInflater = LayoutInflater.from(context);
        dbUtils = new DBUtils();
    }
    @Override
    public int getCount() {
        return mList.size() ;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public View getView(int j, View view, ViewGroup viewGroup) {
        int i = mList.size()-j-1;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item, null);
            viewHolder.textView = (TextView)view.findViewById(R.id.beizhu);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.item_time);
             viewHolder.textView2 = (TextView)view.findViewById(R.id.leibie);
             //viewHolder.textView3 = (TextView) view.findViewById(R.id.item_alarm);
            /*viewHolder.textView4 = (TextView) view.findViewById(R.id.place);*/
            viewHolder.imageView = (ImageView)view.findViewById(R.id.dengjicolor);
            viewHolder.imageView1 = (ImageView)view.findViewById(R.id.im_done);
            viewHolder.checkBox = (CheckBox)view.findViewById(R.id.ck_done);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(mList.get(i).getSport());
        viewHolder.textView1.setText(mList.get(i).getJihua());
        viewHolder.textView2.setText(mList.get(i).getQiangdu()+":");
         //viewHolder.textView3.setText(mList.get(i).getAlarm());
        /*viewHolder.textView3.setText("联系方式:"+mList.get(i).getPhone());
        viewHolder.textView4.setText("地点:"+mList.get(i).getPlace());*/
        if (mList.get(i).getMonth()<month||mList.get(i).getDay()<day){
            viewHolder.checkBox.setClickable(false);
        }else {
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        try {
                            dbUtils.UpdateDone("是", mList.get(i).getId());
                            viewHolder.imageView1.setImageResource(R.drawable.done);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            dbUtils.UpdateDone("否", mList.get(i).getId());
                            viewHolder.imageView1.setImageResource(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }

        });
        }
        if (mList.get(i).getDone().equals("是")){
            viewHolder.checkBox.setChecked(true);
            viewHolder.imageView1.setImageResource(R.drawable.done);
        }else {
            viewHolder.checkBox.setChecked(false);
            viewHolder.imageView1.setImageResource(0);
        }
        switch (mList.get(i).getQiangdu()){
            case "剧烈":
                viewHolder.imageView.setBackgroundColor(Color.RED);
                break;
            case "一般":
                viewHolder.imageView.setBackgroundColor(Color.YELLOW);
                break;
            case "轻松":
                viewHolder.imageView.setBackgroundColor(Color.BLUE);
                break;
        }
        return view;
    }
    public final class ViewHolder {
        public TextView textView, textView1, textView2, textView3,textView4;
        public ImageView imageView,imageView1;
        public CheckBox checkBox;
    }
}
