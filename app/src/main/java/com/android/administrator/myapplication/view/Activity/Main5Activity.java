package com.android.administrator.myapplication.view.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.administrator.myapplication.Bean.Star;
import com.android.administrator.myapplication.R;
import com.android.administrator.myapplication.view.adapter.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main5Activity extends AppCompatActivity {

    private Spinner spinner;
    private List<Star> stars = new ArrayList<Star>
            (Arrays.asList(new Star("张靓颖", 33, R.mipmap.zly),
                    new Star("佟丽娅", 33, R.mipmap.tly),
                    new Star("刘亦菲", 33, R.mipmap.lyf),
                    new Star("舒畅", 33, R.mipmap.sc),
                    new Star("迪丽热巴", 33, R.mipmap.dlrb),
                    new Star("杨乐乐", 33, R.mipmap.yll),
                    new Star("郭雪芙", 33, R.mipmap.gxf),
                    new Star("蔡依林", 33, R.mipmap.cyl),
                    new Star("邓紫棋", 33, R.mipmap.dzq),
                    new Star("范冰冰", 33, R.mipmap.fbb),
                    new Star("泰勒", 33, R.mipmap.tl)
            ));

    private View showImageView;
    private ImageView largeImage;
    private Button cancel;
    private AnimatorSet animationSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initView();
    }

    private void initView() {
        spinner = findViewById(R.id.spinner);
        showImageView = findViewById(R.id.show_img);
        largeImage = findViewById(R.id.large_img);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView.setVisibility(View.GONE);
                animationSet.cancel();
            }
        });

        spinner.setAdapter(new SpinnerAdapter(stars, this, new SpinnerAdapter.ImageOnclikListener() {
            @Override
            public void onClick(int position) {
                spinner.setSelection(position);
                showImageView.setVisibility(View.VISIBLE);
                largeImage.setImageResource(stars.get(position).getImage());
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(showImageView,"scaleX",0f,1f).setDuration(200);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(showImageView,"scaleY",0f,1f).setDuration(200);
                animationSet = new AnimatorSet();
                animationSet.play(scaleX).with(scaleY);
                animationSet.start();
            }
        }));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
