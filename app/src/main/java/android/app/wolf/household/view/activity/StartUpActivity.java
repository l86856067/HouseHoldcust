package android.app.wolf.household.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.bean.UpGradeBean;
import android.app.wolf.household.bean.UserCustInfoBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.app.wolf.household.view.myview.ChargeDialog;
import android.app.wolf.household.view.myview.MyDialog;
import android.app.wolf.household.view.myview.NoticeDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartUpActivity extends BaseActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isLogin;
    private int custId;

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
                case 0:
                    Intent intent = new Intent(StartUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        initData();

//        judgeStateToNext();

        checkVersion();

    }

    private void judgeStateToNext() {
        if (isLogin){
            custId = sharedPreferences.getInt("custId",0);
            HttpRequest getCustInfo = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
            getCustInfo.postIdtoUserCustInfo(custId).enqueue(new Callback<UserCustInfoBean>() {
                @Override
                public void onResponse(Call<UserCustInfoBean> call, Response<UserCustInfoBean> response) {
                    UserCustInfoBean body = response.body();
                    if (body != null){
                        if (body.getStatusCode().equals("200")){
                            UserCustInfoBean.DataBean data = body.getData();
                            editor.putFloat("money", data.getAvailMoney());
                            editor.putString("custName",data.getCustName());
                            editor.putString("custAddre",data.getAddress());
                            editor.putString("custScore",data.getCustScore());
                            editor.commit();

                            Intent intent = new Intent(StartUpActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                }

                @Override
                public void onFailure(Call<UserCustInfoBean> call, Throwable t) {
                    ToastUtils.showShortToast(t.getMessage());
                }
            });
        }else {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private void initData() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("下载中，请稍候...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences("household",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isLogin = sharedPreferences.getBoolean("isLogin",false);
    }




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
            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;

            HttpRequest upCodeRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
            upCodeRequest.postAppCodetoUpGrade(versionCode,11).enqueue(new Callback<UpGradeBean>() {
                @Override
                public void onResponse(Call<UpGradeBean> call, Response<UpGradeBean> response) {
                    UpGradeBean body = response.body();
                    if (body != null){
                        if (body.getStatusCode().equals("200")){
                            switch (body.getData().getUpdataType()){
                                case -1:
                                    judgeStateToNext();
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

        final NoticeDialog dialog = new NoticeDialog(this);
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

        final MyDialog myDialog = new MyDialog(this);
        myDialog.setTitle("检查更新");
        myDialog.setMessage("检测到可以更新的版本，是否更新");
        myDialog.setOnCancelListener("暂时不", new MyDialog.OnCancelinterface() {
            @Override
            public void cancel() {
                judgeStateToNext();
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
            uriForFile = FileProvider.getUriForFile(StartUpActivity.this, "android.app.wolf.household.fileprovider", file);
            intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        startActivity(intent);
        finish();

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
