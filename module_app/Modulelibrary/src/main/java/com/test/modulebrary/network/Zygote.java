package com.test.modulebrary.network;

import com.test.modulebrary.BuildConfig;
import com.test.modulebrary.util.Logout;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by meijunqiang on 2017/11/2 0007.
 * 描述：网络请求管理类
 */
public class Zygote {
    private static final String BASE_USER_URL = BuildConfig.BASEURL;
    private static final long TIMEOUT = 30;
    private static Zygote INSTANCE;
    private Retrofit retrofit;

    public Zygote() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_USER_URL)
                //添加gson转换器
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    /**
     * Okhttp客户端
     */
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            // 添加通用的Header
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
//                    String token = SPUserInfo.getToken(ETApplication.getAppContext());
                    builder.addHeader("platform", "1");
                    //TODO:meiyizhi 需要传入实际Token
                    builder.addHeader("token", "2");
                    builder.addHeader("Content-Type", "application/json");
                    if (Logout.DEBUG) {
                        return logDataOpt(chain, builder);
                    }
                    return chain.proceed(builder.build());
                }


            }).connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();

    /**
     * 打印请求日志
     *
     * @param chain
     * @param builder
     * @return
     * @throws IOException
     */
    private static Response logDataOpt(Interceptor.Chain chain, Request.Builder builder) throws IOException {
        String requestInfo = logRequest(chain.request());
        Logout.log(requestInfo);
        Response response = chain.proceed(builder.build());
        requestInfo = requestInfo.substring(requestInfo.lastIndexOf("/") + 1);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String result = buffer.clone().readString(charset);
        Logout.log(requestInfo + "——>" + result);
        return response;
    }

    /**
     * debug模式下，打印请求信息
     *
     * @param request
     * @throws IOException
     */
    private static String logRequest(Request request) throws IOException {
        RequestBody requestBody = request.body();
        String paramsStr = "";
        if (null != requestBody) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            paramsStr = buffer.readString(charset);
        }
        return request.url() + "?" + paramsStr;
    }
    public static Zygote getZygote() {
        if (INSTANCE == null) {
            synchronized (Zygote.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Zygote();
                }
            }
        }
        return INSTANCE;
    }
    /**
     * 根据模块返回对应的ApiService
     *
     * @param t
     * @param <T> ApiService类型
     * @return
     */
    public <T> T getApiService(Class<T> t) {
        return retrofit.create(t);
    }
}
