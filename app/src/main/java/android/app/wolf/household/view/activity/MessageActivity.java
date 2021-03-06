package android.app.wolf.household.view.activity;

import android.app.wolf.household.R;
import android.app.wolf.household.application.BaseActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MessageActivity extends BaseActivity {

    private ImageView message_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initView();

        setListener();

    }

    private void setListener() {
        message_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        message_back = (ImageView) findViewById(R.id.message_back);
    }
}
