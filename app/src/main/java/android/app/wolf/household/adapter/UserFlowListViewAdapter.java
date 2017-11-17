package android.app.wolf.household.adapter;

import android.app.wolf.household.R;
import android.app.wolf.household.bean.HomeOrderInfoBean;
import android.app.wolf.household.bean.UserMoneyFlowBean;
import android.app.wolf.household.view.myview.ChargeDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lh on 2017/10/12.
 * <p>
 * 功能作用：首页抢单的ListView的适配器
 * <p>
 * 修改记录：
 */
public class UserFlowListViewAdapter extends BaseAdapter {

    private Context context;
    private List<UserMoneyFlowBean.RowsBean> list;

    public UserFlowListViewAdapter(Context context, List<UserMoneyFlowBean.RowsBean> list){
        this.context = context;
        this.list = list;
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

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_userfragment_moneyflow_item,null);
            holder.flowlist_item_head = (ImageView) convertView.findViewById(R.id.flowlist_item_head);
            holder.flowlist_item_textName = (TextView) convertView.findViewById(R.id.flowlist_item_textName);
            holder.flowlist_item_textDate = (TextView) convertView.findViewById(R.id.flowlist_item_textDate);
            holder.flowlist_item_state = (TextView) convertView.findViewById(R.id.flowlist_item_state);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserMoneyFlowBean.RowsBean rowsBean = list.get(position);
        holder.flowlist_item_textDate.setText(rowsBean.getGenerateTime());
        switch (rowsBean.getCashType()){
            case 0:
                holder.flowlist_item_head.setImageResource(R.drawable.flow_cash);
                holder.flowlist_item_textName.setText("提现");
                holder.flowlist_item_state.setText("-"+rowsBean.getMoney());
                break;
            case 2:
                holder.flowlist_item_head.setImageResource(R.drawable.flow_refund);
                holder.flowlist_item_textName.setText("退款");
                holder.flowlist_item_state.setText("+"+rowsBean.getMoney());
                break;
            case 3:
                holder.flowlist_item_textName.setText("充值");
                holder.flowlist_item_head.setImageResource(R.drawable.flow_recharge);
                holder.flowlist_item_state.setText("+"+rowsBean.getMoney());
                break;
            case 5:
                holder.flowlist_item_head.setImageResource(R.drawable.flow_grab);
                holder.flowlist_item_textName.setText("抢单扣除");
                holder.flowlist_item_state.setText("-"+rowsBean.getMoney());
                break;
            case 7:
                holder.flowlist_item_textName.setText("订单收入");
                holder.flowlist_item_head.setImageResource(R.drawable.flow_income);
                holder.flowlist_item_state.setText("+"+rowsBean.getMoney());
                break;
        }


        return convertView;
    }

    class ViewHolder{

        ImageView flowlist_item_head;
        TextView flowlist_item_textName;
        TextView flowlist_item_textDate;
        TextView flowlist_item_state;
    }

}
