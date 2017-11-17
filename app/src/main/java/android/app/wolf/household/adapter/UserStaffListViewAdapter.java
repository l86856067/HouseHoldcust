package android.app.wolf.household.adapter;

import android.app.wolf.household.R;
import android.app.wolf.household.bean.UserStaffInfoBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lh on 2017/10/18.
 * <p/>
 * 功能作用：
 * <p/>
 * 修改记录：
 */
public class UserStaffListViewAdapter extends BaseAdapter {

    private Context context;
    private List<UserStaffInfoBean.RowsBean> list;

    public UserStaffListViewAdapter(Context context, List<UserStaffInfoBean.RowsBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_userfragment_staff_item,null);
            holder.name = (TextView) convertView.findViewById(R.id.stafflist_item_name);
            holder.tele = (TextView) convertView.findViewById(R.id.stafflist_item_tele);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserStaffInfoBean.RowsBean rowsBean = list.get(position);
        holder.name.setText(rowsBean.getRealname());
        holder.tele.setText(rowsBean.getIdcardNumber());

        return convertView;
    }

    class ViewHolder{
        TextView name;
        TextView tele;
    }

}
