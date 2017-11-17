package android.app.wolf.household.adapter;

import android.app.wolf.household.R;
import android.app.wolf.household.bean.HistoryOrderInfoBean;
import android.app.wolf.household.bean.HomeOrderInfoBean;
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
 * 功能作用：历史订单的ListView的适配器
 * <p>
 * 修改记录：
 */
public class HisListViewAdapter extends BaseAdapter {

    private Context context;
    private List<HistoryOrderInfoBean.RowsBean> list;

    public HisListViewAdapter(Context context, List<HistoryOrderInfoBean.RowsBean> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_hisfragment_item,null);
            holder.hislist_item_head = (ImageView) convertView.findViewById(R.id.hislist_item_head);
            holder.hislist_item_textName = (TextView) convertView.findViewById(R.id.hislist_item_textName);
            holder.hislist_item_textDate = (TextView) convertView.findViewById(R.id.hislist_item_textDate);
            holder.homelist_item_state = (TextView) convertView.findViewById(R.id.homelist_item_state);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        HistoryOrderInfoBean.RowsBean rowsBean = list.get(position);
        holder.hislist_item_textName.setText(rowsBean.getServiceName());
        holder.hislist_item_textDate.setText(rowsBean.getDoneTime());
        switch (rowsBean.getOrderStatus()){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                holder.homelist_item_state.setText("等待沟通");
                break;
            case 4:
                break;
            case 5:
                holder.homelist_item_state.setText("取消订单");
                break;
            case 6:
                holder.homelist_item_state.setText("已接单");
                break;
            case 10:
                holder.homelist_item_state.setText("服务中");
                break;
            case 11:
                holder.homelist_item_state.setText("服务完成");
                break;
            case 12:
                holder.homelist_item_state.setText("已评价");
                break;
        }

        switch (rowsBean.getServiceItemId()){
            case 1:
                holder.hislist_item_head.setImageResource(R.drawable.home_clean);
                break;
            case 2:
                holder.hislist_item_head.setImageResource(R.drawable.home_glass);
                break;
            case 3:
                holder.hislist_item_head.setImageResource(R.drawable.home_hourly);
                break;
            case 4:
                holder.hislist_item_head.setImageResource(R.drawable.home_mater);
                break;
            case 5:
                holder.hislist_item_head.setImageResource(R.drawable.home_nanny);
                break;
            case 6:
                holder.hislist_item_head.setImageResource(R.drawable.home_nanny);
                break;
            case 7:
                holder.hislist_item_head.setImageResource(R.drawable.home_old);
                break;
            case 8:
                holder.hislist_item_head.setImageResource(R.drawable.home_parent);
                break;
            case 9:
                holder.hislist_item_head.setImageResource(R.drawable.home_other);
                break;
            case 10:
                holder.hislist_item_head.setImageResource(R.drawable.home_reclaim);
                break;
        }

        return convertView;
    }

    class ViewHolder{
        ImageView hislist_item_head;
        TextView hislist_item_textName;
        TextView hislist_item_textDate;
        TextView homelist_item_state;
    }

}
