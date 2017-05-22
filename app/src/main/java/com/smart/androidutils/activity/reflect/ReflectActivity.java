package com.smart.androidutils.activity.reflect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.smart.androidutils.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReflectActivity extends AppCompatActivity {

    @BindView(R.id.click_me)
    Button mClickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);
        setTitle("反射机制");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.click_me)
    public void onClickMe() {
//        Toast.makeText(this, "sds", Toast.LENGTH_SHORT).show();
        try {
            Class<?> clazz = Class.forName("com.smart.androidutils.activity.reflect.Mooc");
            //这种方式，ReMooc必须有一个默认的public构造函数
            Mooc iReMooc = (Mooc) clazz.newInstance();
            iReMooc.setString("ddddd");
            Log.i("xxx", "onClickMe  " +iReMooc.getString());

            // 获取所有的接口
            Class<?> intes[] = clazz.getInterfaces();
            Log.i("xxx", "onClickMe" +"clazz实现的接口有：");
            for (int i = 0; i < intes.length; i++) {
                Log.i("xxx", (i + 1) + "：" + intes[i].getName());
            }
            //获得所有默认构造函数
            Constructor<?> cons[] = clazz.getConstructors();
            // 查看每个构造方法需要的参数
            for (int i = 0; i < cons.length; i++) {
                Class<?> clazzs[] = cons[i].getParameterTypes();
                Log.i("xxx", "cons[" + i + "] (");
                for (int j = 0; j < clazzs.length; j++) {
                    Log.i("xxx", clazzs[j].getName());
                }
                Log.i("xxx", ")");
            }
            // 取得本类的全部属性
            Field[] field = clazz.getDeclaredFields();
            for (int i = 0; i < field.length; i++) {
                // 权限修饰符
                int mo = field[i].getModifiers();
                String priv = Modifier.toString(mo);
                // 属性类型
                Class<?> type = field[i].getType();
                System.out.println(priv + " " + type.getName() + " " + field[i].getName() + ";");
            }
            // 取得实现的接口或者父类的属性
            Field[] filed1 = clazz.getFields();
            for (int i = 0; i < filed1.length; i++) {
                // 权限修饰符
                int mo = filed1[i].getModifiers();
                String priv = Modifier.toString(mo);
                // 属性类型
                Class<?> type = filed1[i].getType();
                System.out.println(priv + " " + type.getName() + " " + filed1[i].getName() + ";");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
