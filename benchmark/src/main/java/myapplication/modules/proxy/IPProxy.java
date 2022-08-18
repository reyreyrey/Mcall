package myapplication.modules.proxy;

import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.gson.GsonBuilder;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.model.ResponseClass;
import com.lzy.okgo.OkGo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;

import myapplication.ui.MainActivity;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class IPProxy {

    private static IPProxyBean.Obj getProxyConfig(FragmentActivity activity) {
        try {
            Thread.sleep(1000);
            String url = "http://pandavip.xiongmaodaili.com/xiongmao-web/apiPlus/vgb?secret=7f1676d501de4c881bf1c31148c84027&orderNo=VGB20220815182350WOaqowAS&count=10&isTxt=0&proxyType=1&validTime=0&removal=0&cityIds=";
            Response response = OkGo.get(url)
                    .execute();
            String json = response.body().string();
            Log.e("---->", json);
            IPProxyBean ipProxyBean = new GsonBuilder().create().fromJson(json, IPProxyBean.class);
            if (ipProxyBean != null)
                return ipProxyBean.getObj().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long requestTime;

    public static IPProxyBean.Obj setProxy(FragmentActivity activity) {
        if (requestTime == 0){
            requestTime = new Date().getTime();
        }else{
            long current = new Date().getTime();
            long t = current - requestTime;
            if(t < 11 * 1000){
                try {
                    Thread.sleep(11*1000 - t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        IPProxyBean.Obj obj = IPProxy.getProxyConfig(activity);
        if(obj == null || TextUtils.isEmpty(obj.getIp()) ) return null;
        if (!isOnline(obj.getIp(), obj.getPort())) {

            setProxy(activity);
        }

        Log.e("----->", "ip是通的");
        //开始设置代理
        OkHttpClient client = EasyConfig.getInstance().getClient();
        client = client.newBuilder().proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(obj.getIp(), obj.getPort()))).build();
        EasyConfig.getInstance().setClient(client);
        requestTime = new Date().getTime();
        return obj;
    }

    public static boolean isOnline(String path, int port) {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress(path, port);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) {
            Log.e("----->重新获取ip设置代理", e.toString());
            return false;
        }
    }

}
