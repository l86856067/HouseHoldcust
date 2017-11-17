package android.app.wolf.household.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lh on 2017/11/11.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class WechatPayBean {


    /**
     * data : {"appid":"wx62834e4cb5377fbb","noncestr":"4b450d2df67b4f86b514b51b2ff790d7","package":"Sign=WXPay","partnerid":"1491853762","paySign":"606D1476796AB790B5A4F5743A4894F6","prepayid":"wx20171113093508aa860a106f0704724704","timestamp":"1510536908"}
     * statusCode : 200
     * statusDesc : 已生成微信公众号预支付参数
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
         * appid : wx62834e4cb5377fbb
         * noncestr : 4b450d2df67b4f86b514b51b2ff790d7
         * package : Sign=WXPay
         * partnerid : 1491853762
         * paySign : 606D1476796AB790B5A4F5743A4894F6
         * prepayid : wx20171113093508aa860a106f0704724704
         * timestamp : 1510536908
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String paySign;
        private String prepayid;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPaySign() {
            return paySign;
        }

        public void setPaySign(String paySign) {
            this.paySign = paySign;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
