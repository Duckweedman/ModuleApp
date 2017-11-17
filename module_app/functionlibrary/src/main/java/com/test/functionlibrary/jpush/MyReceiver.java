package com.test.functionlibrary.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.test.modulebrary.event.EventCenter;
import com.test.modulebrary.util.Logout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by meijunqiang on 2017/11/15 0019 09:06.
 * 描述：自定义接收器
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "test_jpush";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Logout.log(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logout.log(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logout.log(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Logout.log(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                //TODO:meiyizhi 拿到服务器给的自定义信息内容,转换
                String extra = bundle.getString("cn.jpush.android.EXTRA");
                extra = extra.replace("\\", "");//去掉'/'
                //只拿message中的数据
                int i = extra.indexOf("\"{\"") + 1;
                int j = extra.indexOf("}\"}") + 1;
                extra = extra.substring(i, j);

                //拿出extra中全部数据
//                extra = extra.replace("\"{", "{"); //去掉头尾引号。
//                extra = extra.replace("}\"}", "}}"); //去掉头尾引号。
                Gson gson = new Gson();
                MessageVO bean = gson.fromJson(extra, MessageVO.class);
//                String message = extra.substring(extra.indexOf('-'), extra.length() - 2).trim();
                Logout.log(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId + "转换的数据bean的contents为:" + bean.message.contents);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logout.log(TAG, "[MyReceiver] 用户点击打开了通知");

                //打开自定义的Activity
//                Intent i = new Intent(context, MessageCenterActivity.class);
//                i.putExtras(bundle);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logout.log(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logout.log(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logout.log(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
            if (!JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                EventCenter event = new EventCenter();
                event.eventCode = EventCenter.JPUSH_MESSAGE;
                event.data = intent;
                EventBus.getDefault().post(event);
            }
        } catch (Exception e) {
            Logout.log(e.getMessage());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logout.log(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logout.log(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
