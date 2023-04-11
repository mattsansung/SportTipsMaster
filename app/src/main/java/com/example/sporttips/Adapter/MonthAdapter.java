package com.example.sporttips.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sporttips.Bean.RichengBean;
import com.example.sporttips.R;

import java.util.List;

public class MonthAdapter extends BaseAdapter {
    private List<RichengBean> mList;
    private LayoutInflater layoutInflater;

    public MonthAdapter(Context context, List<RichengBean> list){
        mList = list;
        layoutInflater = LayoutInflater.from(context);
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
            view = layoutInflater.inflate(R.layout.month_item, null);
            viewHolder.textView = (TextView)view.findViewById(R.id.beizhu);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.item_time);
            viewHolder.textView2 = (TextView)view.findViewById(R.id.leibie);
             viewHolder.textView3 = (TextView) view.findViewById(R.id.item_month);
            /*viewHolder.textView4 = (TextView) view.findViewById(R.id.place);*/
            viewHolder.imageView = (ImageView)view.findViewById(R.id.dengjicolor);
            viewHolder.imageView1 = (ImageView)view.findViewById(R.id.im_done);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(mList.get(i).getSport());
        viewHolder.textView1.setText(mList.get(i).getJihua());
        viewHolder.textView2.setText(mList.get(i).getQiangdu()+":");
        viewHolder.textView3.setText(mList.get(i).getDate());
        /* viewHolder.textView2.setText(mList.get(i).getIntro());
        viewHolder.textView3.setText("联系方式:"+mList.get(i).getPhone());
        viewHolder.textView4.setText("地点:"+mList.get(i).getPlace());*/
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
        if (mList.get(i).getDone().equals("是")){
            viewHolder.imageView1.setImageResource(R.drawable.done);
        }else {
            viewHolder.imageView1.setImageResource(R.drawable.undone);
        }
        return view;
    }
    public final class ViewHolder {
        public TextView textView, textView1, textView2, textView3,textView4;
        public ImageView imageView,imageView1;
    }
}

