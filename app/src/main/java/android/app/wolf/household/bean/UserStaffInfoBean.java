package android.app.wolf.household.bean;

import java.util.List;

/**
 * Created by lh on 2017/10/18.
 * <p/>
 * 功能作用：
 * <p/>
 * 修改记录：
 */
public class UserStaffInfoBean {


    /**
     * pageNum : 1
     * pageSize : 1
     * rows : [{"admin":"","createTime":"2017-10-12 10:29:10","custId":1,"department":"清洁部","dr":0,"emplDesc":"","emplScore":"","emplServiceItems":"1","id":1,"idcardNumber":"14222541254587425","jobTitle":"清洁","modifyTime":"2017-10-12 10:29:10","realname":"张槎","status":1,"tele":"1234567894"}]
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
         * admin :
         * createTime : 2017-10-12 10:29:10
         * custId : 1
         * department : 清洁部
         * dr : 0
         * emplDesc :
         * emplScore :
         * emplServiceItems : 1
         * id : 1
         * idcardNumber : 14222541254587425
         * jobTitle : 清洁
         * modifyTime : 2017-10-12 10:29:10
         * realname : 张槎
         * status : 1
         * tele : 1234567894
         */

        private String admin;
        private String createTime;
        private int custId;
        private String department;
        private int dr;
        private String emplDesc;
        private String emplScore;
        private String emplServiceItems;
        private int id;
        private String idcardNumber;
        private String jobTitle;
        private String modifyTime;
        private String realname;
        private int status;
        private String tele;

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
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

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getDr() {
            return dr;
        }

        public void setDr(int dr) {
            this.dr = dr;
        }

        public String getEmplDesc() {
            return emplDesc;
        }

        public void setEmplDesc(String emplDesc) {
            this.emplDesc = emplDesc;
        }

        public String getEmplScore() {
            return emplScore;
        }

        public void setEmplScore(String emplScore) {
            this.emplScore = emplScore;
        }

        public String getEmplServiceItems() {
            return emplServiceItems;
        }

        public void setEmplServiceItems(String emplServiceItems) {
            this.emplServiceItems = emplServiceItems;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdcardNumber() {
            return idcardNumber;
        }

        public void setIdcardNumber(String idcardNumber) {
            this.idcardNumber = idcardNumber;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTele() {
            return tele;
        }

        public void setTele(String tele) {
            this.tele = tele;
        }
    }
}
