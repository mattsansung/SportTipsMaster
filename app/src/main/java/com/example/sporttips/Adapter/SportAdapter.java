package com.example.sporttips.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sporttips.Bean.RichengBean;
import com.example.sporttips.Bean.SportBean;
import com.example.sporttips.Database.DBUtils;
import com.example.sporttips.R;

import java.text.Format;
import java.util.Calendar;
import java.util.List;

public class SportAdapter extends BaseAdapter {
    private List<SportBean> mList;
    private LayoutInflater layoutInflater;
    private DBUtils dbUtils;
    private Context context;
    private int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    public SportAdapter(Context context, List<SportBean> list) {
        this.context = context;
        mList = list;
        layoutInflater = LayoutInflater.from(context);
        dbUtils = new DBUtils();
    }

    @Override
    public int getCount() {
        return mList.size();
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
        int i = mList.size() - j - 1;
       SportAdapter.ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.sport_item, null);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.leibie);
            viewHolder.textView = (TextView) view.findViewById(R.id.beizhu);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.item_time);
            viewHolder.textView3 = (TextView) view.findViewById(R.id.item_month);
            /*viewHolder.textView4 = (TextView) view.findViewById(R.id.place);*/
            viewHolder.imageView = (ImageView) view.findViewById(R.id.dengjicolor);
            view.setTag(viewHolder);
        } else {
            viewHolder = (SportAdapter.ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(String.valueOf(":"+ (mList.get(i).getTime()))+" seconds");
        viewHolder.textView1.setText(mList.get(i).getYear()+"-"+mList.get(i).getMonth()+"-"+mList.get(i).getDay());
        viewHolder.textView2.setText(mList.get(i).getSport()+"  ");
        viewHolder.textView3.setText(mList.get(i).getHour()+":"+mList.get(i).getMin());
        /*viewHolder.textView3.setText("联系方式:"+mList.get(i).getPhone());
        viewHolder.textView4.setText("地点:"+mList.get(i).getPlace());*/
        viewHolder.imageView.setBackgroundResource(R.drawable.relax);
        switch (mList.get(i).getSport()) {
            case "跑步":
                viewHolder.imageView.setBackgroundResource(R.drawable.run);
                break;
            case "拉伸":
                viewHolder.imageView.setBackgroundResource(R.drawable.ic_baseline_self_improvement_24);
                break;
            case "力量训练":
                viewHolder.imageView.setBackgroundResource(R.drawable.ic_baseline_sports_handball_24);
                break;
        }
        return view;
    }

    public final class ViewHolder {
        public TextView textView, textView1, textView2, textView3, textView4;
        public ImageView imageView, imageView1;
        public CheckBox checkBox;
    }
}
