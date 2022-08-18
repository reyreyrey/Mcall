package myapplication.base;

public class HttpData<T> {

    /**
     * 返回码
     */
    private int code;
    /**
     * 提示语
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 是否请求成功
     */
    public boolean isRequestSucceed() {
        return code == 1;
    }

    @Override
    public String toString() {
        return "HttpData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

