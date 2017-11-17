package android.app.wolf.household.bean;

/**
 * Created by lh on 2017/10/16.
 * <p/>
 * 功能作用：
 * <p/>
 * 修改记录：
 */
public class LoginResultBean {


    /**
     * data : {"custAuthInfo":{"address":"","admin":"","availMoney":2200.07,"createTime":"2017-10-18 16:54:20","custAuthDesc":"gdfgadgad","custAuthRet":1,"custDesc":"欠费发啥","custIcon":"","custManage":"","custName":"777","custNumber":"123456","custScore":"10","custServiceArea":"","custServiceItems":"","distance":0,"dr":0,"email":"","emergencyContact1":"","emergencyContact2":"","emergencyTele1":"","emergencyTele2":"","id":1,"landline":"","lat":31.230796,"lng":121.63838,"loginTele":"18835550000","modifyTime":"2017-10-28 12:08:23","nannyCount":null,"password":"c4ca4238a0b923820dcc509a6f75849b","qq":"","weixinNumber":"18835550000"}}
     * statusCode : 200
     * statusDesc : 登录成功
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
         * custAuthInfo : {"address":"","admin":"","availMoney":2200.07,"createTime":"2017-10-18 16:54:20","custAuthDesc":"gdfgadgad","custAuthRet":1,"custDesc":"欠费发啥","custIcon":"","custManage":"","custName":"777","custNumber":"123456","custScore":"10","custServiceArea":"","custServiceItems":"","distance":0,"dr":0,"email":"","emergencyContact1":"","emergencyContact2":"","emergencyTele1":"","emergencyTele2":"","id":1,"landline":"","lat":31.230796,"lng":121.63838,"loginTele":"18835550000","modifyTime":"2017-10-28 12:08:23","nannyCount":null,"password":"c4ca4238a0b923820dcc509a6f75849b","qq":"","weixinNumber":"18835550000"}
         */

        private CustAuthInfoBean custAuthInfo;

        public CustAuthInfoBean getCustAuthInfo() {
            return custAuthInfo;
        }

        public void setCustAuthInfo(CustAuthInfoBean custAuthInfo) {
            this.custAuthInfo = custAuthInfo;
        }

        public static class CustAuthInfoBean {
            /**
             * address :
             * admin :
             * availMoney : 2200.07
             * createTime : 2017-10-18 16:54:20
             * custAuthDesc : gdfgadgad
             * custAuthRet : 1
             * custDesc : 欠费发啥
             * custIcon :
             * custManage :
             * custName : 777
             * custNumber : 123456
             * custScore : 10
             * custServiceArea :
             * custServiceItems :
             * distance : 0
             * dr : 0
             * email :
             * emergencyContact1 :
             * emergencyContact2 :
             * emergencyTele1 :
             * emergencyTele2 :
             * id : 1
             * landline :
             * lat : 31.230796
             * lng : 121.63838
             * loginTele : 18835550000
             * modifyTime : 2017-10-28 12:08:23
             * nannyCount : null
             * password : c4ca4238a0b923820dcc509a6f75849b
             * qq :
             * weixinNumber : 18835550000
             */

            private String address;
            private String admin;
            private float availMoney;
            private String createTime;
            private String custAuthDesc;
            private int custAuthRet;
            private String custDesc;
            private String custIcon;
            private String custManage;
            private String custName;
            private String custNumber;
            private String custScore;
            private String custServiceArea;
            private String custServiceItems;
            private int distance;
            private int dr;
            private String email;
            private String emergencyContact1;
            private String emergencyContact2;
            private String emergencyTele1;
            private String emergencyTele2;
            private int id;
            private String landline;
            private double lat;
            private double lng;
            private String loginTele;
            private String modifyTime;
            private Object nannyCount;
            private String password;
            private String qq;
            private String weixinNumber;

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

            public float getAvailMoney() {
                return availMoney;
            }

            public void setAvailMoney(float availMoney) {
                this.availMoney = availMoney;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCustAuthDesc() {
                return custAuthDesc;
            }

            public void setCustAuthDesc(String custAuthDesc) {
                this.custAuthDesc = custAuthDesc;
            }

            public int getCustAuthRet() {
                return custAuthRet;
            }

            public void setCustAuthRet(int custAuthRet) {
                this.custAuthRet = custAuthRet;
            }

            public String getCustDesc() {
                return custDesc;
            }

            public void setCustDesc(String custDesc) {
                this.custDesc = custDesc;
            }

            public String getCustIcon() {
                return custIcon;
            }

            public void setCustIcon(String custIcon) {
                this.custIcon = custIcon;
            }

            public String getCustManage() {
                return custManage;
            }

            public void setCustManage(String custManage) {
                this.custManage = custManage;
            }

            public String getCustName() {
                return custName;
            }

            public void setCustName(String custName) {
                this.custName = custName;
            }

            public String getCustNumber() {
                return custNumber;
            }

            public void setCustNumber(String custNumber) {
                this.custNumber = custNumber;
            }

            public String getCustScore() {
                return custScore;
            }

            public void setCustScore(String custScore) {
                this.custScore = custScore;
            }

            public String getCustServiceArea() {
                return custServiceArea;
            }

            public void setCustServiceArea(String custServiceArea) {
                this.custServiceArea = custServiceArea;
            }

            public String getCustServiceItems() {
                return custServiceItems;
            }

            public void setCustServiceItems(String custServiceItems) {
                this.custServiceItems = custServiceItems;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public int getDr() {
                return dr;
            }

            public void setDr(int dr) {
                this.dr = dr;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getEmergencyContact1() {
                return emergencyContact1;
            }

            public void setEmergencyContact1(String emergencyContact1) {
                this.emergencyContact1 = emergencyContact1;
            }

            public String getEmergencyContact2() {
                return emergencyContact2;
            }

            public void setEmergencyContact2(String emergencyContact2) {
                this.emergencyContact2 = emergencyContact2;
            }

            public String getEmergencyTele1() {
                return emergencyTele1;
            }

            public void setEmergencyTele1(String emergencyTele1) {
                this.emergencyTele1 = emergencyTele1;
            }

            public String getEmergencyTele2() {
                return emergencyTele2;
            }

            public void setEmergencyTele2(String emergencyTele2) {
                this.emergencyTele2 = emergencyTele2;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLandline() {
                return landline;
            }

            public void setLandline(String landline) {
                this.landline = landline;
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

            public Object getNannyCount() {
                return nannyCount;
            }

            public void setNannyCount(Object nannyCount) {
                this.nannyCount = nannyCount;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getWeixinNumber() {
                return weixinNumber;
            }

            public void setWeixinNumber(String weixinNumber) {
                this.weixinNumber = weixinNumber;
            }
        }
    }
}