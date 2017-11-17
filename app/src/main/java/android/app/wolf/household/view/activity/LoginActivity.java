package android.app.wolf.household.view.activity;

import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.bean.LoginResultBean;
import android.app.wolf.household.http.httpconstant.HttpHost;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private static String TAG = "LoginActivity";

    private EditText login_edit_username;                 // 用户名的输入框
    private EditText login_edit_password;                 // 密码的输入框
    private Button login_btnLogin;                  // 登录按钮

    private String tele = "";         // 电话号，用户登录时候的用户名
    private String password = "";     // 密码，用户登录时候的密码

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        setListener();

    }

    /*
    *  给控件设置监听
    * */
    private void setListener() {

        /*
        *  给登录按钮设置点击监听
        * */
        login_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"点击按钮");
                tologin();
            }
        });
    }

    /*
    *  点击登录按钮时调用的方法
    * */
    private void tologin() {
        if (teleIsRight()){

            Log.d(TAG,"开始网络请求");

            HttpRequest loginRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
            loginRequest.getToLogin(tele,password).enqueue(new Callback<LoginResultBean>() {
                @Override
                public void onResponse(Call<LoginResultBean> call, Response<LoginResultBean> response) {

                    LoginResultBean body = response.body();
                    if (body.getStatusCode().equals("200")){
                        ToastUtils.showShortToast(body.getStatusDesc());
                        LoginResultBean.DataBean.CustAuthInfoBean custAuthInfo = body.getData().getCustAuthInfo();

                        editor.putInt("custId",custAuthInfo.getId());
                        editor.putFloat("money",  custAuthInfo.getAvailMoney());
                        editor.putString("custName",custAuthInfo.getCustName());
                        editor.putString("custScore",custAuthInfo.getCustScore());
                        editor.putBoolean("isLogin",true);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        ToastUtils.showShortToast(body.getStatusDesc());
                    }
                }

                @Override
                public void onFailure(Call<LoginResultBean> call, Throwable t) {
                    ToastUtils.showShortToast(t.getMessage());
                }
            });

        }
    }

    private boolean teleIsRight() {
        tele = login_edit_username.getText().toString();
        password = login_edit_password.getText().toString();

        if (tele.isEmpty()){
            ToastUtils.showShortToast("请输入手机号");
            return false;
        }else if (password.isEmpty()){

            ToastUtils.showShortToast("请输入密码");
            return false;
        }else if (tele.length() != 11){
            ToastUtils.showShortToast("手机号输入有误");
            return false;
        }else {
            return true;
        }
    }

    /*
    *  初始化控件
    * */
    private void initView() {
        login_edit_username = (EditText) findViewById(R.id.login_edit_username);
        login_edit_password = (EditText) findViewById(R.id.login_edit_password);
        login_btnLogin = (Button) findViewById(R.id.login_btnLogin);

        sharedPreferences = getSharedPreferences("household",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
}
