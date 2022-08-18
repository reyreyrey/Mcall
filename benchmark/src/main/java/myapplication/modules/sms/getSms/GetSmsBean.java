package myapplication.modules.sms.getSms;

import java.io.Serializable;
import java.util.List;

public class GetSmsBean {

        private String code;

        private List<Data> data;

        private String message;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<? extends Data> getData() {
            return this.data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public static class Data implements Serializable {
            private String project_type;

            private String project_id;

            private String phone;

            private String modle;

            public String getProject_type() {
                return this.project_type;
            }

            public void setProject_type(String project_type) {
                this.project_type = project_type;
            }

            public String getProject_id() {
                return this.project_id;
            }

            public void setProject_id(String project_id) {
                this.project_id = project_id;
            }

            public String getPhone() {
                return this.phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getModle() {
                return this.modle;
            }

            public void setModle(String modle) {
                this.modle = modle;
            }
        }

}
