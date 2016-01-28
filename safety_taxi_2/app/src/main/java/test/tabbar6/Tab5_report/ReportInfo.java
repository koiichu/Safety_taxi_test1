package test.tabbar6.Tab5_report;

/**
 * Created by KMITL on 18/01/2016.
 */
public class ReportInfo {
private String license_info;
private String checkbox_info;
private String comment_info;
private float rating_info;
private String  dateTime_info;
private String usernameReport_info;

        public ReportInfo(String license, String checkbox, String comment, float rating_query, String dateTime, String usernameReport_query) {
            this.license_info = license;
            this.checkbox_info = checkbox;
            this.comment_info = comment;
            this.rating_info = rating_query;
            this.dateTime_info = dateTime;
            this.usernameReport_info = usernameReport_query;
        }

        public String getLicense_info() {
            return license_info;
        }

        public void setLicense_info(String license_info) {
            this.license_info = license_info;
        }

        public String getCheckbox_info() {
            return checkbox_info;
        }

        public void setCheckbox_info(String checkbox_info) {
            this.checkbox_info = checkbox_info;
        }

        public String getComment_info() {
            return comment_info;
        }

        public void setComment_info(String comment_info) {
            this.comment_info = comment_info;
        }

        public float getRating_info() {
            return rating_info;
        }

        public void setRating_info(float rating_info) {
            this.rating_info = rating_info;
        }

        public String getUsernameReport_info() {
            return usernameReport_info;
        }

        public void setUsernameReport_info(String usernameReport_info) {
            this.usernameReport_info = usernameReport_info;
        }

        public String getDateTime_info() {
            return dateTime_info;
        }

        public void setDateTime_info(String dateTime_info) {
            this.dateTime_info = dateTime_info;
        }
}

