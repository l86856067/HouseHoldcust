package android.app.wolf.household.view.myview;

import android.app.Dialog;
import android.app.wolf.household.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lh on 2017/10/12.
 * <p>
 * 功能作用：扣费的Dialog
 * <p>
 * 修改记录：
 */
public class ChargeDialog extends Dialog {

    private Context context;
    private String message;
    private String cancelText;
    private String confirmText;
    private OnCancelinterface onCancelinterface;
    private OnConfirminterface onConfirminterface;

    public interface OnCancelinterface{
        void cancel();
    }

    public interface OnConfirminterface{
        void confirm();
    }

    public ChargeDialog(Context context) {
        super(context, R.style.dialog_notice);
        this.context = context;
    }

    public ChargeDialog setMessage(String message){
        this.message = message;
        return this;
    }

    public ChargeDialog setOnCancelListener(String cancelText,OnCancelinterface onCancelinterface){
        this.cancelText = cancelText;
        this.onCancelinterface = onCancelinterface;
        return this;
    }

    public ChargeDialog setOnConfirmListener(String confirmText,OnConfirminterface onConfirminterface){
        this.confirmText = confirmText;
        this.onConfirminterface = onConfirminterface;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_charge, null);
        setContentView(view);

        TextView textmessage = (TextView) view.findViewById(R.id.dialog_charge_message);
        TextView btncancel = (TextView) view.findViewById(R.id.dialog_charge_btnCancel);
        TextView btnconfirm = (TextView) view.findViewById(R.id.dialog_charge_btnConfirm);
        btncancel.setText(cancelText);
        btnconfirm.setText(confirmText);
        btncancel.setOnClickListener(new CancelListener());
        btnconfirm.setOnClickListener(new CancelListener());

        textmessage.setText(message);
        setCancelable(false);
    }

    private class CancelListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_charge_btnCancel:
                    onBackPressed();
                    if (onCancelinterface != null){
                        onCancelinterface.cancel();
                    }
                    break;
                case R.id.dialog_charge_btnConfirm:
                    onBackPressed();
                    if (onConfirminterface != null){
                        onConfirminterface.confirm();
                    }
                    break;
            }
        }
    }

}
