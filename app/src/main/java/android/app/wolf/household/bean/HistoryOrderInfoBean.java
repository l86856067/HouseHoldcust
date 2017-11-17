package android.app.wolf.household.bean;

import java.util.List;

/**
 * Created by lh on 2017/10/17.
 * <p/>
 * 功能作用：
 * <p/>
 * 修改记录：
 */
public class HistoryOrderInfoBean {


    /**
     * pageNum : 1
     * pageSize : 1
     * rows : [{"address":"上海浦东新区芳华路","admin":"","applyTime":"2017-10-15 14:37:35","checker":"","contractNumber":"123","createTime":"","custEmplIds":"1","custId":1,"doneTime":"2017-10-16 14:38:27","dr":0,"endTime":"","id":1,"isAcceptAgreement":0,"lat":37.93659,"lng":111.888504,"loginTele":"17635660000","modifyTime":"","money":50.33,"orderStatus":4,"orderStatusDesc":"发过的","payMoney":60,"payTime":"","realname":"张槎","serviceBail":5,"serviceDesc":"","serviceInfo":"很好","serviceItemId":1,"serviceName":"","submitTime":"","tele":"1234567894","userIcon":"","userId":1,"username":"莫昂"}]
     * total : 1
     */

    private int pageNum;
    private int pageSize;
    private int total;
    private List<RowsBean> rows;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * address : 上海浦东新区芳华路
         * admin :
         * applyTime : 2017-10-15 14:37:35
         * checker :
         * contractNumber : 123
         * createTime :
         * custEmplIds : 1
         * custId : 1
         * doneTime : 2017-10-16 14:38:27
         * dr : 0
         * endTime :
         * id : 1
         * isAcceptAgreement : 0
         * lat : 37.93659
         * lng : 111.888504
         * loginTele : 17635660000
         * modifyTime :
         * money : 50.33
         * orderStatus : 4
         * orderStatusDesc : 发过的
         * payMoney : 60.0
         * payTime :
         * realname : 张槎
         * serviceBail : 5
         * serviceDesc :
         * serviceInfo : 很好
         * serviceItemId : 1
         * serviceName :
         * submitTime :
         * tele : 1234567894
         * userIcon :
         * userId : 1
         * username : 莫昂
         */

        private String address;
        private String admin;
        private String applyTime;
        private String checker;
        private String contractNumber;
        private String createTime;
        private String custEmplIds;
        private int custId;
        private String doneTime;
        private int dr;
        private String endTime;
        private int id;
        private int isAcceptAgreement;
        private double lat;
        private double lng;
        private String loginTele;
        private String modifyTime;
        private float money;
        private int orderStatus;
        private String orderStatusDesc;
        private float payMoney;
        private String payTime;
        private String realname;
        private int serviceBail;
        private String serviceDesc;
        private String serviceInfo;
        private int serviceItemId;
        private String serviceName;
        private String submitTime;
        private String tele;
        private String userIcon;
        private int userId;
        private String username;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getChecker() {
            return checker;
        }

        public void setChecker(String checker) {
            this.checker = checker;
        }

        public String getContractNumber() {
            return contractNumber;
        }

        public void setContractNumber(String contractNumber) {
            this.contractNumber = contractNumber;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCustEmplIds() {
            return custEmplIds;
        }

        public void setCustEmplIds(String custEmplIds) {
            this.custEmplIds = custEmplIds;
        }

        public int getCustId() {
            return custId;
        }

        public void setCustId(int custId) {
            this.custId = custId;
        }

        public String getDoneTime() {
            return doneTime;
        }

        public void setDoneTime(String doneTime) {
            this.doneTime = doneTime;
        }

        public int getDr() {
            return dr;
        }

        public void setDr(int dr) {
            this.dr = dr;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsAcceptAgreement() {
            return isAcceptAgreement;
        }

        public void setIsAcceptAgreement(int isAcceptAgreement) {
            this.isAcceptAgreement = isAcceptAgreement;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getLoginTele() {
            return loginTele;
        }

        public void setLoginTele(String loginTele) {
            this.loginTele = loginTele;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusDesc() {
            return orderStatusDesc;
        }

        public void setOrderStatusDesc(String orderStatusDesc) {
            this.orderStatusDesc = orderStatusDesc;
        }

        public float getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(float payMoney) {
            this.payMoney = payMoney;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getServiceBail() {
            return serviceBail;
        }

        public void setServiceBail(int serviceBail) {
            this.serviceBail = serviceBail;
        }

        public String getServiceDesc() {
            return serviceDesc;
        }

        public void setServiceDesc(String serviceDesc) {
            this.serviceDesc = serviceDesc;
        }

        public String getServiceInfo() {
            return serviceInfo;
        }

        public void setServiceInfo(String serviceInfo) {
            this.serviceInfo = serviceInfo;
        }

        public int getServiceItemId() {
            return serviceItemId;
        }

        public void setServiceItemId(int serviceItemId) {
            this.serviceItemId = serviceItemId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(String submitTime) {
            this.submitTime = submitTime;
        }

        public String getTele() {
            return tele;
        }

        public void setTele(String tele) {
            this.tele = tele;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
