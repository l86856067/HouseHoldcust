package android.app.wolf.household.view.activity;

import android.Manifest;
import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.utils.FragmentUtils;
import android.app.wolf.household.utils.NotificationsUtils;
import android.app.wolf.household.view.fragment.HistoryFragment;
import android.app.wolf.household.view.fragment.HomeFragment;
import android.app.wolf.household.view.fragment.UserFragment;
import android.app.wolf.household.view.myview.MyDialog;
import android.app.wolf.household.view.myview.NoticeDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView main_message;
    private ImageView main_btnHome;
    private ImageView main_btnHis;
    private ImageView main_btnUser;

    private HomeFragment homeFragment;
    private HistoryFragment historyFragment;
    private UserFragment userFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    SharedPreferences sharedPreferences;
    private final String YM_TYPE = "cust_id";


    String[] persission = new String[]{Manifest.permission.CALL_PHONE ,Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initNotice();

//        loadNoticeDialog();
        getPermission();

        initFragmentandShow();

        setListener();

    }

    private void initNotice() {
        PushAgent.getInstance(this).addExclusiveAlias(sharedPreferences.getInt("custId",0)+"", YM_TYPE , new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {

                Log.d("mytoken",b+";"+s);
            }
        });
    }

    private void loadNoticeDialog() {
        final NoticeDialog dialog = new NoticeDialog(this);
        dialog.setMessage("如果你无法简洁的表达你的想法，那只说明你还不够了解他。");
        dialog.setPostButton("我知道了", new NoticeDialog.OnpostButtonInterface() {
            @Override
            public void post() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getPermission() {
        List<String> mpersission = new ArrayList<>();

        for (int i = 0 ; i < persission.length ; i ++){
            if (ContextCompat.checkSelfPermission(this, persission[i]) != PackageManager.PERMISSION_GRANTED){
                mpersission.add(persission[i]);
            }
        }
        if (!mpersission.isEmpty()){
            String[] needPersisssion = mpersission.toArray(new String[mpersission.size()]);
            ActivityCompat.requestPermissions(this,needPersisssion,1);
        }

        if (! NotificationsUtils.isNotificationEnabled(this)){

            final MyDialog dialog = new MyDialog(this);
            dialog.setMessage("检测到您未开启应用通知，将不能使用来订单提醒功能，是否去开启通知？");
            dialog.setOnCancelListener("不开启", new MyDialog.OnCancelinterface() {
                @Override
                public void cancel() {
                    dialog.dismiss();
                }
            });
            dialog.setOnConfirmListener("开启", new MyDialog.OnConfirminterface() {
                @Override
                public void confirm() {
                    dialog.dismiss();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
                        // 进入设置系统应用权限界面
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
                        // 进入设置系统应用权限界面
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                }
            });
            dialog.show();


        }

    }

    private void initView() {
        main_message = (ImageView) findViewById(R.id.main_message);
        main_btnHome = (ImageView) findViewById(R.id.main_btnHome);
        main_btnHis = (ImageView) findViewById(R.id.main_btnHis);
        main_btnUser = (ImageView) findViewById(R.id.main_btnUser);

        sharedPreferences = getSharedPreferences("household",MODE_PRIVATE);
    }

    private void initFragmentandShow() {
        homeFragment = new HomeFragment();
        historyFragment = new HistoryFragment();
        userFragment = new UserFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_fragmentlayout,homeFragment)
                .add(R.id.main_fragmentlayout,historyFragment).hide(historyFragment)
                .add(R.id.main_fragmentlayout,userFragment).hide(userFragment)
                .commit();
    }

    private void setListener() {
        main_message.setOnClickListener(this);
        main_btnHome.setOnClickListener(this);
        main_btnHis.setOnClickListener(this);
        main_btnUser.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_message:
                Intent intent1 = new Intent(MainActivity.this,MessageActivity.class);
                startActivity(intent1);
                break;
            case R.id.main_btnHome:
                main_btnHome.setImageResource(R.drawable.main_homeyes);
                main_btnHis.setImageResource(R.drawable.main_his_no);
                main_btnUser.setImageResource(R.drawable.main_user_no);
                FragmentUtils.judgeToShow(fragmentManager,homeFragment);
                break;
            case R.id.main_btnHis:
                main_btnHome.setImageResource(R.drawable.main_home_no);
                main_btnHis.setImageResource(R.drawable.main_hisyes);
                main_btnUser.setImageResource(R.drawable.main_user_no);
                FragmentUtils.judgeToShow(fragmentManager,historyFragment);
                break;
            case R.id.main_btnUser:
                main_btnHome.setImageResource(R.drawable.main_home_no);
                main_btnHis.setImageResource(R.drawable.main_his_no);
                main_btnUser.setImageResource(R.drawable.main_useryes);
                FragmentUtils.judgeToShow(fragmentManager,userFragment);
                break;
        }
    }
}
