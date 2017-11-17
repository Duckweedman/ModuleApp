package com.test.modulebrary.event;

import com.test.modulebrary.jsbridge.CallBackFunction;

import org.json.JSONObject;

/**
 * Created by meijunqiang on 2017/11/9  18:05.
 * 描述：EventCenter Event信息处理中心
 */

public class EventCenter {
    public String str;//处理字符串
    public JSONObject gsonParams;//处理字符串
    public CallBackFunction callBack;
    public static int JPUSH_MESSAGE = -3;
    public int eventCode = -1;
    public Object data;
}
