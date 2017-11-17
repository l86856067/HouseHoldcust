package android.app.wolf.household.utils;

import android.app.wolf.household.application.MyApplication;
import android.content.Context;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lh on 2017/10/16.
 * <p>
 * 功能作用：  用于Toast消息的方法
 * <p>
 * 修改记录：
 */
public class ToastUtils {

    public static void showLongToast(String info){
        Toast.makeText(MyApplication.getInstance().getApplicationContext(), info, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(String info){
        Toast.makeText(MyApplication.getInstance().getApplicationContext(), info, Toast.LENGTH_SHORT).show();
    }

}
