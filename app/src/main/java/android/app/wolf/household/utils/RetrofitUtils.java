package android.app.wolf.household.utils;

import android.app.wolf.household.http.httpconstant.HttpHost;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lh on 2017/10/16.
 * <p>
 * 功能作用： 用于获取Retrofit的单例
 * <p>
 * 修改记录：
 */

public class RetrofitUtils {

    private static Retrofit retrofit;

    //获取Retrofit单实例方法
    public static synchronized Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()//创建者
                    .baseUrl(HttpHost.getHttpHost())//添加BaseUrl
                    .addConverterFactory(GsonConverterFactory.create())//Gosn覆盖
                    .build();//创建
        }
        return retrofit;
    }

}
