package android.app.wolf.household.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.view.myview.NoticeDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, "wx62834e4cb5377fbb");
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {

		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		switch (resp.errCode){
			case 0:

				final NoticeDialog success = new NoticeDialog(this);
				success.setMessage("充值成功！");
				success.setPostButton("我知道了", new NoticeDialog.OnpostButtonInterface() {
					@Override
					public void post() {
						success.dismiss();
						finish();
					}
				});
				success.show();

				break;
			case -1:
			case -2:

				final NoticeDialog fail = new NoticeDialog(this);
				fail.setMessage(resp.errStr);
				fail.setPostButton("我知道了", new NoticeDialog.OnpostButtonInterface() {
					@Override
					public void post() {
						fail.dismiss();
						finish();
					}
				});
				fail.show();

				break;
		}

//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
//		}
	}
}