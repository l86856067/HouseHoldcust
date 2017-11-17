package android.app.wolf.household.bean;

/**
 * Created by lh on 2017/10/29.
 * <p/>
 * 功能作用：
 * <p/>
 * 修改记录：
 */
public class UpGradeBean {


    /**
     * data : {"downloadUrl":"http://public.gold-doctor.com/golddoctor-2.0.4-server.apk","updataType":1}
     * statusCode : 200
     * statusDesc : 查询成功
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
         * downloadUrl : http://public.gold-doctor.com/golddoctor-2.0.4-server.apk
         * updataType : 1
         */

        private String downloadUrl;
        private int updataType;

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public int getUpdataType() {
            return updataType;
        }

        public void setUpdataType(int updataType) {
            this.updataType = updataType;
        }
    }
}
