package android.app.wolf.household.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.bean.AliPayBean;
import android.app.wolf.household.bean.ResultBean;
import android.app.wolf.household.bean.UserCustInfoBean;
import android.app.wolf.household.bean.WechatPayBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.app.wolf.household.view.myview.NoticeDialog;
import android.app.wolf.household.view.myview.PayBottomDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoneyActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MoneyActivity";

    private ImageView money_btnBack;
    private TextView money_custMoney;
    private TextView money_cangetMoney;
    private EditText money_inputMoney;
    private TextView money_btnExtract;
    private TextView money_btnRecharge;

    ProgressDialog loadDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private float money = 0;
    private boolean canRecharge = false;
    private String editInput = "";

    int custId = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Map<String, String> stringStringMap = (Map<String, String>) msg.obj;
                    Log.d("moneyActivity",stringStringMap.toString());

                    if (stringStringMap.get("resultStatus").equals("9000")){

                        ToastUtils.showShortToast("充值成功");

                        HttpRequest getCustInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                        getCustInfoRequest.postIdtoUserCustInfo(custId).enqueue(new retrofit2.Callback<UserCustInfoBean>() {
                            @Override
                            public void onResponse(Call<UserCustInfoBean> call, Response<UserCustInfoBean> response) {

                                UserCustInfoBean body = response.body();
                                if (body != null){
                                    if (body.getStatusCode().equals("200")){
                                        UserCustInfoBean.DataBean data = body.getData();
                                        editor = sharedPreferences.edit();
                                        editor.putFloat("money", data.getAvailMoney());
                                        editor.commit();

                                        EventBus.getDefault().post("success");

                                        setMoneyText();

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<UserCustInfoBean> call, Throwable t) {

                            }
                        });

                    }

                    break;
                case 2:

                    HttpRequest getCustInfoRequest2 = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                    getCustInfoRequest2.postIdtoUserCustInfo(custId).enqueue(new retrofit2.Callback<UserCustInfoBean>() {
                        @Override
                        public void onResponse(Call<UserCustInfoBean> call, Response<UserCustInfoBean> response) {
                            loadDialog.dismiss();
                            UserCustInfoBean body = response.body();
                            if (body != null){
                                if (body.getStatusCode().equals("200")){
                                    UserCustInfoBean.DataBean data = body.getData();
                                    editor = sharedPreferences.edit();
                                    editor.putFloat("money", data.getAvailMoney());
                                    editor.commit();

                                    EventBus.getDefault().post("success");

                                    final NoticeDialog noticeDialog = new NoticeDialog(MoneyActivity.this);
                                    noticeDialog.setMessage("提现请求成功，我们会尽快为您处理");
                                    noticeDialog.setPostButton("确定", new NoticeDialog.OnpostButtonInterface() {
                                        @Override
                                        public void post() {
                                            noticeDialog.dismiss();
                                        }
                                    });
                                    noticeDialog.show();

                                    setMoneyText();


                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserCustInfoBean> call, Throwable t) {
                            loadDialog.dismiss();
                        }
                    });

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        initView();

        setListener();

    }

    private void setListener() {
        money_btnBack.setOnClickListener(this);
        money_btnExtract.setOnClickListener(this);
        money_btnRecharge.setOnClickListener(this);

        money_inputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if (!input.isEmpty()){
                    double inputMoney = Double.parseDouble(input);
                    if (inputMoney > money){
                        money_cangetMoney.setText("输入金额超过余额");
                    }else {
                        money_cangetMoney.setText("可提现金额："+money+"元");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initView() {
        money_btnBack = (ImageView) findViewById(R.id.money_btnBack);
        money_custMoney = (TextView) findViewById(R.id.money_custMoney);
        money_cangetMoney = (TextView) findViewById(R.id.money_cangetMoney);
        money_inputMoney = (EditText) findViewById(R.id.money_inputMoney);
        money_btnExtract = (TextView) findViewById(R.id.money_btnExtract);
        money_btnRecharge = (TextView) findViewById(R.id.money_btnRecharge);
        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage("请稍等");
        loadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        money_inputMoney.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source.equals("0") && dest.toString().length() == 0){
                    return null;
                }

                if(source.equals(".") && dest.toString().length() == 0){
                    return "0.";
                }

                if(dest.toString().contains(".")){
                    int index = dest.toString().indexOf(".");
                    int length = dest.toString().substring(index).length();
                    if(length == 3){
                        return null;
                    }
                }
                return null;

            }
        }});

        sharedPreferences = getSharedPreferences("household",MODE_PRIVATE);
        custId = sharedPreferences.getInt("custId",0);

//        setMoneyText();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.money_btnBack:
                finish();
                break;
            case R.id.money_btnExtract:
                if (canClickButton()){
                    double tixianMoney = Double.parseDouble(editInput);
                    if (tixianMoney > money){
                        ToastUtils.showShortToast("提现金额超过余额");
                    }else {
                        loadDialog.show();
                        HttpRequest tixianRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                        tixianRequest.postMoneyToOutMoney(custId,tixianMoney).enqueue(new Callback<ResultBean>() {
                            @Override
                            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                                ResultBean body = response.body();
                                if (body != null){
                                    if (body.getStatusCode().equals("200")){
                                        handler.sendEmptyMessage(2);
                                    }else {
                                        loadDialog.dismiss();
                                        ToastUtils.showShortToast(body.getStatusDesc());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultBean> call, Throwable t) {
                                loadDialog.dismiss();
                                ToastUtils.showShortToast(t.getMessage());
                            }
                        });


                    }
                }
                break;
            case R.id.money_btnRecharge:
                if (canClickButton()){

                    final float chongzhiMoney = Float.parseFloat(editInput);
                    final PayBottomDialog dialog = new PayBottomDialog();
                    dialog.setMoneyText(chongzhiMoney+" 元");
                    dialog.setOnPayListener(new PayBottomDialog.OnPayinterface() {
                        @Override
                        public void pay(int type) {
                            switch (type){
                                case 0:
                                    ToastUtils.showShortToast("请选择支付方式");
                                    break;
                                case 1:


                    HttpRequest chongzhiRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                    chongzhiRequest.postIdandMoneytoChongzhi(custId,chongzhiMoney).enqueue(new Callback<AliPayBean>() {
                        @Override
                        public void onResponse(Call<AliPayBean> call, Response<AliPayBean> response) {
                            AliPayBean body = response.body();
                            if (body != null){
                                if (body.getStatusCode().equals("200")){
                                    AliPayBean.DataBean data = body.getData();
                                    final String body1 = data.getBody();

                                    new Thread(){
                                        @Override
                                        public void run() {
                                            super.run();

                                            PayTask aliPay = new PayTask(MoneyActivity.this);
                                            Map<String, String> stringStringMap = aliPay.payV2(body1, true);

                                            Message msg = new Message();
                                            msg.what = 1;
                                            msg.obj = stringStringMap;
                                            handler.sendMessage(msg);

                                        }
                                    }.start();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AliPayBean> call, Throwable t) {

                        }
                    });

                                    dialog.dismiss();
                                    break;
                                case 2:

                                    HttpRequest wechatRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);

                                    wechatRequest.postIdandMoneytowechatChongzhi(custId,chongzhiMoney).enqueue(new Callback<WechatPayBean>() {
                                        @Override
                                        public void onResponse(Call<WechatPayBean> call, Response<WechatPayBean> response) {
                                            WechatPayBean body = response.body();
                                            if (body != null){
                                                if (body.getStatusCode().equals("200")){
                                                    WechatPayBean.DataBean data = body.getData();

                                                    IWXAPI api = WXAPIFactory.createWXAPI(MoneyActivity.this,"wx62834e4cb5377fbb");
                                                    api.registerApp("wx62834e4cb5377fbb");

                                                    PayReq request = new PayReq();

                                                    request.appId = data.getAppid();

                                                    request.partnerId = data.getPartnerid();

                                                    request.prepayId= data.getPrepayid();

                                                    request.packageValue = data.getPackageX();

                                                    request.nonceStr= data.getNoncestr();

                                                    request.timeStamp= data.getTimestamp();

                                                    request.sign= data.getPaySign();

                                                    boolean b = api.sendReq(request);

                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<WechatPayBean> call, Throwable t) {

                                        }
                                    });


                                    dialog.dismiss();
                                    break;
                            }
                        }
                    });
                    dialog.show(getSupportFragmentManager(),"dialog");

                }
                break;
        }
    }

    public void setMoneyText(){

        money = sharedPreferences.getFloat("money", 0);
        float ddd = money;
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String str = format.format(ddd);
        money_custMoney.setText(str);
        money_cangetMoney.setText("可提现金额："+str+"元");
        money_inputMoney.setText("");

    }

    public boolean canClickButton(){
        editInput = money_inputMoney.getText().toString();
        if (editInput.isEmpty()){
            ToastUtils.showShortToast("请输入金额");
            return false;
        }else {
            return true;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

//        ToastUtils.showShortToast("onResume");
        HttpRequest getCustInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        getCustInfoRequest.postIdtoUserCustInfo(custId).enqueue(new retrofit2.Callback<UserCustInfoBean>() {
            @Override
            public void onResponse(Call<UserCustInfoBean> call, Response<UserCustInfoBean> response) {

                UserCustInfoBean body = response.body();
                if (body != null){
                    if (body.getStatusCode().equals("200")){
                        UserCustInfoBean.DataBean data = body.getData();
                        editor = sharedPreferences.edit();
                        editor.putFloat("money", data.getAvailMoney());
                        editor.commit();

                        EventBus.getDefault().post("success");

                        setMoneyText();

                    }
                }
            }

            @Override
            public void onFailure(Call<UserCustInfoBean> call, Throwable t) {

            }
        });
    }
}
