package com.android.administrator.myapplication.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.android.administrator.myapplication.R;

public class Main3Activity extends AppCompatActivity {

    private GridLayout gridLayout;
    private GridLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
    }

    private void initView() {
        gridLayout = (GridLayout)findViewById(R.id.grid);
        for(int i = 0;i<9;i++){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                GridLayout.Spec rowSpec = GridLayout.spec(i/3,1f);
                GridLayout.Spec columnSpec = GridLayout.spec(i%3,1f);
                layoutParams = new GridLayout.LayoutParams(rowSpec,columnSpec);
                layoutParams.columnSpec = columnSpec;
                layoutParams.rowSpec = rowSpec;
                layoutParams.height = 0;
                layoutParams.width = 0;
             }

            if (i <6)
                layoutParams.bottomMargin = 2;
            if (i % 3 == 1) {
                layoutParams.leftMargin = 2;
                layoutParams.rightMargin = 2;
            }

            Button button = new Button(this);
            button.setText(i+"");
            button.setLayoutParams(layoutParams);
            button.setBackgroundColor(0xff999999);
            button.setGravity(Gravity.CENTER);
            gridLayout.addView(button, layoutParams);

        }
    }

    public void tiao(View view){
        Intent intent = new Intent(this,Main7Activity.class);
        startActivity(intent);
    }

}
