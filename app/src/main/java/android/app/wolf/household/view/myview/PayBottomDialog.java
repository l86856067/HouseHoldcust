package android.app.wolf.household.view.myview;

import android.app.Dialog;
import android.app.wolf.household.R;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by lh on 2017/11/3.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class PayBottomDialog extends DialogFragment {

    private CheckBox paydialog_aliPay;
    private CheckBox paydialog_wechatPay;
    private TextView paydialog_money;
    private String moneyText;
    private Button paydialog_pay;

    private OnPayinterface payinterface;
    private int payType = 0;

    public interface OnPayinterface{
        void pay(int type);
    }

    public PayBottomDialog setOnPayListener(OnPayinterface payinterface){
        this.payinterface = payinterface;
        return this;
    }

    public PayBottomDialog setMoneyText(String moneyText){
        this.moneyText = moneyText;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_bottonpay);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 5 / 10;
        window.setAttributes(lp);
        paydialog_aliPay = (CheckBox) dialog.findViewById(R.id.paydialog_aliPay);
        paydialog_wechatPay = (CheckBox) dialog.findViewById(R.id.paydialog_wechatPay);
        paydialog_money = (TextView) dialog.findViewById(R.id.paydialog_money);
        paydialog_pay = (Button) dialog.findViewById(R.id.paydialog_pay);

        paydialog_money.setText(moneyText);

        paydialog_aliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    paydialog_wechatPay.setChecked(false);
                }
            }
        });

        paydialog_wechatPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    paydialog_aliPay.setChecked(false);
                }
            }
        });

        paydialog_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payinterface != null){
                    if (paydialog_aliPay.isChecked()){
                        payType = 1;
                    }else if (paydialog_wechatPay.isChecked()){
                        payType = 2;
                    }else {
                        payType = 0;
                    }
                    payinterface.pay(payType);
                }
            }
        });

        return dialog;

    }


}
