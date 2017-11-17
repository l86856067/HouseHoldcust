package android.app.wolf.household.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.bean.FlowInfoBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlowDetailsActivity extends BaseActivity {

    private ImageView flowdeta_back;
    private TextView flowdeta_serviceName;
    private TextView flowdeta_money;
    private TextView flowdeta_orderName;
    private TextView flowdeta_orderTime;
    private TextView flowdeta_orderId;

    int id = 0;
    String userIcon = "";
    String serviceName = "";
    int cashType = 0;
    float money = 0;
    String username = "";
    String generateTime = "";
    String paymentFlowId = "";

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_details);

        initView();

        getData();

        loadData();

        setListener();
    }

    private void setListener() {
        flowdeta_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        switch (cashType){
            case 0:
                flowdeta_serviceName.setText("提现");
                flowdeta_money.setText("-"+money);
                break;
            case 2:
                flowdeta_serviceName.setText("退款");
                flowdeta_money.setText("+"+money);
                break;
            case 3:
                flowdeta_serviceName.setText("充值");
                flowdeta_money.setText("+"+money);
                break;
            case 5:
                flowdeta_serviceName.setText("抢单支出");
                flowdeta_money.setText("-"+money);
                break;
            case 7:
                flowdeta_serviceName.setText("服务收入");
                flowdeta_money.setText("+"+money);
                break;
        }
        flowdeta_orderName.setText(username);
        flowdeta_orderTime.setText(generateTime);
        flowdeta_orderId.setText(paymentFlowId);
    }

    private void getData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        dialog.show();

        HttpRequest getFlowInfoRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        getFlowInfoRequest.postFlowIdtoGetFlowInfo(id).enqueue(new Callback<FlowInfoBean>() {
            @Override
            public void onResponse(Call<FlowInfoBean> call, Response<FlowInfoBean> response) {
                dialog.dismiss();
                FlowInfoBean body = response.body();
                if (body != null){
                    if (body.getStatusCode().equals("200")){
                        FlowInfoBean.DataBean data = body.getData();
                        userIcon = data.getUserIcon();
                        serviceName = data.getServiceName();
                        cashType = data.getCashType();
                        money = data.getMoney();
                        username = data.getUsername();
                        generateTime = data.getGenerateTime();
                        paymentFlowId = data.getPaymentFlowId();
                        loadData();
                    }
                }
            }

            @Override
            public void onFailure(Call<FlowInfoBean> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void initView() {
        flowdeta_back = (ImageView) findViewById(R.id.flowdeta_back);
        flowdeta_serviceName = (TextView) findViewById(R.id.flowdeta_serviceName);
        flowdeta_money = (TextView) findViewById(R.id.flowdeta_money);
        flowdeta_orderName = (TextView) findViewById(R.id.flowdeta_orderName);
        flowdeta_orderTime = (TextView) findViewById(R.id.flowdeta_orderTime);
        flowdeta_orderId = (TextView) findViewById(R.id.flowdeta_orderId);
        dialog = new ProgressDialog(this);
        dialog.setMessage("加载中");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
}
