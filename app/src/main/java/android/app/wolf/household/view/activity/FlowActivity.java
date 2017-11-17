package android.app.wolf.household.view.activity;

import android.app.wolf.household.R;
import android.app.wolf.household.adapter.HisListViewAdapter;
import android.app.wolf.household.adapter.UserFlowListViewAdapter;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.bean.HomeOrderInfoBean;
import android.app.wolf.household.bean.UserMoneyFlowBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlowActivity extends BaseActivity {

    private ImageView flow_btnBack;
    private SwipeRefreshLayout flow_refresh;
    private ListView flow_list;
    private UserFlowListViewAdapter adapter;

    SharedPreferences sharedPreferences;
    int custId = 0;    //商户id
    int initpage = 1;  //初始页码
    int rows = 15;     //每页个数
    int nowpage = 1;   //当前页面
    int maxpage = 0;   //最大页面
    int last_item = 0; //最后条目
    int max_item = 0;  //最大条目
    int now_item = 0;  //当前顶部条目

    List<UserMoneyFlowBean.RowsBean> list;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    flow_refresh.setRefreshing(false);
                    break;
                case 1:  //刷新数据完成
                    adapter.notifyDataSetChanged();
                    flow_refresh.setRefreshing(false);
                    break;
                case 2:  //上拉加载数据完成
                    adapter.notifyDataSetChanged();
                    flow_list.setSelection(now_item);
                    flow_refresh.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);

        initView();

        loadData(initpage,0);

        setListener();

    }

    private void loadData(int page, final int sign) {

        flow_refresh.setRefreshing(true);
        HttpRequest getFolwList = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        getFolwList.postIdtoUserMoneyFlowList(custId,page,rows).enqueue(new Callback<UserMoneyFlowBean>() {
            @Override
            public void onResponse(Call<UserMoneyFlowBean> call, Response<UserMoneyFlowBean> response) {
                UserMoneyFlowBean body = response.body();
                maxpage = (body.getTotal() / rows) + 1;
                nowpage = body.getPageNum();
                List<UserMoneyFlowBean.RowsBean> rows = body.getRows();
                if (rows != null){
                    for (int i = 0 ; i < rows.size() ; i ++){
                        list.add(rows.get(i));
                    }
                    adapter = new UserFlowListViewAdapter(FlowActivity.this,list);
                    flow_list.setAdapter(adapter);
                    handler.sendEmptyMessage(sign);
                }
            }

            @Override
            public void onFailure(Call<UserMoneyFlowBean> call, Throwable t) {

            }
        });

    }

    private void setListener() {
        flow_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        flow_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData(initpage,1);
            }
        });

        flow_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FlowActivity.this,FlowDetailsActivity.class);
                intent.putExtra("id",list.get(position).getId());
                startActivity(intent);
            }
        });


        flow_list.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void initView() {
        flow_btnBack = (ImageView) findViewById(R.id.flow_btnBack);
        flow_refresh = (SwipeRefreshLayout) findViewById(R.id.flow_refresh);
        flow_list = (ListView) findViewById(R.id.flow_list);
        list = new ArrayList<>();

        sharedPreferences = getSharedPreferences("household",MODE_PRIVATE);
        custId = sharedPreferences.getInt("custId",0);
    }
}
