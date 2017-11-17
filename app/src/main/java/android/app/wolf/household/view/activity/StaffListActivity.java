package android.app.wolf.household.view.activity;

import android.app.wolf.household.R;
import android.app.wolf.household.adapter.UserStaffListViewAdapter;
import android.app.wolf.household.application.BaseActivity;
import android.app.wolf.household.bean.UserStaffInfoBean;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView stafflist_back;
    private TextView stafflist_add;
    private SwipeRefreshLayout stafflist_refresh;
    private ListView stafflist_list;

    List<UserStaffInfoBean.RowsBean> list;
    UserStaffListViewAdapter adapter;
    SharedPreferences sharedPreferences;
    int custId = 0;
    int initpage = 1;  //初始页码
    int rows = 15;     //每页个数
    int nowpage = 1;      //当前页面
    int maxpage = 0;
    int last_item = 0;
    int max_item = 0;
    int now_item = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    adapter.notifyDataSetChanged();
                    stafflist_refresh.setRefreshing(false);
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    stafflist_refresh.setRefreshing(false);
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    stafflist_refresh.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);

        initView();

        loadData(initpage,0);

        setListener();
    }

    private void loadData(int page, final int sign) {

        stafflist_refresh.setRefreshing(true);
        HttpRequest loadStaffList = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        loadStaffList.postIdtoUserStaffInfoList(custId,page,rows).enqueue(new Callback<UserStaffInfoBean>() {
            @Override
            public void onResponse(Call<UserStaffInfoBean> call, Response<UserStaffInfoBean> response) {
                UserStaffInfoBean body = response.body();
                maxpage = (body.getTotal() / rows) + 1;
                nowpage = body.getPageNum();
                List<UserStaffInfoBean.RowsBean> rows = body.getRows();
                for (int i = 0 ; i < rows.size() ; i ++){
                    list.add(rows.get(i));
                }

                handler.sendEmptyMessage(sign);
            }

            @Override
            public void onFailure(Call<UserStaffInfoBean> call, Throwable t) {
                handler.sendEmptyMessage(sign);
                ToastUtils.showShortToast(t.getMessage());
            }
        });
    }

    private void setListener() {
        stafflist_back.setOnClickListener(this);
        stafflist_add.setOnClickListener(this);

        stafflist_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData(initpage,1);
            }
        });

        stafflist_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
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
                last_item = firstVisibleItem + visibleItemCount;
                now_item = firstVisibleItem;
                max_item = totalItemCount;
            }
        });

    }

    private void initView() {
        stafflist_back = (ImageView) findViewById(R.id.stafflist_back);
        stafflist_add = (TextView) findViewById(R.id.stafflist_add);
        stafflist_refresh = (SwipeRefreshLayout) findViewById(R.id.stafflist_refresh);
        stafflist_list = (ListView) findViewById(R.id.stafflist_list);
        sharedPreferences = getSharedPreferences("household",MODE_PRIVATE);
        custId = sharedPreferences.getInt("custId",0);
        list = new ArrayList<>();
        adapter = new UserStaffListViewAdapter(StaffListActivity.this,list);
        stafflist_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stafflist_back:
                finish();
                break;
            case R.id.stafflist_add:

                Intent intent = new Intent(this,AddStaffActivity.class);
                startActivityForResult(intent,1);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        list.clear();
        loadData(initpage,1);
    }
}
