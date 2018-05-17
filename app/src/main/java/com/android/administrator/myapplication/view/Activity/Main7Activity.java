package com.android.administrator.myapplication.view.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.administrator.myapplication.R;

import java.lang.reflect.Method;

public class Main7Activity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        textView = findViewById(R.id.tv_cs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("父标题");
        toolbar.setSubtitle("子标题");
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        registerForContextMenu(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.a:
                        Toast.makeText(this, "菜单一", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.b:
                        Toast.makeText(this,"菜单二",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.c:
                        Toast.makeText(this,"菜单三",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.d:
                        Toast.makeText(this,"菜单四",Toast.LENGTH_SHORT).show();
                        default:
                            break;
                }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
        return super.onMenuOpened(featureId,menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contexmenu,menu);
//        menu.add(0,1,1,"菜单一");
//        menu.add(0,2,2,"菜单二");
//        menu.add(0,3,3,"菜单三");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case 1:
                Toast.makeText(this, "菜单一", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"菜单二",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this,"菜单三",Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this,"菜单四",Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }


}
