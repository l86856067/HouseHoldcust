package android.app.wolf.household.view.fragment;

import android.app.ProgressDialog;
import android.app.wolf.household.R;
import android.app.wolf.household.adapter.HomeListViewAdapter;
import android.app.wolf.household.bean.HomeOrderInfoBean;
import android.app.wolf.household.bean.LoginResultBean;
import android.app.wolf.household.bean.MessageEvent;
import android.app.wolf.household.bean.SimpleReturnBean;
import android.app.wolf.household.http.httpconstant.HttpHost;
import android.app.wolf.household.http.httpinterface.HttpRequest;
import android.app.wolf.household.utils.RetrofitUtils;
import android.app.wolf.household.utils.ToastUtils;
import android.app.wolf.household.view.activity.OrderNowActivity;
import android.app.wolf.household.view.myview.ResultFailDialog;
import android.app.wolf.household.view.myview.ResultSuccessDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lh on 2017/9/26.
 * <p/>
 * 功能作用：商户端首页的fragment
 * <p/>
 * 修改记录：
 */
public class HomeFragment extends Fragment {

    private static String TAG = "HomeFragment";

    private SwipeRefreshLayout homefragment_refresh;
    private ListView homefragment_list;

    private List<HomeOrderInfoBean.RowsBean> list;
    private HomeListViewAdapter adapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog dialog;

    double money = 0;
    int custId = 0;    //商户id
    int distance = 50; //距离
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
                    Log.d(TAG,"加载数据完成,"+list.size());
                    homefragment_refresh.setRefreshing(false);
                    break;
                case 1:  //刷新数据完成
                    homefragment_refresh.setRefreshing(false);
                    break;
                case 2:  //上拉加载数据完成
                    homefragment_list.setSelection(now_item);
                    homefragment_refresh.setRefreshing(false);
                    break;
                case 3:  //抢单成功

//                    toRodOrder(msg.arg1);

                    break;
            }
        }
    };

    HomeListViewAdapter.onItemButtonClickListener onItemButtonClickListener = new HomeListViewAdapter.onItemButtonClickListener() {
        @Override
        public void itemBtnClick(int i) {

            HomeOrderInfoBean.RowsBean rowsBean = list.get(i);
            money = sharedPreferences.getFloat("money",0);
            final double newMoney = money - rowsBean.getServiceBail();

            dialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HttpHost.getHttpHost())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
            HttpRequest httpRequest = retrofit.create(HttpRequest.class);
            httpRequest.postOrderIdtoRobOrder(rowsBean.getId(),custId).enqueue(new Callback<SimpleReturnBean>() {
                @Override
                public void onResponse(Call<SimpleReturnBean> call, Response<SimpleReturnBean> response) {
                    SimpleReturnBean body = response.body();
                    dialog.dismiss();
                    if (body != null){
                        if (body.getStatusCode().equals("200")){

                            editor = sharedPreferences.edit();
                            editor.putFloat("money", (float) newMoney);
                            editor.commit();

                            EventBus.getDefault().post("success");

                            final ResultSuccessDialog dialog = new ResultSuccessDialog(getActivity());
                            dialog.setMessage("接单成功");
                            dialog.setPostButton("确定", new ResultSuccessDialog.OnpostButtonInterface() {
                                @Override
                                public void post() {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }else {
                            final ResultFailDialog dialog = new ResultFailDialog(getActivity());
                            dialog.setMessage(body.getStatusDesc());
                            dialog.setPostButton("确定", new ResultFailDialog.OnpostButtonInterface() {
                                @Override
                                public void post() {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SimpleReturnBean> call, Throwable t) {
                    dialog.dismiss();
                    ToastUtils.showShortToast(t.getMessage());
                }
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        initView(view);

        loadData(initpage,0);

        setListener();

        return view;
    }

    private void setListener() {

        /*
        *  点击条目跳转到订单详情页面
        * */
        homefragment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrderNowActivity.class);

                intent.putExtra("orderId",list.get(position).getId());

                startActivity(intent);
            }
        });

        /*
        *  下拉刷新
        * */
        homefragment_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                loadData(initpage,1);
            }
        });

        homefragment_list.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void loadData(final int page , final int sign) {

        homefragment_refresh.setRefreshing(true);
        HttpRequest loadHomeData = RetrofitUtils.getRetrofitInstance().create(HttpRequest.class);
        loadHomeData.postIdtoHomeOrderList(custId,distance,page,rows).enqueue(new Callback<HomeOrderInfoBean>() {
            @Override
            public void onResponse(Call<HomeOrderInfoBean> call, Response<HomeOrderInfoBean> response) {
                HomeOrderInfoBean body = response.body();
                if (body != null){
                    maxpage = (body.getTotal() / rows) + 1;
                    nowpage = body.getPageNum();
                    Log.d(TAG,nowpage+";"+maxpage);
                    List<HomeOrderInfoBean.RowsBean> rows = body.getRows();
                    if (rows != null){
                        for (int i = 0 ; i < rows.size() ; i ++){
                            list.add(rows.get(i));
                        }
                        adapter = new HomeListViewAdapter(getActivity(),list);
                        homefragment_list.setAdapter(adapter);
                        adapter.setOnItemButtonClickListener(onItemButtonClickListener);
                        handler.sendEmptyMessage(sign);
                    }
                }

            }

            @Override
            public void onFailure(Call<HomeOrderInfoBean> call, Throwable t) {
                ToastUtils.showShortToast(t.getMessage());
                Log.d(TAG,t.getMessage());
                handler.sendEmptyMessage(sign);
            }
        });

    }

    private void initView(View view) {
        homefragment_refresh = (SwipeRefreshLayout) view.findViewById(R.id.homefragment_refresh);
        homefragment_list = (ListView) view.findViewById(R.id.homefragment_list);
        sharedPreferences = getActivity().getSharedPreferences("household", Context.MODE_PRIVATE);
        custId = sharedPreferences.getInt("custId",0);
        list = new ArrayList<>();
        adapter = new HomeListViewAdapter(getActivity(),list);
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("接单中");
    }


}
