package com.android.administrator.myapplication.Bean;

/**
 * Created by Administrator on 2018/5/15.
 */

public class Star {
    private String name;
    private int age;
    private int image;

    public Star (){}
    public Star(String name,int age,int image_id){
        this.age = age;
        this.name = name;
        this.image = image_id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getImage() {
        return image;
    }
}
