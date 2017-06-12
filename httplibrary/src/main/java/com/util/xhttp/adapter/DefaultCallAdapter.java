package com.util.xhttp.adapter;

/**
 * ================================================
 * 描    述：默认的工厂处理,不对返回值做任何操作
 * 修订历史：
 * ================================================
 */
public class DefaultCallAdapter<T> implements CallAdapter<Call<T>> {

    public static <T> DefaultCallAdapter<T> create() {
        return new DefaultCallAdapter<>();
    }

    @Override
    public <R> Call<T> adapt(Call<R> call) {
        return (Call<T>) call;
    }
}
