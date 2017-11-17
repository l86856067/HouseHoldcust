package android.app.wolf.household.application;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by lh on 2017/10/11.
 * <p/>
 * 功能作用：全局Activity
 * <p/>
 * 修改记录：
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setTranslucentStatus(true);
    }

    private void setTranslucentStatus(boolean b) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = getWindow();
            WindowManager.LayoutParams winparams = window.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (b){
                winparams.flags |= bits;
            }else {
                winparams.flags |= ~bits;
            }
            window.setAttributes(winparams);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        MobclickAgent.onPause(this);

    }
}
