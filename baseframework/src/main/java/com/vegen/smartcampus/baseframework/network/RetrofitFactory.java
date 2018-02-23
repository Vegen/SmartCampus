package com.vegen.smartcampus.baseframework.network;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.vegen.smartcampus.baseframework.network.type.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by vegen on 16-3-25.
 */
public class RetrofitFactory {

    private static Converter.Factory gsonConverterFactory;
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static final Map<String, Object> mRetrofitServiceCache = new HashMap<>();

    public static Converter.Factory getGsonConverterFactory() {
        if (gsonConverterFactory == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                    .registerTypeAdapter(Double.class, new DoubleTypeAdapter())
                    .registerTypeAdapter(Float.class, new FloatTypeAdapter())
                    .registerTypeAdapter(int.class, new IntegerTypeAdapter())
                    .registerTypeAdapter(float.class, new FloatTypeAdapter())
                    .registerTypeAdapter(double.class, new DoubleTypeAdapter())
                    .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        @Override
                        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            String value = json.getAsString();
                            if (TextUtils.isEmpty(value)) {
                                return null;
                            }
                            try {
                                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    })
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            gsonConverterFactory = GsonConverterFactory.create(gson);
        }
        return gsonConverterFactory;
    }

    public static <T> T obtainRetrofitService(String url, Class<T> service, OkHttpClient client) {
        T retrofitService;
        synchronized (mRetrofitServiceCache) {
            retrofitService = (T) mRetrofitServiceCache.get(url);
            if (retrofitService == null) {
                retrofitService = provideRetrofit(url, client).create(service);
                mRetrofitServiceCache.put(service.getName(), retrofitService);
            }
        }
        return retrofitService;
    }

    private static Retrofit provideRetrofit(String httpUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(httpUrl)   //域名
                .client(client)     //设置okhttp
                .addCallAdapterFactory(rxJavaCallAdapterFactory)    //使用rxjava
                .addConverterFactory(getGsonConverterFactory())     //使用GSON
                .build();
    }
}
