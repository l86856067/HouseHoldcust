package android.app.wolf.household.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.bean.MessageEvent;
import android.app.wolf.household.bean.OrderInfoBean;
import android.app.wolf.household.bean.SimpleReturnBean;
import android.app.wolf.household.http.httpconstant.HttpHost;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.app.wolf.household.view.myview.ChargeDialog;
import android.app.wolf.household.view.myview.ResultFailDialog;
import android.app.wolf.household.view.myview.ResultSuccessDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderNowActivity extends BaseActivity {

    private static final String TAG = "OrderNowActivity";

    private ImageView ordernew_btnBack;

    private LinearLayout orderNow_layout1;
    private LinearLayout orderNow_layout2;
    private LinearLayout orderNow_layout3;
    private LinearLayout orderNow_layout4;
    private LinearLayout orderNow_layout5;
    private LinearLayout orderNow_layout6;
    private LinearLayout orderNow_layout7;
    private LinearLayout orderNow_layout8;
    private LinearLayout orderNow_layout9;
    private LinearLayout orderNow_layout10;

    private TextView ordernow_serviceName;
    private TextView ordernow_addre;
    private TextView ordernow_customerInfo;
    private TextView ordernow_serviceMoney;
    private TextView ordernow_serviceTime;
    private TextView ordernow_serviceLength;
    private TextView ordernow_serviceNumber;
    private TextView ordernow_acreage;
    private TextView ordernow_frequency;
    private TextView ordernew_remarks;
    private TextView ordernew_btnRob;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private double money = 0;               //账户余额
    private int orderStatus = 0;           //订单状态
    private int custId = 0;                 //商户id
    private int orderId = 0;                //订单id
    private int serviceBail = 0;           //抢单金额
    private int serviceItemid = 0;         //订单类型

    private String serviceName = "";       //服务名称
    private String address = "";           //服务地址
    private String userName = "";          //客户姓名
    private String userTele = "";          //客户电话
    private float serviceMoney = 0;      //服务金额
    private String serviceTime = "";     //上门时间
    private int serviceLength = 2;       //服务时长
    private int serviceNum = 1;          //阿姨数量
    private int acreage = 0;             //房间面积
    private int frequency = 0;          //服务频率
    private String serviceRemarks = "";      //客户备注

    private ProgressDialog progressDialog;
    private ProgressDialog loadingDialog;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    ToastUtils.showShortToast("接受到了");

                    Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(HttpHost.getHttpHost())
                                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                                .build();
                        HttpRequest httpRequest = retrofit.create(HttpRequest.class);
                    httpRequest.postOrderIdtoRobOrder(orderId,custId).enqueue(new retrofit2.Callback<SimpleReturnBean>() {
                        @Override
                        public void onResponse(Call<SimpleReturnBean> call, Response<SimpleReturnBean> response) {
                            SimpleReturnBean body = response.body();
                            if (body.equals("200")){
                                ToastUtils.showShortToast(body.getStatusDesc());
                                EventBus.getDefault().post(new MessageEvent("sss"));
                            }else {
                                ToastUtils.showShortToast(body.getStatusDesc());
                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleReturnBean> call, Throwable t) {

                        }
                    });

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_now);

        initView();

        getOrderInfo();

        setListener();

    }

    private void showData() {

        ordernow_serviceName.setText(serviceName);
        String[] adds = address.split("[|]");
        Log.d(TAG,address);
        Log.d(TAG,adds.length+"");

        ordernow_addre.setText(adds[0] + adds[1] + adds[2]);
        ordernow_serviceMoney.setText(serviceMoney+"元");
        ordernow_serviceTime.setText(serviceTime);
        ordernow_serviceLength.setText(serviceLength+"个小时");
        ordernow_serviceNumber.setText(serviceNum+"个");
        ordernow_acreage.setText(acreage + " m²");
        ordernow_frequency.setText(frequency + "次");
        ordernew_remarks.setText(serviceRemarks);

        switch (serviceItemid){
            case 1:
            case 2:
                orderNow_layout4.setVisibility(View.VISIBLE);
                orderNow_layout5.setVisibility(View.VISIBLE);
                orderNow_layout6.setVisibility(View.VISIBLE);
                orderNow_layout7.setVisibility(View.VISIBLE);
                break;
            case 3:
                orderNow_layout9.setVisibility(View.VISIBLE);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                break;
            case 10:
                orderNow_layout4.setVisibility(View.VISIBLE);
                orderNow_layout5.setVisibility(View.VISIBLE);
                orderNow_layout8.setVisibility(View.VISIBLE);
                break;
        }

        switch (orderStatus){
            case 0:
                ordernew_btnRob.setText("加载错误");
                ordernew_btnRob.setOnClickListener(null);
                break;
            case 3:
                ordernew_btnRob.setText("立即接单");
                break;
            case 5:
            case 6:
            case 10:
            case 11:
            case 12:
                ordernew_btnRob.setText("此单已被接");
                ordernew_btnRob.setOnClickListener(null);
                break;
        }

    }

    private void getOrderInfo() {
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId",0);

        loadingDialog.show();
        HttpRequest getOrderInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        getOrderInfoRequest.postOrderIdtoGetOrderInfo(orderId).enqueue(new Callback<OrderInfoBean>() {
            @Override
            public void onResponse(Call<OrderInfoBean> call, Response<OrderInfoBean> response) {
                loadingDialog.dismiss();
                OrderInfoBean body = response.body();
                if (body != null){

                    if (body.getStatusCode().equals("200")){
                        OrderInfoBean.DataBean data = body.getData();

                        orderStatus = data.getOrderStatus();
                        serviceItemid = data.getServiceItemId();
                        serviceName = data.getServiceName();
                        address = data.getAddress();
                        userName = data.getUsername();
                        userTele = data.getUserTele();
                        serviceMoney = data.getMoney() - data.getServiceBail();
                        serviceTime = data.getServiceStarttime();
                        serviceLength = data.getServiceTime();
                        serviceNum = data.getCustEmplNum();
                        acreage = data.getServiceParam();
                        frequency = data.getServiceEncy();
                        serviceRemarks = data.getServiceDesc();
                        serviceBail = data.getServiceBail();

                        showData();

                    }else {
                        ToastUtils.showShortToast(body.getStatusDesc());
                    }

                }
            }

            @Override
            public void onFailure(Call<OrderInfoBean> call, Throwable t) {
                loadingDialog.dismiss();
                ToastUtils.showShortToast(t.getMessage());
            }
        });

    }

    private void setListener() {
        ordernew_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ordernew_btnRob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (serviceBail == 0){  //如果抢这个单不需要支付抢单费，直接抢单

                    final ChargeDialog dialog = new ChargeDialog(OrderNowActivity.this);
                    dialog.setMessage("确定接此订单吗？");
                    dialog.setOnCancelListener("取消", new ChargeDialog.OnCancelinterface() {
                        @Override
                        public void cancel() {
                            dialog.dismiss();
                        }
                    });
                    dialog.setOnConfirmListener("确定", new ChargeDialog.OnConfirminterface() {
                        @Override
                        public void confirm() {
                            dialog.dismiss();

                            progressDialog.show();

                            ordernew_btnRob.setText("已接单");
                            ordernew_btnRob.setOnClickListener(null);

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(HttpHost.getHttpHost())
                                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                                    .build();
                            HttpRequest httpRequest = retrofit.create(HttpRequest.class);
                            httpRequest.postOrderIdtoRobOrder(orderId,custId).enqueue(new Callback<SimpleReturnBean>() {
                                @Override
                                public void onResponse(Call<SimpleReturnBean> call, Response<SimpleReturnBean> response) {
                                    SimpleReturnBean body = response.body();
                                    progressDialog.dismiss();
                                    if (body != null){
                                        if (body.getStatusCode().equals("200")){

                                            EventBus.getDefault().post("success");

                                            final ResultSuccessDialog dialog = new ResultSuccessDialog(OrderNowActivity.this);
                                            dialog.setMessage("接单成功");
                                            dialog.setPostButton("确定", new ResultSuccessDialog.OnpostButtonInterface() {
                                                @Override
                                                public void post() {
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog.show();
                                        }else {
                                            final ResultFailDialog dialog = new ResultFailDialog(OrderNowActivity.this);
                                            dialog.setMessage(body.getStatusDesc());
                                            dialog.setPostButton("确定", new ResultFailDialog.OnpostButtonInterface() {
                                                @Override
                                                public void post() {
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog.show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<SimpleReturnBean> call, Throwable t) {
                                    progressDialog.dismiss();
                                    ToastUtils.showShortToast(t.getMessage());
                                }
                            });

                        }
                    });
                    dialog.show();

                }else {  //如果抢这个单需要支付抢单费，扣费以后再抢单

                    money = sharedPreferences.getFloat("money",0);

                    if ((money - serviceBail) >= 0){  //如果剩下的金额足以支付抢单费，去抢单

                        final ChargeDialog dialog = new ChargeDialog(OrderNowActivity.this);
                        dialog.setMessage("接此订单需要收费"+serviceBail+"元，确定支付吗？");
                        dialog.setOnCancelListener("取消", new ChargeDialog.OnCancelinterface() {
                            @Override
                            public void cancel() {
                                dialog.dismiss();
                            }
                        });
                        dialog.setOnConfirmListener("确定", new ChargeDialog.OnConfirminterface() {
                            @Override
                            public void confirm() {
                                dialog.dismiss();

                                progressDialog.show();

                                ordernew_btnRob.setText("已接单");
                                ordernew_btnRob.setOnClickListener(null);

                                final double newMoney = money - serviceBail;

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(HttpHost.getHttpHost())
                                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                                        .build();
                                HttpRequest httpRequest = retrofit.create(HttpRequest.class);
                                httpRequest.postOrderIdtoRobOrder(orderId,custId).enqueue(new Callback<SimpleReturnBean>() {
                                    @Override
                                    public void onResponse(Call<SimpleReturnBean> call, Response<SimpleReturnBean> response) {
                                        SimpleReturnBean body = response.body();
                                        progressDialog.dismiss();
                                        if (body != null){
                                            if (body.getStatusCode().equals("200")){

                                                editor = sharedPreferences.edit();
                                                editor.putFloat("money", (float) newMoney);
                                                editor.commit();

                                                EventBus.getDefault().post("success");

                                                final ResultSuccessDialog dialog = new ResultSuccessDialog(OrderNowActivity.this);
                                                dialog.setMessage("接单成功");
                                                dialog.setPostButton("确定", new ResultSuccessDialog.OnpostButtonInterface() {
                                                    @Override
                                                    public void post() {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                dialog.show();

                                            }else {
                                                final ResultSuccessDialog dialog = new ResultSuccessDialog(OrderNowActivity.this);
                                                dialog.setMessage(body.getStatusDesc());
                                                dialog.setPostButton("确定", new ResultSuccessDialog.OnpostButtonInterface() {
                                                    @Override
                                                    public void post() {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                dialog.show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SimpleReturnBean> call, Throwable t) {
                                        progressDialog.dismiss();
                                        ToastUtils.showShortToast(t.getMessage());
                                    }
                                });

                            }
                        });
                        dialog.show();

                    }else {  //如果剩下的钱不足以支付抢单费，去充值

                        final ChargeDialog dialog = new ChargeDialog(OrderNowActivity.this);
                        dialog.setMessage("您的账户余额不足，请充值！");
                        dialog.setOnCancelListener("取消", new ChargeDialog.OnCancelinterface() {
                            @Override
                            public void cancel() {
                                dialog.dismiss();
                            }
                        });
                        dialog.setOnConfirmListener("去充值", new ChargeDialog.OnConfirminterface() {
                            @Override
                            public void confirm() {
                                Intent intent = new Intent(OrderNowActivity.this, MoneyActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }

                }

            }
        });
    }

    private void initView() {
        ordernew_btnBack = (ImageView) findViewById(R.id.ordernew_btnBack);

        orderNow_layout1 = (LinearLayout) findViewById(R.id.orderNow_layout1);
        orderNow_layout2 = (LinearLayout) findViewById(R.id.orderNow_layout2);
        orderNow_layout3 = (LinearLayout) findViewById(R.id.orderNow_layout3);
        orderNow_layout4 = (LinearLayout) findViewById(R.id.orderNow_layout4);
        orderNow_layout5 = (LinearLayout) findViewById(R.id.orderNow_layout5);
        orderNow_layout6 = (LinearLayout) findViewById(R.id.orderNow_layout6);
        orderNow_layout7 = (LinearLayout) findViewById(R.id.orderNow_layout7);
        orderNow_layout8 = (LinearLayout) findViewById(R.id.orderNow_layout8);
        orderNow_layout9 = (LinearLayout) findViewById(R.id.orderNow_layout9);
        orderNow_layout10 = (LinearLayout) findViewById(R.id.orderNow_layout10);

        ordernew_btnRob = (TextView) findViewById(R.id.ordernew_btnRob);
        ordernow_serviceName = (TextView) findViewById(R.id.ordernow_serviceName);
        ordernow_addre = (TextView) findViewById(R.id.ordernow_addre);
        ordernow_customerInfo = (TextView) findViewById(R.id.ordernow_customerInfo);
        ordernow_serviceMoney = (TextView) findViewById(R.id.ordernow_serviceMoney);
        ordernow_serviceTime = (TextView) findViewById(R.id.ordernow_serviceTime);
        ordernow_serviceLength = (TextView) findViewById(R.id.ordernow_serviceLength);
        ordernow_serviceNumber = (TextView) findViewById(R.id.ordernow_serviceNumber);
        ordernow_acreage = (TextView) findViewById(R.id.ordernow_acreage);
        ordernow_frequency = (TextView) findViewById(R.id.ordernow_frequency);
        ordernew_remarks = (TextView) findViewById(R.id.ordernow_remarks);

        sharedPreferences = getSharedPreferences("household",MODE_PRIVATE);
        custId = sharedPreferences.getInt("custId",0);

        progressDialog = new ProgressDialog(this);
        loadingDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("努力抢单中");
        loadingDialog.setMessage("加载中");
    }
}
