package com.yt.net.client;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * created by Albert
 */
public class ApiStore {

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    //private static final String BASE_URL = "http://192.168.0.9:8080/";
    private static final String BASE_URL = "http://192.168.43.136/";
    private static final long DEFAULT_TIME_OUT = 20;
    private static final long DEFAULT_READ_TIME_OUT = 20;

    public static Application application;

    public static void init(Application application){
        ApiStore.application=application;
    }

    public static <T> T create(Class<T> cls) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(cls);
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
            builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
            builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                private StringBuilder messages = new StringBuilder();
                private final int JSON_INDENT = 2;

                @Override
                public void log(String message) {
                    try {
                        if (message.startsWith("{") && message.endsWith("}")) {
                            JSONObject jsonObject = new JSONObject(message);
                            message = jsonObject.toString(JSON_INDENT);
                        } else if (message.startsWith("[") && message.endsWith("]")) {
                            JSONArray jsonArray = new JSONArray(message);
                            message = jsonArray.toString(JSON_INDENT);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    messages.append(message);
                    messages.append("\n");
                    if (message.startsWith("<-- END HTTP")) {
                        Log.i("ApiRequest",messages.toString());
                        messages.delete(0, messages.length());
                    }
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(interceptor);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }
}
