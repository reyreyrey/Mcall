package myapplication.base;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestServer;
import com.hjq.http.model.BodyType;

public class RequestServer implements IRequestServer {

    @NonNull
    @Override
    public String getHost() {
//        return "https://noface.vjlnw.com/api/v1?lang=zh-Hans";
//        return "https://www.baidu.com";
        return "https://face.guayh.com/api/v1?lang=zh-Hans";
    }

    @NonNull
    @Override
    public BodyType getBodyType() {
        // 参数以 Json 格式提交（默认是表单）
        return BodyType.FORM;
    }
}