package android.app.wolf.household.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.bean.OrderInfoBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHisActivity extends BaseActivity {

    private final String TAG = "OrderHisActivity";

    private ImageView orderhis_btnBack;

    private RelativeLayout orderhis_layout4;
    private LinearLayout orderhis_layout5;
    private LinearLayout orderhis_layout6;
    private LinearLayout orderhis_layout7;
    private LinearLayout orderhis_layout8;
    private LinearLayout orderhis_layout9;
    private LinearLayout orderhis_layout11;
    private RelativeLayout orderhis_layout12;

    private TextView orderhis_layout1_serviceName;
    private TextView orderhis_layout2_addre;
    private TextView orderhis_layout3_customerInfo;
    private TextView orderhis_layout4_serviceMoney;
    private TextView orderhis_serviceStartTime;
    private TextView orderhis_serviceLength;
    private TextView orderhis_serviceNumber;
    private TextView orderhis_acreage;
    private TextView orderhis_frequency;
    private TextView orderhis_remarks;
    private TextView orderhis_servicePeople;
    private RatingBar orderhis_layout12_ratingStar;
    private TextView orderhis_evaluate;
    private TextView orderhis_orderStatu;


    int serviceItemid = 0;
    int oid = 0;
    int serviceStatu = 0;

    String projectName = "";
    String projectAddress = "";
    String userName = "";
    String userTele = "";
    float serviceMoney = 0;
    String serviceStartTime = "";
    int serviceLength = 1;
    int serviceNum = 1;
    int acreage = 0;
    int frequency = 0;
    String remarks = "";
    String serviceName = "";
    String serviceTele = "";
    String evaluate = "";
    float evaluateFraction = 0;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_his);

        initView();

        getData();

        setListener();
    }

    private void loadData() {

        orderhis_layout1_serviceName.setText(projectName);

        String[] adds = projectAddress.split("[|]");
        orderhis_layout2_addre.setText(adds[0] + adds[1] + adds[2] + adds[3]);
        orderhis_layout3_customerInfo.setText(userName + "    " + userTele);
        orderhis_layout4_serviceMoney.setText(serviceMoney + "元");
        orderhis_serviceStartTime.setText(serviceStartTime);
        orderhis_serviceLength.setText(serviceLength + "个小时");
        orderhis_serviceNumber.setText(serviceNum + "个");
        orderhis_acreage.setText(acreage + " m²");
        orderhis_frequency.setText(frequency + "次");
        orderhis_remarks.setText(remarks);
        orderhis_servicePeople.setText(serviceName + "    " + serviceTele);
        orderhis_layout12_ratingStar.setRating(evaluateFraction);
        orderhis_evaluate.setText(evaluate);

        switch (serviceItemid){
            case 1:
            case 2:
                orderhis_layout4.setVisibility(View.VISIBLE);
                orderhis_layout5.setVisibility(View.VISIBLE);
                orderhis_layout6.setVisibility(View.VISIBLE);
                orderhis_layout7.setVisibility(View.VISIBLE);
                break;
            case 3:
                orderhis_layout9.setVisibility(View.VISIBLE);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                break;
            case 10:
                orderhis_layout4.setVisibility(View.VISIBLE);
                orderhis_layout5.setVisibility(View.VISIBLE);
                orderhis_layout8.setVisibility(View.VISIBLE);
                break;
        }

        switch (serviceStatu){
            case 3:
                orderhis_orderStatu.setText("等待沟通");
                break;
            case 5:
                orderhis_orderStatu.setText("订单取消");
                break;
            case 6:
                orderhis_orderStatu.setText("已接单");
                break;
            case 10:
                orderhis_layout11.setVisibility(View.VISIBLE);
                orderhis_orderStatu.setText("服务中");
                break;
            case 11:
                orderhis_layout11.setVisibility(View.VISIBLE);
                orderhis_orderStatu.setText("服务完成");
                break;
            case 12:
                orderhis_layout11.setVisibility(View.VISIBLE);
                orderhis_layout12.setVisibility(View.VISIBLE);
                orderhis_orderStatu.setText("订单完成");
                break;
        }
    }

    private void setListener() {
        orderhis_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {

        oid = getIntent().getIntExtra("oid",0);

        dialog.show();
        HttpRequest getOrderInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        getOrderInfoRequest.postOrderIdtoGetOrderInfo(oid).enqueue(new Callback<OrderInfoBean>() {
            @Override
            public void onResponse(Call<OrderInfoBean> call, Response<OrderInfoBean> response) {
                dialog.dismiss();
                OrderInfoBean body = response.body();
                if (body != null){
                    if (body.getStatusCode().equals("200")){
                        OrderInfoBean.DataBean data = body.getData();
                        serviceItemid = data.getServiceItemId();
                        serviceStatu = data.getOrderStatus();
                        projectName = data.getServiceName();
                        projectAddress = data.getAddress();
                        userName = data.getUsername();
                        userTele = data.getUserTele();
                        serviceMoney = data.getMoney() - data.getServiceBail();
                        serviceStartTime = data.getServiceStarttime();
                        serviceLength = data.getServiceTime();
                        serviceNum = data.getCustEmplNum();
                        acreage = data.getServiceParam();
                        frequency = data.getServiceEncy();
                        remarks = data.getServiceDesc();
                        serviceName = data.getRealname();
                        serviceTele = data.getTele();

                        evaluate = data.getServiceEvaluation();
                        evaluateFraction = data.getServiceScore();


                        loadData();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderInfoBean> call, Throwable t) {
                dialog.dismiss();
                ToastUtils.showShortToast(t.getMessage());
            }
        });
    }

    private void initView() {
        orderhis_btnBack = (ImageView) findViewById(R.id.orderhis_btnBack);

        orderhis_layout4 = (RelativeLayout) findViewById(R.id.orderhis_layout4);
        orderhis_layout5 = (LinearLayout) findViewById(R.id.orderhis_layout5);
        orderhis_layout6 = (LinearLayout) findViewById(R.id.orderhis_layout6);
        orderhis_layout7 = (LinearLayout) findViewById(R.id.orderhis_layout7);
        orderhis_layout8 = (LinearLayout) findViewById(R.id.orderhis_layout8);
        orderhis_layout9 = (LinearLayout) findViewById(R.id.orderhis_layout9);
        orderhis_layout11 = (LinearLayout) findViewById(R.id.orderhis_layout11);
        orderhis_layout12 = (RelativeLayout) findViewById(R.id.orderhis_layout12);

        orderhis_layout1_serviceName = (TextView) findViewById(R.id.orderhis_layout1_serviceName);
        orderhis_layout2_addre = (TextView) findViewById(R.id.orderhis_layout2_addre);
        orderhis_layout3_customerInfo = (TextView) findViewById(R.id.orderhis_layout3_customerInfo);
        orderhis_layout4_serviceMoney = (TextView) findViewById(R.id.orderhis_layout4_serviceMoney);
        orderhis_serviceStartTime = (TextView) findViewById(R.id.orderhis_serviceStartTime);
        orderhis_serviceLength = (TextView) findViewById(R.id.orderhis_serviceLength);
        orderhis_serviceNumber = (TextView) findViewById(R.id.orderhis_serviceNumber);
        orderhis_acreage = (TextView) findViewById(R.id.orderhis_acreage);
        orderhis_frequency = (TextView) findViewById(R.id.orderhis_frequency);
        orderhis_remarks = (TextView) findViewById(R.id.orderhis_remarks);
        orderhis_servicePeople = (TextView) findViewById(R.id.orderhis_servicePeople);
        orderhis_layout12_ratingStar = (RatingBar) findViewById(R.id.orderhis_layout12_ratingStar);
        orderhis_evaluate = (TextView) findViewById(R.id.orderhis_evaluate);
        orderhis_orderStatu = (TextView) findViewById(R.id.orderhis_orderStatu);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("加载中");
    }
}
