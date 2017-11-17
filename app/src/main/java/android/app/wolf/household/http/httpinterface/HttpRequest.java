package android.app.wolf.household.http.httpinterface;

import android.app.wolf.household.bean.AliPayBean;
import android.app.wolf.household.bean.FlowInfoBean;
import android.app.wolf.household.bean.HistoryOrderInfoBean;
import android.app.wolf.household.bean.HomeOrderInfoBean;
import android.app.wolf.household.bean.LoginResultBean;
import android.app.wolf.household.bean.OrderInfoBean;
import android.app.wolf.household.bean.ResultBean;
import android.app.wolf.household.bean.SimpleReturnBean;
import android.app.wolf.household.bean.UpGradeBean;
import android.app.wolf.household.bean.UserCustInfoBean;
import android.app.wolf.household.bean.UserMoneyFlowBean;
import android.app.wolf.household.bean.UserStaffInfoBean;
import android.app.wolf.household.bean.WechatPayBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lh on 2017/10/16.
 * <p/>
 * 功能作用： 网络请求时的请求接口
 * <p/>
 * 修改记录：
 */
public interface HttpRequest {

    /*
    *  用户登录时候的get请求
    *  tele 用户手机号码
    *  password 用户密码
    * */
    @GET("custauthinfo/gdlogin.do")
    Call<LoginResultBean> getToLogin(@Query("tele")String tele, @Query("password")String password);

    /*
    *  抢单页面获取附近订单列表的post请求
    *  id 商户id
    *  distance 距离
    *  page 页码
    *  rows 每页个数
    * */
    @POST("orderinfo/queryRecOrderPageList.do")
    @FormUrlEncoded
    Call<HomeOrderInfoBean> postIdtoHomeOrderList(@Field("id") int id,@Field("distance") int distance,@Field("page") int page,@Field("rows") int rows);

    /*
    *  抢单页面抢单的post请求
    *  oid 订单id
    *  cid 商户id
    * */
    @POST("graborder/goGrabOrder.do")
    @FormUrlEncoded
    Call<SimpleReturnBean> postOrderIdtoRobOrder(@Field("oid") int oid,@Field("cid") int cid);


/****************** 历史订单的请求 ****************************************************************************************************************************************/

    /*
    *  历史订单页面获取订单List的post请求
    *  id 商户id
    *  page 页码
    *  rows 每页个数
    * */
    @POST("graborder/queryOldOrderPageList.do")
    @FormUrlEncoded
    Call<HistoryOrderInfoBean> postIdtoHistoryOrderList(@Field("id")int id,@Field("startdate") String startdate,@Field("enddate") String enddate,@Field("page")int page,@Field("rows")int rows);

    /*
    *  历史订单页面获取订单详情的post请求
    *  oid 订单id
    * */
    @POST("orderinfo/orderInfodetail.do")
    @FormUrlEncoded
    Call<OrderInfoBean> postOrderIdtoGetOrderInfo(@Field("oid") int oid);

/***************** 商户页面的请求 **********************************************************************************************************************************************/

    /*
    *  获取商户信息的post请求
    *  id 商户id
    * */
    @POST("custauthinfo/queryById.do")
    @FormUrlEncoded
    Call<UserCustInfoBean> postIdtoUserCustInfo(@Field("id") int id);

    /*
    *  商户页面获取商户下员工List的POST请求
    *  id 商户id
    *  page 页码
    *  rows 每页个数
    * */
    @POST("custemployee/queryCustEmpPageList.do")
    @FormUrlEncoded
    Call<UserStaffInfoBean> postIdtoUserStaffInfoList(@Field("id") int id,@Field("page") int page,@Field("rows") int rows);

    /*
    *  商户添加员工的post请求
    *  custId 商户id
    *  realname 员工姓名
    *  idcardNumber 身份证号
    *  tele 员工电话
    * */
    @POST("custemployee/save.do")
    @FormUrlEncoded
    Call<ResultBean> postcustInfotoAdd(@Field("custId") int custId,@Query("realname") String name,@Field("idcardNumber") String idcardNumber,@Field("tele") String tele);

    /*
    *  获取资金流水的Post请求
    *  id 商户id
    *  page 页码
    *  rows每页个数
    * */
    @POST("cashflow/queryCashFlowPageList.do")
    @FormUrlEncoded
    Call<UserMoneyFlowBean> postIdtoUserMoneyFlowList(@Field("cid") int id,@Field("page") int page,@Field("rows") int rows);

    /*
    *  获取资金流水详情的post请求
    *  id 流水id
    * */
    @POST("cashflow/queryCashFlowDetail.do")
    @FormUrlEncoded
    Call<FlowInfoBean> postFlowIdtoGetFlowInfo(@Field("id") int id);

    /*
    *  用户提现的post请求
    *  cid 商户id
    *  uid 用户id
    *  money 提现金额
    * */
    @POST("cashapply/custWithdrawsCash.do")
    @FormUrlEncoded
    Call<ResultBean> postMoneyToOutMoney(@Field("cid") int cid,@Field("money") double money);

    /*
    *  用户支付宝充值的post请求
    *  cid 商户id
    *  id 用户id
    *  money 充值金额
    * */
    @POST("orderinfo/zfbRecharge.do")
    @FormUrlEncoded
    Call<AliPayBean> postIdandMoneytoChongzhi(@Field("cid") int cid,@Field("money") float money);

    /*
    *  用户微信充值的post请求
    *  cid 商户id
    *  id 用户id
    *  money 充值金额
    * */
    @POST("weixin/wxAppCZPay.do")
    @FormUrlEncoded
    Call<WechatPayBean> postIdandMoneytowechatChongzhi(@Field("cid") int cid, @Field("money") float money);

    /*
    *  检查升级的接口
    *  versionCode 版本号
    *  appType APP类型
    * */
    @POST("appversion/queryAppNewVersion.do")
    @FormUrlEncoded
    Call<UpGradeBean> postAppCodetoUpGrade(@Field("versionCode") int versionCode,@Field("appType") int appType);

}
