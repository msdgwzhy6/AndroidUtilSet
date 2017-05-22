package com.smart.androidutils.activity.classactivity;

import android.util.Log;

/**
 * Created by smart on 2017/5/22.
 */

/*
* 静态的方法属性初始化，是在加载类的时候初始化。而非静态方法属性初始化，是new类实例对象的时候加载。
* */
public class Person {
    private int age;
    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    static {
        Log.i("xxx", "静态的参数初始化,只会初始化一次" );
    }

    public Person() {
        Log.i("xxx", "构造函数");
    }
    {
        Log.i("xxx", "非静态的参数初始化" );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
