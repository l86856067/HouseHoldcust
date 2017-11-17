package android.app.wolf.household.view.myview;

import android.app.Dialog;
import android.app.wolf.household.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lh on 2017/10/14.
 * <p>
 * 功能作用：联系人的Dialog
 * <p>
 * 修改记录：
 */
public class CallDialog extends Dialog {

    private Context context;
    private String message;
    private String cancleText;
    private String nextText;
    private onCancelButtoninterface onCancelButtoninterface;
    private onNextButtoninterface onNextButtoninterface;

    public interface onCancelButtoninterface{
        void cancel();
    }

    public interface onNextButtoninterface{
        void call();
    }

    public CallDialog(Context context) {
        super(context, R.style.dialog_notice);
        this.context = context;
    }

    public CallDialog setMessage(String message){
        this.message = message;
        return this;
    }

    public CallDialog setOnCancelButtoninterface(String cancleText,onCancelButtoninterface onCancelButtoninterface) {
        this.cancleText = cancleText;
        this.onCancelButtoninterface = onCancelButtoninterface;
        return this;
    }

    public CallDialog setOnNextButtoninterface(String nextText,onNextButtoninterface onNextButtoninterface) {
        this.nextText = nextText;
        this.onNextButtoninterface = onNextButtoninterface;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_call, null);
        setContentView(view);
        setCancelable(false);

        TextView dialog_call_message = (TextView) view.findViewById(R.id.dialog_call_message);
        TextView dialog_call_cancelBtn = (TextView) view.findViewById(R.id.dialog_call_cancelBtn);
        TextView dialog_call_callBtn = (TextView) view.findViewById(R.id.dialog_call_callBtn);

        dialog_call_message.setText(message);

        dialog_call_cancelBtn.setOnClickListener(new CallDialogListener());
        dialog_call_callBtn.setOnClickListener(new CallDialogListener());


    }

    private class CallDialogListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_call_cancelBtn:
                    onBackPressed();
                    if (onCancelButtoninterface != null){
                        onCancelButtoninterface.cancel();
                    }
                    break;
                case R.id.dialog_call_callBtn:
                    onBackPressed();
                    if (onNextButtoninterface != null){
                        onNextButtoninterface.call();
                    }
                    break;
            }
        }
    }

}
