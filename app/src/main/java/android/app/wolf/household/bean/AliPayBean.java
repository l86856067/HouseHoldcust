package android.app.wolf.household.bean;

/**
 * Created by lh on 2017/10/27.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class AliPayBean {


    /**
     * data : {"body":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017102609532951&biz_content=%7B%22body%22%3A%22%E5%8A%A0%E8%8F%B2%E7%8C%AB%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%221509106223512-24%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%8A%A0%E8%8F%B2%E7%8C%AB%E7%94%A8%E6%88%B7%E4%B8%8B%E5%8D%95%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=https%3A%2F%2Fpublic.jiafeicat.com%2Forderinfo%2FendOrderPayNotify.do&sign=dgfr8ffnVXhLoUcjdfM73sXYQ1%2F0L2Lslxh%2F4zsAGbKCCQzK%2FdstD0PAZK%2FUkyTR%2FKTdKh%2B%2Bsg9Qgi%2Fwm972%2BjDVDD1yZo7lZrqV%2FvsHDbRJh3QRLfKfI6S9nQRaHghGsiwF%2BNUypeUvp3l26Giz4hZSRpIVjYk8rv8hCtfaOxSu7JaLj2Xmn6Gzs4nqsmgr0uNa7CtVOkZjwlvBJ3dxG9rpJAtf7jCp%2Bg9Z30Wu%2BIyLahPXDdfIMBsBBLDmLWeitIwUNYfP5MBSw36T1%2BKH%2FG2LgO1fNs6eLM6ws8B0Z8pctuzmA6G7W2YFLDv4Ab6QOof%2FYTuntPbWMWbnzlIlCA%3D%3D&sign_type=RSA2×tamp=2017-10-27+20%3A10%3A23&version=1.0","code":"","errorCode":"","msg":"","outTradeNo":"","params":null,"sellerId":"","subCode":"","subMsg":"","success":true,"totalAmount":"","tradeNo":""}
     * statusCode : 200
     * statusDesc : 成功
     */

    private DataBean data;
    private String statusCode;
    private String statusDesc;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public static class DataBean {
        /**
         * body : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017102609532951&biz_content=%7B%22body%22%3A%22%E5%8A%A0%E8%8F%B2%E7%8C%AB%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%221509106223512-24%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%8A%A0%E8%8F%B2%E7%8C%AB%E7%94%A8%E6%88%B7%E4%B8%8B%E5%8D%95%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=https%3A%2F%2Fpublic.jiafeicat.com%2Forderinfo%2FendOrderPayNotify.do&sign=dgfr8ffnVXhLoUcjdfM73sXYQ1%2F0L2Lslxh%2F4zsAGbKCCQzK%2FdstD0PAZK%2FUkyTR%2FKTdKh%2B%2Bsg9Qgi%2Fwm972%2BjDVDD1yZo7lZrqV%2FvsHDbRJh3QRLfKfI6S9nQRaHghGsiwF%2BNUypeUvp3l26Giz4hZSRpIVjYk8rv8hCtfaOxSu7JaLj2Xmn6Gzs4nqsmgr0uNa7CtVOkZjwlvBJ3dxG9rpJAtf7jCp%2Bg9Z30Wu%2BIyLahPXDdfIMBsBBLDmLWeitIwUNYfP5MBSw36T1%2BKH%2FG2LgO1fNs6eLM6ws8B0Z8pctuzmA6G7W2YFLDv4Ab6QOof%2FYTuntPbWMWbnzlIlCA%3D%3D&sign_type=RSA2×tamp=2017-10-27+20%3A10%3A23&version=1.0
         * code :
         * errorCode :
         * msg :
         * outTradeNo :
         * params : null
         * sellerId :
         * subCode :
         * subMsg :
         * success : true
         * totalAmount :
         * tradeNo :
         */

        private String body;
        private String code;
        private String errorCode;
        private String msg;
        private String outTradeNo;
        private Object params;
        private String sellerId;
        private String subCode;
        private String subMsg;
        private boolean success;
        private String totalAmount;
        private String tradeNo;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public Object getParams() {
            return params;
        }

        public void setParams(Object params) {
            this.params = params;
        }

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getSubCode() {
            return subCode;
        }

        public void setSubCode(String subCode) {
            this.subCode = subCode;
        }

        public String getSubMsg() {
            return subMsg;
        }

        public void setSubMsg(String subMsg) {
            this.subMsg = subMsg;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }
    }
}
