package com.smart.androidutils.activity.reflect;

import java.io.Serializable;

/**
 * Created by smart on 2017/5/22.
 */

public class Mooc extends ReMooc implements Serializable{
    private int mAge;
    private String mName;

    public Mooc(){

    }
    public Mooc(int age) {
        mAge = age;
    }
    public Mooc(int age, String name,String b) {
        mAge = age;
        mName = name;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
