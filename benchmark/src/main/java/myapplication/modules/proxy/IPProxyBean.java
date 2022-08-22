package myapplication.modules.proxy;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

public class IPProxyBean {


        private String msg;

        private Integer errno;

        private String code;

        private List<Obj> obj;

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getErrno() {
            return this.errno;
        }

        public void setErrno(Integer errno) {
            this.errno = errno;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<Obj> getObj() {
            return this.obj;
        }

        public void setObj(List<Obj> obj) {
            this.obj = obj;
        }

        public static class Obj implements Serializable {
            private String port;

            private String ip;

            public int getPort() {
                if(TextUtils.isEmpty(port))return 0;
                return Integer.parseInt(this.port);
            }

            public void setPort(String port) {
                this.port = port;
            }

            public String getIp() {
                return this.ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }
        }



}
