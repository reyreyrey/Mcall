package myapplication.modules.sms.getPhoneNum;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestApi;

public class GetPhoneNumApi implements IRequestApi {
    @NonNull
    @Override
    public String getApi() {
        return "api/get_mobile";
    }

    private String token;
    private String project_id = "23693";
    private String operator = "4";
    private String scope_black = "1700,1701,1702,162,1703,1705,1706,165,1704,1707,1708,1709,171,167,1349,174,140,141,144,146,148,195";

    public String getScope_black() {
        return scope_black;
    }

    public void setScope_black(String scope_black) {
        this.scope_black = scope_black;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getToken() {
        return token;
    }

    public GetPhoneNumApi setToken(String token) {
        this.token = token;
        return this;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }
}
