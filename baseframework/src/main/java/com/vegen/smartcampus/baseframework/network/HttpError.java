package com.vegen.smartcampus.baseframework.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vegen.smartcampus.baseframework.BuildConfig;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import retrofit2.HttpException;


/**
 * Created by vegen on 2018/3/2.
 */
public class HttpError {

    public static String getErrorMessage(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            return throwable.getMessage();
        }
        if (throwable instanceof HttpException) {
            try {
                HttpException httpException = (HttpException) throwable;
                String errorMsg = httpException.response().errorBody().string();
                HashMap data = new Gson().fromJson(errorMsg, HashMap.class);
                if (data.containsKey("msg")) {
                    return (String) data.get("msg");
                } else {
                    return httpException.getMessage();
                }
            } catch (IOException e) {
                LogUtils.e(e.getMessage());
            } catch (JsonSyntaxException e) {
                LogUtils.e(e.getMessage());
                return throwable.getMessage();
            } catch (Exception e){
                return "请求错误";
            }
        }else if (throwable instanceof SocketTimeoutException ||
                throwable instanceof UnknownHostException){
            return "请检查网络是否连通";
        }else{
            return "请求错误";
        }
        return "请求错误";
    }

    public static int getErrorCode(Throwable throwable) {
        if (throwable instanceof HttpException) {
            try {
                HttpException httpException = (HttpException) throwable;
                String errorMsg = httpException.response().errorBody().string();
                HashMap data = new Gson().fromJson(errorMsg, HashMap.class);
                if (data.containsKey("msg")) {
                    return ((Double) data.get("code")).intValue();
                } else {
                    return -1;
                }
            } catch (IOException e) {
                LogUtils.e(e.getMessage());
            } catch (JsonSyntaxException e) {
                LogUtils.e(e.getMessage());
                return -1;
            } catch (Exception e){
                return -1;
            }
        }
        return -1;
    }

    public static boolean isForbiddenStatus(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            return httpException.code() == 403;
        }
        return false;
    }

}
