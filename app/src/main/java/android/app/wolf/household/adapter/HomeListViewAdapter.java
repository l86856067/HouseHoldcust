package android.app.wolf.household.adapter;

import android.app.ProgressDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.bean.HomeOrderInfoBean;
import android.app.wolf.household.bean.SimpleReturnBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.app.wolf.household.view.activity.MoneyActivity;
import android.app.wolf.household.view.myview.ChargeDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lh on 2017/10/12.
 * <p>
 * 功能作用：首页抢单的ListView的适配器
 * <p>
 * 修改记录：
 */
public class HomeListViewAdapter extends BaseAdapter {

    private Context context;
    private List<HomeOrderInfoBean.RowsBean> list;
    private int custId;
    private double money;
    private double newMoney;
    private SharedPreferences sharedPreferences;


    public HomeListViewAdapter(Context context,List<HomeOrderInfoBean.RowsBean> list){
        this.context = context;
        this.list = list;
        sharedPreferences = context.getSharedPreferences("household",Context.MODE_PRIVATE);
        custId = sharedPreferences.getInt("custId",0);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_homefragment_item,null);
            holder.homelist_item_head = (ImageView) convertView.findViewById(R.id.homelist_item_head);
            holder.homelist_item_serviceName = (TextView) convertView.findViewById(R.id.homelist_item_serviceName);
            holder.homelist_item_textAddre = (TextView) convertView.findViewById(R.id.homelist_item_textAddre);
            holder.homelist_item_customerName = (TextView) convertView.findViewById(R.id.homelist_item_customerName);
            holder.homelist_item_textBtn = (TextView) convertView.findViewById(R.id.homelist_item_textBtn);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final HomeOrderInfoBean.RowsBean rowsBean = list.get(position);
        holder.homelist_item_serviceName.setText(rowsBean.getServiceName());
        String[] adds = rowsBean.getAddress().split("[|]");
        holder.homelist_item_textAddre.setText(adds[0] + adds[1] + adds[2]);
        holder.homelist_item_customerName.setText(rowsBean.getUsername());

        switch (rowsBean.getServiceItemId()){
            case 1:
                holder.homelist_item_head.setImageResource(R.drawable.home_clean);
                break;
            case 2:
                holder.homelist_item_head.setImageResource(R.drawable.home_glass);
                break;
            case 3:
                holder.homelist_item_head.setImageResource(R.drawable.home_hourly);
                break;
            case 4:
                holder.homelist_item_head.setImageResource(R.drawable.home_mater);
                break;
            case 5:
                holder.homelist_item_head.setImageResource(R.drawable.home_nanny);
                break;
            case 6:
                holder.homelist_item_head.setImageResource(R.drawable.home_nanny);
                break;
            case 7:
                holder.homelist_item_head.setImageResource(R.drawable.home_old);
                break;
            case 8:
                holder.homelist_item_head.setImageResource(R.drawable.home_parent);
                break;
            case 9:
                holder.homelist_item_head.setImageResource(R.drawable.home_other);
                break;
            case 10:
                holder.homelist_item_head.setImageResource(R.drawable.home_reclaim);
                break;
        }

        holder.homelist_item_textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rowsBean.getServiceBail() == 0){

                    final ChargeDialog dialog = new ChargeDialog(context);
                    dialog.setMessage("接此订单需要收费"+rowsBean.getServiceBail()+"元，确定支付吗？");
                    dialog.setOnCancelListener("取消", new ChargeDialog.OnCancelinterface() {
                        @Override
                        public void cancel() {
                            dialog.dismiss();
                        }
                    });
                    dialog.setOnConfirmListener("确定", new ChargeDialog.OnConfirminterface() {
                        @Override
                        public void confirm() {
                            dialog.dismiss();
                            onItemButtonClickListener.itemBtnClick(position);
                            holder.homelist_item_textBtn.setText("已抢单");
                            holder.homelist_item_textBtn.setOnClickListener(null);

                        }
                    });
                    dialog.show();

                }else {

                    money = sharedPreferences.getFloat("money",0);

                    if ((money - rowsBean.getServiceBail()) >= 0){

                        final ChargeDialog dialog = new ChargeDialog(context);
                        dialog.setMessage("接此订单需要收费"+rowsBean.getServiceBail()+"元，确定支付吗？");
                        dialog.setOnCancelListener("取消", new ChargeDialog.OnCancelinterface() {
                            @Override
                            public void cancel() {
                                dialog.dismiss();
                            }
                        });
                        dialog.setOnConfirmListener("确定", new ChargeDialog.OnConfirminterface() {
                            @Override
                            public void confirm() {
                                dialog.dismiss();
                                onItemButtonClickListener.itemBtnClick(position);
                                holder.homelist_item_textBtn.setText("已抢单");
                                holder.homelist_item_textBtn.setOnClickListener(null);

                            }
                        });
                        dialog.show();

                    }else {

                        final ChargeDialog dialog = new ChargeDialog(context);
                        dialog.setMessage("您的账户余额不足，请充值！");
                        dialog.setOnCancelListener("取消", new ChargeDialog.OnCancelinterface() {
                            @Override
                            public void cancel() {
                                dialog.dismiss();
                            }
                        });
                        dialog.setOnConfirmListener("去充值", new ChargeDialog.OnConfirminterface() {
                            @Override
                            public void confirm() {
                                Intent intent = new Intent(context, MoneyActivity.class);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }
                }

            }
        });

        return convertView;
    }

    class ViewHolder{
        ImageView homelist_item_head;
        TextView homelist_item_serviceName;
        TextView homelist_item_textAddre;
        TextView homelist_item_customerName;
        TextView homelist_item_textBtn;
    }

    public interface onItemButtonClickListener{
        void itemBtnClick(int i);
    }
    private onItemButtonClickListener onItemButtonClickListener;

    public void setOnItemButtonClickListener(onItemButtonClickListener onItemButtonClickListener){
        this.onItemButtonClickListener = onItemButtonClickListener;
    }

}
