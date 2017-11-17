package android.app.wolf.household.view.activity;

import android.app.ProgressDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.bean.ResultBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.app.wolf.household.view.myview.MyDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStaffActivity extends BaseActivity {

    private ImageView addstaff_btnBack;
    private EditText addstaff_editName;
    private EditText addstaff_editidCard;
    private EditText addstaff_editTele;
    private Button addstaff_btnNext;

    private int custId = 0;
    private String realname = "";
    private String idCardNum = "";
    private String tele = "";

    SharedPreferences sharedPreferences;

    ProgressDialog addDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        initView();

        setListener();

    }

    private void setListener() {
        addstaff_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addstaff_btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoIdTrue()){

                    addDialog.show();
                    HttpRequest addRequest = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
                    addRequest.postcustInfotoAdd(custId,realname,idCardNum,tele).enqueue(new Callback<ResultBean>() {
                        @Override
                        public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                            addDialog.dismiss();
                            ResultBean body = response.body();
                            if (body != null){
                                if (body.getStatusCode().equals("200")){
                                    final MyDialog myDialog = new MyDialog(AddStaffActivity.this);
                                    myDialog.setTitle("添加员工");
                                    myDialog.setMessage("添加员工信息成功，是否继续添加？");
                                    myDialog.setOnCancelListener("退出", new MyDialog.OnCancelinterface() {
                                        @Override
                                        public void cancel() {
                                            setResult(1);
                                            finish();
                                        }
                                    });
                                    myDialog.setOnConfirmListener("继续添加", new MyDialog.OnConfirminterface() {
                                        @Override
                                        public void confirm() {
                                            addstaff_editName.setText("");
                                            addstaff_editidCard.setText("");
                                            addstaff_editTele.setText("");
                                            myDialog.dismiss();
                                        }
                                    });
                                    myDialog.show();
                                }else {
                                    ToastUtils.showShortToast(body.getStatusDesc());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultBean> call, Throwable t) {
                            addDialog.dismiss();
                            ToastUtils.showShortToast(t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private boolean infoIdTrue(){
        realname = addstaff_editName.getText().toString();
        idCardNum = addstaff_editidCard.getText().toString();
        tele = addstaff_editTele.getText().toString();

        if (realname.isEmpty()){
            ToastUtils.showShortToast("请输入姓名");
            return false;
        }else if (idCardNum.length() != 18){
            ToastUtils.showShortToast("请输入正确的身份证号");
            return false;
        }else if (tele.length() != 11){
            ToastUtils.showShortToast("请输入正确的手机号");
            return false;
        }else {
            return true;
        }

    }

    private void initView() {
        addstaff_btnBack = (ImageView) findViewById(R.id.addstaff_btnBack);
        addstaff_editName = (EditText) findViewById(R.id.addstaff_editName);
        addstaff_editidCard = (EditText) findViewById(R.id.addstaff_editidCard);
        addstaff_editTele = (EditText) findViewById(R.id.addstaff_editTele);
        addstaff_btnNext = (Button) findViewById(R.id.addstaff_btnNext);

        addDialog = new ProgressDialog(this);
        addDialog.setMessage("正在添加");
        addDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        sharedPreferences = getSharedPreferences("household",MODE_PRIVATE);
        custId = sharedPreferences.getInt("custId",0);
    }
}
