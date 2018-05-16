package com.android.administrator.myapplication.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.administrator.myapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class AutoCompleteTextViewAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<String> data ;
    private LayoutInflater layoutInflater;
    private DeleteItemListener itemListener;
    public AutoCompleteTextViewAdapter(Context context, List<String>data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold;
        if(view == null){
            view = layoutInflater.inflate(R.layout.search_view_item_layout,null);
            viewHold = new ViewHold();
            viewHold.content = view.findViewById(R.id.tv_search_content);
            viewHold.cancel = view.findViewById(R.id.img_cancle);
            viewHold.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemListener!=null){
                        itemListener.itemDelete(i);
                        Log.d("tag",i+""+data.size());
                        data.remove(i);
                        notifyDataSetChanged();
                    }
                }
            });
            view.setTag(viewHold);
            viewHold.content.setText(data.get(i));
        }else{
            viewHold = (ViewHold) view.getTag();
            viewHold.content.setText(data.get(i));
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
    }

    class ViewHold{
        TextView content;
        ImageView cancel;
    }

    public interface DeleteItemListener{
        void itemDelete(int position);
    }

    public void setDeleteItemListenr(DeleteItemListener itemListenr){
        this.itemListener = itemListenr;
    }

    public void add(String value){
        data.add(value);
        notifyDataSetChanged();
    }

    public void clear(){
        data.removeAll(data);
        notifyDataSetChanged();
    }
}
