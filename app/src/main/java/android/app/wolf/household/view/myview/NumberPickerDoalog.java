package android.app.wolf.household.view.myview;

import android.app.Dialog;
import android.app.wolf.household.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by lh on 2017/10/20.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class NumberPickerDoalog extends Dialog {

    private Context context;
    private String cancelText;
    private String confirmText;

    private NumberPicker dialog_time_yearPick;
    private NumberPicker dialog_time_monthPick;
    private NumberPicker dialog_time_dayPick;

    private OnCancelinterface onCancelinterface;
    private OnConfirminterface onConfirminterface;

    public interface OnCancelinterface{
        void cancel();
    }

    public interface OnConfirminterface{
        void confirm(String date);
    }


    public NumberPickerDoalog(Context context) {
        super(context, R.style.dialog_notice);
        this.context = context;
    }

    public NumberPickerDoalog setOnCancelListener(String cancelText,OnCancelinterface onCancelinterface){
        this.cancelText = cancelText;
        this.onCancelinterface = onCancelinterface;
        return this;
    }

    public NumberPickerDoalog setOnConfirmListener(String confirmText,OnConfirminterface onConfirminterface){
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
        View view = inflater.inflate(R.layout.dialog_timepacker, null);
        setContentView(view);
        setCancelable(false);

        TextView dialog_time_btnCancel = (TextView) view.findViewById(R.id.dialog_time_btnCancel);
        TextView dialog_time_btnConfim = (TextView) view.findViewById(R.id.dialog_time_btnConfim);

        dialog_time_btnCancel.setText(cancelText);
        dialog_time_btnConfim.setText(confirmText);


        dialog_time_yearPick = (NumberPicker) view.findViewById(R.id.dialog_time_yearPick);
        dialog_time_monthPick = (NumberPicker) view.findViewById(R.id.dialog_time_monthPick);
        dialog_time_dayPick = (NumberPicker) view.findViewById(R.id.dialog_time_dayPick);

        dialog_time_yearPick.setMinValue(2000);
        dialog_time_yearPick.setMaxValue(2020);
        dialog_time_yearPick.setValue(2017);

        dialog_time_monthPick.setMaxValue(12);
        dialog_time_monthPick.setMinValue(1);
        dialog_time_monthPick.setValue(1);

        dialog_time_dayPick.setMaxValue(31);
        dialog_time_dayPick.setMinValue(1);
        dialog_time_dayPick.setValue(1);


        dialog_time_btnCancel.setOnClickListener(new timepickerListener());
        dialog_time_btnConfim.setOnClickListener(new timepickerListener());

    }

    private class timepickerListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_time_btnCancel:
                    onBackPressed();
                    if (onCancelinterface != null){
                        onCancelinterface.cancel();
                    }
                    break;
                case R.id.dialog_time_btnConfim:
                    onBackPressed();
                    if (onConfirminterface != null){
                        onConfirminterface.confirm(dialog_time_yearPick.getValue()+"-"+dialog_time_monthPick.getValue()+"-"+dialog_time_dayPick.getValue());
                    }
                    break;
            }
        }
    }

}
