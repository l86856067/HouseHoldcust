package android.app.wolf.household.bean;

import java.util.List;

/**
 * Created by lh on 2017/10/18.
 * <p/>
 * 功能作用：
 * <p/>
 * 修改记录：
 */
public class UserMoneyFlowBean {


    /**
     * pageNum : 1
     * pageSize : 1
     * rows : [{"address":"","admin":"","cashType":1,"createTime":"","custId":1,"dr":0,"generateTime":"","id":1,"modifyTime":"","money":50,"orderId":1,"paymentAccount":"","paymentFlowId":"","paymentWay":1,"serviceName":"保洁","userIcon":"","userId":1,"username":"莫昂"}]
     * total : 2
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
         * address :
         * admin :
         * cashType : 1
         * createTime :
         * custId : 1
         * dr : 0
         * generateTime :
         * id : 1
         * modifyTime :
         * money : 50.0
         * orderId : 1
         * paymentAccount :
         * paymentFlowId :
         * paymentWay : 1
         * serviceName : 保洁
         * userIcon :
         * userId : 1
         * username : 莫昂
         */

        private String address;
        private String admin;
        private int cashType;
        private String createTime;
        private int custId;
        private int dr;
        private String generateTime;
        private int id;
        private int idType;
        private String modifyTime;
        private float money;
        private int orderId;
        private String paymentAccount;
        private String paymentFlowId;
        private int paymentWay;
        private String serviceName;
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

        public int getCashType() {
            return cashType;
        }

        public void setCashType(int cashType) {
            this.cashType = cashType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCustId() {
            return custId;
        }

        public void setCustId(int custId) {
            this.custId = custId;
        }

        public int getDr() {
            return dr;
        }

        public void setDr(int dr) {
            this.dr = dr;
        }

        public String getGenerateTime() {
            return generateTime;
        }

        public void setGenerateTime(String generateTime) {
            this.generateTime = generateTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdType() {
            return idType;
        }

        public void setIdType(int idType) {
            this.idType = idType;
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

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getPaymentAccount() {
            return paymentAccount;
        }

        public void setPaymentAccount(String paymentAccount) {
            this.paymentAccount = paymentAccount;
        }

        public String getPaymentFlowId() {
            return paymentFlowId;
        }

        public void setPaymentFlowId(String paymentFlowId) {
            this.paymentFlowId = paymentFlowId;
        }

        public int getPaymentWay() {
            return paymentWay;
        }

        public void setPaymentWay(int paymentWay) {
            this.paymentWay = paymentWay;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
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
