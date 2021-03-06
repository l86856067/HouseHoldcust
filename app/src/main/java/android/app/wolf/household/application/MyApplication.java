package android.app.wolf.household.application;

import android.app.Application;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.entity.Alias;

/**
 * Created by lh on 2017/10/16.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance(){
        if (instance == null){
            return instance;
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        NotificationManagerCompat.from(this).areNotificationsEnabled();

        PushAgent mPushAgent = PushAgent.getInstance(this);    //注册推送服务，每次调用register方法都会回调该接口

        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("mytoken",deviceToken);

            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

    }
}
