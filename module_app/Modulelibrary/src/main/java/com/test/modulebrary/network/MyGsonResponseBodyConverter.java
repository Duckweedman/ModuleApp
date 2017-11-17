package com.test.modulebrary.network;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by meijunqiang on 2017/11/4 0011 15:14.
 * 描述：自定义解析器
 */

public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    public MyGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //TODO:meiyizhi 拿到原始的请求Gson数据，自定义解析方式
        String json = value.string();
        return gson.fromJson(json, type);
    }
}