package com.android.administrator.myapplication.view.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.administrator.myapplication.R;
import com.android.administrator.myapplication.view.adapter.AutoCompleteTextViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main6Activity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView searchView;
    private Button savaButton;
    private Button cleanButton;
    private String EMPTY_DATA = "";
    private String SEPARATOR = ",";
    private int HISTORY_MAX_LEANTH = 20;
    private AutoCompleteTextViewAdapter adapter;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            ProgressDialog progressDialog = new ProgressDialog(Main6Activity.this);
            progressDialog.show();
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        initView();
        handler.sendMessageAtTime(new Message(),10000);
    }

    private void initView() {
        searchView = findViewById(R.id.search);
        final List<String> data = getHistoryList();
        adapter = new AutoCompleteTextViewAdapter(this,data);
        adapter.setDeleteItemListenr(new AutoCompleteTextViewAdapter.DeleteItemListener() {
            @Override
            public void itemDelete(int position) {
                data.remove(position);
                updateData(data);
            }
        });
        searchView.setAdapter(adapter);
        savaButton = findViewById(R.id.save);
        savaButton.setOnClickListener(this);
        cleanButton = findViewById(R.id.clean);
        cleanButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save:
                addHistoryData(searchView.getText().toString().trim());
                break;
            case R.id.clean:
                cleanHistoryData();
                adapter.clear();
                break;
        }
    }

    private String getHistoryData(String key){
        SharedPreferences historyData = getSharedPreferences("SEARCH_HISTORY",MODE_PRIVATE);
        String data = historyData.getString(key,EMPTY_DATA);
        return data;
    }

    private void savaHistoryData(String data){
        SharedPreferences historyData = getSharedPreferences("SEARCH_HISTORY",MODE_PRIVATE);
        SharedPreferences.Editor editor = historyData.edit();
        editor.putString("SEARCH_HISTORY",data);
        editor.apply();
    }

    private void cleanHistoryData(){
        SharedPreferences historyData = getSharedPreferences("SEARCH_HISTORY",MODE_PRIVATE);
        SharedPreferences.Editor editor = historyData.edit();
        editor.clear();
        editor.apply();
    }

    private  void updateData(List<String> data){
        cleanHistoryData();
        String [] value = new String[data.size()];
        data.toArray(value);
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<value.length;i++){
            builder.append(value[i]+SEPARATOR);
        }
        String newData = builder.toString();
        addHistoryData(newData);
    }

    private List<String> getHistoryList(){
        String history = getHistoryData("SEARCH_HISTORY");
        List<String> data = new ArrayList<String>(Arrays.asList(history.split(SEPARATOR)));
//        最多显示20条
        if(data.size()>HISTORY_MAX_LEANTH){
            List<String> subData = data.subList(0,HISTORY_MAX_LEANTH-1);
            return subData;
        }
        return data;
    }

    private void addHistoryData(String data){
        if(data.isEmpty()){
            return;
        }
        String oldData = getHistoryData("SEARCH_HISTORY");
        StringBuilder sb;
        if(oldData.equals(EMPTY_DATA)){
            sb = new StringBuilder();
        }else {
            sb = new StringBuilder(oldData);
        }
        sb.append(data+SEPARATOR);
        if(!oldData.contains(data+SEPARATOR)){
            savaHistoryData(sb.toString());
            adapter.add(data);
        }
    }
}
