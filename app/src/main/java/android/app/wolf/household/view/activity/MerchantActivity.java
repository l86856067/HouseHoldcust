package android.app.wolf.household.view.activity;

import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MerchantActivity extends BaseActivity implements View.OnClickListener {

    private ImageView merchant_back;
    private TextView merchant_merName;
    private TextView merchant_merAddre;
    private RelativeLayout merchant_staff;

    private SharedPreferences sharedPreferences;
    private String custName = "";
    private String custAddre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        initView();
        setListener();
    }

    private void setListener() {
        merchant_back.setOnClickListener(this);
        merchant_staff.setOnClickListener(this);
    }

    private void initView() {
        merchant_back = (ImageView) findViewById(R.id.merchant_back);
        merchant_merName = (TextView) findViewById(R.id.merchant_merName);
        merchant_merAddre = (TextView) findViewById(R.id.merchant_merAddre);
        merchant_staff = (RelativeLayout) findViewById(R.id.merchant_staff);

        sharedPreferences = getSharedPreferences("household",MODE_PRIVATE);
        custName = sharedPreferences.getString("custName","");
        custAddre = sharedPreferences.getString("custAddre","");

        merchant_merName.setText(custName);
        merchant_merAddre.setText(custAddre);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.merchant_back:
                finish();
                break;
            case R.id.merchant_staff:
                Intent intent1 = new Intent(MerchantActivity.this,StaffListActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
