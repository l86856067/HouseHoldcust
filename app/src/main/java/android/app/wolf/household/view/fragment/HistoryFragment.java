package android.app.wolf.household.view.fragment;

import android.app.ProgressDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.adapter.HisListViewAdapter;
import android.app.wolf.household.adapter.HomeListViewAdapter;
import android.app.wolf.household.bean.HistoryOrderInfoBean;
import android.app.wolf.household.bean.HomeOrderInfoBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.app.wolf.household.view.activity.OrderHisActivity;
import android.app.wolf.household.view.activity.SearchHisOrderActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lh on 2017/9/26.
 * <p/>
 * 功能作用：商户端历史订单的fragment
 * <p/>
 * 修改记录：
 */
public class HistoryFragment extends Fragment {

    private RelativeLayout hisfragment_search_btn;    //搜索按钮
    private SwipeRefreshLayout hisfragment_refresh;   //下拉刷新
    private ListView hisfragment_list;                //显示订单信息的ListView
    private List<HistoryOrderInfoBean.RowsBean> list;  //放置订单信息的ArrayList
    private HisListViewAdapter adapter;                //

    private SharedPreferences sharedPreferences;
    int custId = 0;    //商户id
    int initpage = 1;  //初始页码
    int rows = 15;     //每页个数
    int nowpage = 1;   //当前页面
    int maxpage = 0;   //最大页面
    int last_item = 0; //最后条目
    int max_item = 0;  //最大条目
    int now_item = 0;  //当前顶部条目

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:  //加载数据完成
                    hisfragment_refresh.setRefreshing(false);
                    break;
                case 1:  //刷新数据完成
                    hisfragment_refresh.setRefreshing(false);
                    break;
                case 2:  //上拉加载数据完成
                    hisfragment_list.setSelection(now_item);
                    hisfragment_refresh.setRefreshing(false);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);

        initView(view);

        loadData(initpage,0);

        setListener();

        return view;
    }

    private void loadData(int page,final int sign) {

        hisfragment_refresh.setRefreshing(true);
        HttpRequest loadHisData = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        loadHisData.postIdtoHistoryOrderList(custId,"","",page,rows).enqueue(new Callback<HistoryOrderInfoBean>() {
            @Override
            public void onResponse(Call<HistoryOrderInfoBean> call, Response<HistoryOrderInfoBean> response) {
                HistoryOrderInfoBean body = response.body();
                if (body != null){
                    maxpage = (body.getTotal() / rows) + 1;
                    nowpage = body.getPageNum();
                    List<HistoryOrderInfoBean.RowsBean> rows = body.getRows();

                    if (rows != null){

                        for (HistoryOrderInfoBean.RowsBean rowsBean : rows){
                            list.add(rowsBean);
                        }

                        hisfragment_list.setAdapter(adapter);
                        handler.sendEmptyMessage(sign);
                    }
                }

            }

            @Override
            public void onFailure(Call<HistoryOrderInfoBean> call, Throwable t) {
                ToastUtils.showShortToast(t.getMessage());
                handler.sendEmptyMessage(sign);
            }
        });

    }

    private void setListener() {

        hisfragment_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Double ddd = Double.parseDouble("6565");
//                NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
//                String str = format.format(ddd);
//                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SearchHisOrderActivity.class);
                startActivity(intent);
            }
        });

        hisfragment_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData(initpage,1);
            }
        });

        hisfragment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HistoryOrderInfoBean.RowsBean rowsBean = list.get(position);
                Intent intent = new Intent(getActivity(), OrderHisActivity.class);

                intent.putExtra("oid",list.get(position).getId());

                startActivity(intent);
            }
        });

        hisfragment_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.d(TAG,"onScrollStateChanged:"+scrollState);
                switch (scrollState){
                    case 0:
                        if (last_item == max_item){
                            if (nowpage < maxpage){
                                loadData(++nowpage , 2);
                            }else {
                                ToastUtils.showShortToast("到最底了");
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.d(TAG,"onScroll:"+firstVisibleItem+","+visibleItemCount+","+totalItemCount);
                last_item = firstVisibleItem + visibleItemCount;
                now_item = totalItemCount;
                max_item = totalItemCount;
            }
        });

    }

    private void initView(View view) {
        hisfragment_search_btn = (RelativeLayout) view.findViewById(R.id.hisfragment_search_btn);
        hisfragment_refresh = (SwipeRefreshLayout) view.findViewById(R.id.hisfragment_refresh);
        hisfragment_list = (ListView) view.findViewById(R.id.hisfragment_list);
        list = new ArrayList<>();
        adapter = new HisListViewAdapter(getActivity(),list);
        sharedPreferences = getActivity().getSharedPreferences("household", Context.MODE_PRIVATE);
        custId = sharedPreferences.getInt("custId",0);
    }
}
