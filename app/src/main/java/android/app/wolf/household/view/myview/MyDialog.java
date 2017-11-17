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
public class MyDialog extends Dialog {

    private Context context;
    private String title;
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

    public MyDialog(Context context) {
        super(context, R.style.dialog_notice);
        this.context = context;
    }

    public MyDialog setMessage(String message){
        this.message = message;
        return this;
    }
    public MyDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public MyDialog setOnCancelListener(String cancelText, OnCancelinterface onCancelinterface){
        this.cancelText = cancelText;
        this.onCancelinterface = onCancelinterface;
        return this;
    }

    public MyDialog setOnConfirmListener(String confirmText, OnConfirminterface onConfirminterface){
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
        View view = inflater.inflate(R.layout.dialog_mydialog, null);
        setContentView(view);

        TextView mydialog_titleName = (TextView) view.findViewById(R.id.mydialog_titleName);
        TextView mydialog_message = (TextView) view.findViewById(R.id.mydialog_message);
        TextView mydialog_btnCancel = (TextView) view.findViewById(R.id.mydialog_btnCancel);
        TextView mydialog_btnConfirm = (TextView) view.findViewById(R.id.mydialog_btnConfirm);
        mydialog_btnCancel.setText(cancelText);
        mydialog_btnConfirm.setText(confirmText);
        mydialog_btnCancel.setOnClickListener(new CancelListener());
        mydialog_btnConfirm.setOnClickListener(new CancelListener());

        mydialog_titleName.setText(title);
        mydialog_message.setText(message);
        setCancelable(false);
    }

    private class CancelListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mydialog_btnCancel:
                    onBackPressed();
                    if (onCancelinterface != null){
                        onCancelinterface.cancel();
                    }
                    break;
                case R.id.mydialog_btnConfirm:
                    onBackPressed();
                    if (onConfirminterface != null){
                        onConfirminterface.confirm();
                    }
                    break;
            }
        }
    }

}
