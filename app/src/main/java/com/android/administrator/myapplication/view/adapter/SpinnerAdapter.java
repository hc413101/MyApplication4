package com.android.administrator.myapplication.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.administrator.myapplication.Bean.Star;
import com.android.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class SpinnerAdapter extends BaseAdapter{
    private ImageOnclikListener imageOnclikListener;
    public interface ImageOnclikListener{
        void onClick(int position);
    }

    private List<Star> stars = new ArrayList<>();
    private Context context;
    public SpinnerAdapter(List<Star> stars,Context context,ImageOnclikListener imageOnclikListener){
        this.stars = stars;
        this.context = context;
        this.imageOnclikListener = imageOnclikListener;
    }

    @Override
    public int getCount() {
        return stars.size();
    }

    @Override
    public Object getItem(int position) {
        return stars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_layout,null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.img = convertView.findViewById(R.id.img);
            viewHolder.img.setImageResource(stars.get(position).getImage());
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageOnclikListener.onClick(position);
                }
            });
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name_value);
            viewHolder.tv_name.setText(stars.get(position).getName());
            viewHolder.tv_age = convertView.findViewById(R.id.tv_age_value);
            viewHolder.tv_age.setText(String.valueOf(stars.get(position).getAge()));
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.img.setImageResource(stars.get(position).getImage());
            viewHolder.tv_name.setText(stars.get(position).getName());
            viewHolder.tv_age.setText(String.valueOf(stars.get(position).getAge()));
        }

        return convertView;
    }

   private class ViewHolder{
        public ViewHolder(){

        }
        TextView tv_name ;
        TextView tv_age;
        ImageView img;

    }
}
