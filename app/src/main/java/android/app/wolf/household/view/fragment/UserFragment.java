package android.app.wolf.household.view.fragment;

import android.app.ProgressDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.bean.MessageEvent;
import android.app.wolf.household.bean.UpGradeBean;
import android.app.wolf.household.bean.UserCustInfoBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.app.wolf.household.view.activity.AboutActivity;
import android.app.wolf.household.view.activity.FlowActivity;
import android.app.wolf.household.view.activity.MainActivity;
import android.app.wolf.household.view.activity.MerchantActivity;
import android.app.wolf.household.view.activity.MoneyActivity;
import android.app.wolf.household.view.myview.CallDialog;
import android.app.wolf.household.view.myview.ChargeDialog;
import android.app.wolf.household.view.myview.MyDialog;
import android.app.wolf.household.view.myview.NoticeDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 刘辉 on 2017/9/26.
 * <p/>
 * 功能作用：商户端商户信息的fragment
 * <p/>
 * 修改记录：
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private SwipeRefreshLayout userfragment_refresh;
    private RelativeLayout userfragment_info;     //商户信息
    private RelativeLayout userfragment_apply;    //申请提现
    private RelativeLayout userfragment_flow;     //资金流水
    private RelativeLayout userfragment_service;  //联系客服
    private RelativeLayout userfragment_about;    //关于我们
    private RelativeLayout userfragment_inspect;  //检查新版
    private TextView userfragment_money;
    private TextView userfragment_custScore;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int custId = 0;
    private float money = 0;
    private String custScore = "";

    ProgressDialog upDialog;


    private static final int DOWNLOADING = 11;
    private static final int DOWNLOADEND = 2;
    private static final String APK_PATH = "sdcard/household";
    private static final String fileName = "household.apk";
    private String upUrl = "";
    private int downloaded = 0;
    private ProgressDialog progressDialog;
    private Uri fileUri;
    private String versionName = "";
    private int versionCode = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DOWNLOADING:
                    progressDialog.setProgress(downloaded);
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("userfragment","onCreate");
//        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user,container,false);

        initView(view);

        showView();

        setListener();

        EventBus.getDefault().register(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userfragment_info:  //点击商户信息
                Intent intent1 = new Intent(getActivity(), MerchantActivity.class);
                startActivity(intent1);
                break;
            case R.id.userfragment_apply:  //点击申请提现
                Intent intent2 = new Intent(getActivity(), MoneyActivity.class);
                startActivity(intent2);
                break;
            case R.id.userfragment_flow:  //点击资金流水
                Intent intent3 = new Intent(getActivity(), FlowActivity.class);
                startActivity(intent3);
                break;
            case R.id.userfragment_service:  //点击联系客服


                final CallDialog dialog = new CallDialog(getActivity());
                dialog.setMessage("15000515095");
                dialog.setOnCancelButtoninterface("取消", new CallDialog.onCancelButtoninterface() {
                    @Override
                    public void cancel() {
                        dialog.dismiss();
                    }
                });
                dialog.setOnNextButtoninterface("拨打", new CallDialog.onNextButtoninterface() {
                    @Override
                    public void call() {
                        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"15000515095"));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.show();


                break;
            case R.id.userfragment_about:  //点击关于我们

                Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                startActivity(aboutIntent);

                break;
            case R.id.userfragment_inspect:  //点击检查新版

                upDialog.show();

                checkVersion();

                break;
        }
    }

    private void setListener() {

        userfragment_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpRequest getCustInfo = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                getCustInfo.postIdtoUserCustInfo(custId).enqueue(new Callback<UserCustInfoBean>() {
                    @Override
                    public void onResponse(Call<UserCustInfoBean> call, Response<UserCustInfoBean> response) {
                        UserCustInfoBean body = response.body();
                        if (body != null){
                            if (body.getStatusCode().equals("200")){
                                userfragment_refresh.setRefreshing(false);
                                UserCustInfoBean.DataBean data = body.getData();
                                editor = sharedPreferences.edit();
                                editor.putFloat("money", data.getAvailMoney());
                                editor.putString("custName",data.getCustName());
                                editor.putString("custScore",data.getCustScore());
                                editor.commit();

                                showView();

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<UserCustInfoBean> call, Throwable t) {
                        ToastUtils.showShortToast(t.getMessage());
                    }
                });
            }
        });

        userfragment_info.setOnClickListener(this);
        userfragment_apply.setOnClickListener(this);
        userfragment_flow.setOnClickListener(this);
        userfragment_service.setOnClickListener(this);
        userfragment_about.setOnClickListener(this);
        userfragment_inspect.setOnClickListener(this);
    }


    private void showView() {
        money = sharedPreferences.getFloat("money",0);
        custScore = sharedPreferences.getString("custScore","");

        userfragment_money.setText(money+"");
        userfragment_custScore.setText("商户评分："+custScore);
    }

    private void initView(View view) {
        userfragment_refresh = (SwipeRefreshLayout) view.findViewById(R.id.userfragment_refresh);
        userfragment_info = (RelativeLayout) view.findViewById(R.id.userfragment_info);
        userfragment_apply = (RelativeLayout) view.findViewById(R.id.userfragment_apply);
        userfragment_flow = (RelativeLayout) view.findViewById(R.id.userfragment_flow);
        userfragment_service = (RelativeLayout) view.findViewById(R.id.userfragment_service);
        userfragment_about = (RelativeLayout) view.findViewById(R.id.userfragment_about);
        userfragment_inspect = (RelativeLayout) view.findViewById(R.id.userfragment_inspect);
        userfragment_custScore = (TextView) view.findViewById(R.id.userfragment_custScore);
        userfragment_money = (TextView) view.findViewById(R.id.userfragment_money);

        upDialog = new ProgressDialog(getActivity());
        upDialog.setMessage("检查更新");
        upDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("下载中，请稍候...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);

        sharedPreferences = getActivity().getSharedPreferences("household", Context.MODE_PRIVATE);
        custId = sharedPreferences.getInt("custId",0);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void flahView(String str){
        Log.d("userfragment",str);
        showView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("refreuserfragment","onDestroy:");
        EventBus.getDefault().unregister(this);
    }


    /*==========================================================*/


    /**
     * 删除shangmenle文件夹及里面的内容
     */
    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 是否是文件夹，处理目录
                    File files[] = file.listFiles();//获取当前目录下的所有文件和文件夹
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /*
    *  检查更新
    * */
    private void checkVersion() {

        deleteFolderFile(APK_PATH,true);

        try {
            PackageManager pm = getActivity().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;

            HttpRequest upCodeRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
            upCodeRequest.postAppCodetoUpGrade(versionCode,11).enqueue(new Callback<UpGradeBean>() {
                @Override
                public void onResponse(Call<UpGradeBean> call, Response<UpGradeBean> response) {
                    upDialog.dismiss();
                    UpGradeBean body = response.body();
                    if (body != null){
                        if (body.getStatusCode().equals("200")){
                            switch (body.getData().getUpdataType()){
                                case -1:
                                    ToastUtils.showShortToast("没有检测到更新");
                                    break;
                                case 0:
                                    upUrl = body.getData().getDownloadUrl();
                                    myDialogNotMust();
                                    break;
                                case 1:
                                    upUrl = body.getData().getDownloadUrl();
                                    myDialog();
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<UpGradeBean> call, Throwable t) {
                    upDialog.dismiss();
                    ToastUtils.showShortToast(t.getMessage());
                }
            });

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * 有版本需要强制更新的dialog
     */
    protected void myDialog() {

        final NoticeDialog dialog = new NoticeDialog(getActivity());
        dialog.setMessage("版本需要立即更新，请更新");
        dialog.setPostButton("更新", new NoticeDialog.OnpostButtonInterface() {
            @Override
            public void post() {
                upApp();
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    /**
     * 有版本不需要强制更新的dialog
     */
    protected void myDialogNotMust() {

        final MyDialog myDialog = new MyDialog(getActivity());
        myDialog.setTitle("检查更新");
        myDialog.setMessage("检测到可以更新的版本，是否更新");
        myDialog.setOnCancelListener("暂时不", new MyDialog.OnCancelinterface() {
            @Override
            public void cancel() {
                myDialog.dismiss();
            }
        });
        myDialog.setOnConfirmListener("立即更新", new MyDialog.OnConfirminterface() {
            @Override
            public void confirm() {
                upApp();
                myDialog.dismiss();
            }
        });
        myDialog.show();

    }

    protected void upApp(){
        Log.d("MainActivity","upApp");
        progressDialog.show();
        if (!upUrl.equals("")) {
            new Thread() {
                public void run() {
                    openApk(downLoadFile(upUrl));
                }
            }.start();
        }
    }

    /*
    *  打开APK的代码
    * */
    private void openApk(File file){
        progressDialog.dismiss();
        Log.d("MainActivity", "openApk"+file.getName());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uriForFile;
        if (Build.VERSION.SDK_INT >= 24){
            uriForFile = FileProvider.getUriForFile(getActivity(), "android.app.wolf.household.fileprovider", file);
            intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        startActivity(intent);
        getActivity().finish();

    }

    protected File downLoadFile(String httpUrl) {
        Log.d("MainActivity", "downLoadFile");


        File tmpFile = new File(APK_PATH);
        if (!tmpFile.exists()) {//如果
            tmpFile.mkdir();
        }
        final File file = new File(APK_PATH + File.separator + fileName);
        long fileLength = 0L;

        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[256];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {
                    ToastUtils.showShortToast("连接超时");
                } else {
                    fileLength = conn.getContentLength();
                    int fileDownload = 0;

                    while (true) {
                        if (is != null) {

                            Message msg = new Message();
                            msg.what = DOWNLOADING;

                            int numRead = is.read(buf);
                            fileDownload = fileDownload + numRead;
                            downloaded = (int) (fileDownload * 100 / fileLength);
                            handler.sendMessage(msg);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }

                        } else {
                            break;
                        }

                    }
                }
                conn.disconnect();
                fos.close();
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return file;

    }



}
