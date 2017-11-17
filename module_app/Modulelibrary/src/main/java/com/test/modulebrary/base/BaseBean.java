package com.test.modulebrary.base;

import java.io.Serializable;

/**
 * Created by meijunqiang on 2017/11/3 0007.
 * 描述：所有数据结构的基类，方便数据携带
 */

public class BaseBean<T> implements Serializable {
    public String message;
    public String code;
    public String timestamp;
    public boolean success;
    public T data;
    public String requestValue;

    /**
     * code请求判断
     * @return
     */
    public boolean isSuccess() {
        if (null==code) {
            return false;
        }
        return code.matches("200|0");
    }
}
