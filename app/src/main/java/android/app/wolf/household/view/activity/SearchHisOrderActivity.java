package android.app.wolf.household.view.activity;

import android.app.wolf.household.R;
import android.app.wolf.household.adapter.HisListViewAdapter;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.bean.HistoryOrderInfoBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.app.wolf.household.view.myview.NumberPickerDoalog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchHisOrderActivity extends BaseActivity implements View.OnClickListener {

    private TextView searchhis_startBtn;
    private TextView searchhis_endBtn;
    private TextView searchhis_search;
    private ImageView searchhis_back;
    private SwipeRefreshLayout searchhis_refer;
    private ListView searchhis_list;

    String startDate = "";
    String endDate = "";

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
                    searchhis_refer.setRefreshing(false);
                    break;
                case 1:  //刷新数据完成
                    adapter.notifyDataSetChanged();
                    searchhis_refer.setRefreshing(false);
                    break;
                case 2:  //上拉加载数据完成
                    searchhis_list.setSelection(now_item);
                    searchhis_refer.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_his_order);

        initView();

        setListener();
    }

    private void setListener() {
        searchhis_back.setOnClickListener(this);
        searchhis_startBtn.setOnClickListener(this);
        searchhis_endBtn.setOnClickListener(this);
        searchhis_search.setOnClickListener(this);

        searchhis_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HistoryOrderInfoBean.RowsBean rowsBean = list.get(position);
                Intent intent = new Intent(SearchHisOrderActivity.this, OrderHisActivity.class);

                intent.putExtra("oid",list.get(position).getId());

                startActivity(intent);
            }
        });

        searchhis_list.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                now_item = visibleItemCount;
                max_item = totalItemCount;
            }
        });

        searchhis_refer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData(initpage,1);
            }
        });

    }

    private void initView() {
        searchhis_startBtn = (TextView) findViewById(R.id.searchhis_startBtn);
        searchhis_endBtn = (TextView) findViewById(R.id.searchhis_endBtn);
        searchhis_search = (TextView) findViewById(R.id.searchhis_search);
        searchhis_back = (ImageView) findViewById(R.id.searchhis_back);
        searchhis_refer = (SwipeRefreshLayout) findViewById(R.id.searchhis_refer);
        searchhis_list = (ListView) findViewById(R.id.searchhis_list);
        list = new ArrayList<>();
        sharedPreferences = getSharedPreferences("household",MODE_PRIVATE);
        custId = sharedPreferences.getInt("custId",0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchhis_back:
                finish();
                break;
            case R.id.searchhis_startBtn:

                final NumberPickerDoalog startdialog = new NumberPickerDoalog(SearchHisOrderActivity.this);
                startdialog.setOnCancelListener("取消", new NumberPickerDoalog.OnCancelinterface() {
                    @Override
                    public void cancel() {
                        startdialog.dismiss();
                    }
                });
                startdialog.setOnConfirmListener("确定", new NumberPickerDoalog.OnConfirminterface() {
                    @Override
                    public void confirm(String date) {
                        startDate = date;
                        searchhis_startBtn.setText(date);
                        startdialog.dismiss();
                    }


                });
                startdialog.show();

                break;
            case R.id.searchhis_endBtn:

                final NumberPickerDoalog enddialog = new NumberPickerDoalog(SearchHisOrderActivity.this);
                enddialog.setOnCancelListener("取消", new NumberPickerDoalog.OnCancelinterface() {
                    @Override
                    public void cancel() {
                        enddialog.dismiss();
                    }
                });
                enddialog.setOnConfirmListener("确定", new NumberPickerDoalog.OnConfirminterface() {
                    @Override
                    public void confirm(String date) {
                        endDate = date;
                        searchhis_endBtn.setText(date);
                        enddialog.dismiss();
                    }


                });
                enddialog.show();

                break;
            case R.id.searchhis_search:
                    if (dateIsOk()){
                        list.clear();
                        loadData(initpage,0);
                    }
                break;
        }
    }

    private void loadData(int page, final int sign) {

        searchhis_refer.setRefreshing(true);
        HttpRequest loadHisData = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        loadHisData.postIdtoHistoryOrderList(custId,startDate,endDate,page,rows).enqueue(new Callback<HistoryOrderInfoBean>() {
            @Override
            public void onResponse(Call<HistoryOrderInfoBean> call, Response<HistoryOrderInfoBean> response) {
                HistoryOrderInfoBean body = response.body();
                if (body != null){
                    maxpage = (body.getTotal() / rows) + 1;
                    nowpage = body.getPageNum();
                    List<HistoryOrderInfoBean.RowsBean> rows = body.getRows();
                    if (rows != null){
                        for (int i = 0 ; i < rows.size() ; i ++){
                            list.add(rows.get(i));
                        }
                        adapter = new HisListViewAdapter(SearchHisOrderActivity.this,list);
                        searchhis_list.setAdapter(adapter);
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

    public boolean dateIsOk(){
        if (startDate.isEmpty()){
            ToastUtils.showShortToast("请选择开始日期");
            return false;
        }else if (endDate.isEmpty()){
            ToastUtils.showShortToast("请选择结束日期");
            return false;
        }else {
            return true;
        }
    }

}
