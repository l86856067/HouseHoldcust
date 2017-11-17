package android.app.wolf.household.view.activity;

import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutActivity extends BaseActivity {

    private ImageView about_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();

        setListener();
    }

    private void setListener() {
        about_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        about_back = (ImageView) findViewById(R.id.about_back);
    }
}
