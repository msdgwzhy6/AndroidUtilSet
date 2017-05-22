package com.smart.androidutils.activity.classactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.smart.androidutils.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassActivity extends AppCompatActivity {


    @BindView(R.id.id_btn_class)
    Button mBtnClass;
    @BindView(R.id.id_btn_instance)
    Button mBtnInstance;
    @BindView(R.id.id_btn_method)
    Button mBtnMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.id_btn_class)
    public void onMBtnClassClicked() {
        //通过forName("类名")获取
        Class clazz_for_name;
        try {
            Log.i("xxx", "-------------------------------------");
            clazz_for_name = Class.forName("com.smart.androidutils.activity.classactivity.Person");
            Log.i("xxx", "通过forName(\"类名\")获取" + clazz_for_name.getName());
            Log.i("xxx", "-------------------------------------");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //通过类名.class
        Class clazz_class = Person.class;
        Log.i("xxx", "通过类名.class" + clazz_class.getName());
        Log.i("xxx", "-------------------------------------");
        //通过该类的实例对象获取Test的Class对象
        Person person = new Person();
        Class clazz_get_class = person.getClass();
        Log.i("xxx", "实例对象.getClass()" + clazz_get_class.getName());
        Log.i("xxx", "-------------------------------------");

        /*
        * 生成Class对象的过程其实是如此的：
        * 当我们编写一个新的Java类时,JVM就会帮我们编译成class对象,存放在同名的.class文件中。
        * 在运行时，当需要生成这个类的对象，JVM就会检查此类是否已经装载内存中。
        * 若是没有装载，则把.class文件装入到内存中。若是装载，则根据class文件生成实例对象。
        */
    }

    /*
    * 以前操作对象的时候用的是new关键字完成，并通过对象.成员方式进行调用;
    * 现在可以通过字节码对象完成以上动作;
    * */
    @OnClick(R.id.id_btn_instance)
    public void onMBtnInstanceClicked() {
        //加载Person.class文件，获取Person.class文件对象的Class对象
        Class clazz = null;
        try {
            //通过字节码文件对象，对指定类进行创建并初始化。
            clazz = Class.forName("com.smart.androidutils.activity.classactivity.Person");
            Person p = (Person) clazz.newInstance();
            //通过对象调用成员
            //p.function("heihei");
            p.setAge(10);
            Log.i("xxx", "onMBtnInstanceClicked" + p.getAge());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.id_btn_method)
    public void onViewClicked() {

        //加载Person.class文件，获取Person.class文件对象的Class对象
        Class clazz = null;
        try {
            //通过字节码文件对象，对指定类进行创建并初始化。
            clazz = Class.forName("com.smart.androidutils.activity.classactivity.Person");
            Person p = (Person) clazz.newInstance();
            //通过对象调用成员
            p.setAge(10);
            Log.i("xxx", "onMBtnInstanceClicked" + p.getAge());
            /*
            * 类可以作为参数进行传递，那么方法也一样可以作为参数进行传递，
            * 因为方法存在于字节码文件内， 所以可以通过Class对象获取字节码文件中的内容
            * */
            Method m =  clazz.getMethod("setName",String.class);
            m.invoke(p,"heihei");

            Log.i("xxx", "onMBtnInstanceClicked" + p.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
