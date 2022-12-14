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
import myapplication.utils.Config;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class IPProxy {
    private static String userName = "183E6A05";
    private static String password = "9439B58A4FC5";
    private static IPProxyBean.Obj getProxyConfig() {
        try {
            Thread.sleep(1000);
            //String url = "http://pandavip.xiongmaodaili.com/xiongmao-web/apiPlus/vgb?secret=7f1676d501de4c881bf1c31148c84027&orderNo=VGB20220815182350WOaqowAS&count=10&isTxt=0&proxyType=1&validTime=0&removal=0&cityIds=";
//           String url = "http://www.siyetian.com/index/apis_get.html?token=gHbi1yTUVVeNRVVw0ERRhXTR1STqFUeNpWQ00kaNBjTEVlMPRUSx0ERRFjTqdGN.gN4UTOxMTM2YTM&limit=1&type=0&time=0&split=0&split_text=&area=0&repeat=0&isp=";
//            String url = Config.getConfig().getIpProxyUrl();
            String url = "https://proxy.qg.net/extract?Key=183E6A05";
           if(TextUtils.isEmpty(url))return null;
            Response response = OkGo.get(url)
                    .execute();
            String json = response.body().string();
            Log.e("---->", json);
//            String ip = json.split(":")[0];
//            int port = Integer.parseInt(json.split(":")[1]);
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
        IPProxyBean.Obj obj = IPProxy.getProxyConfig();
        if(obj == null || TextUtils.isEmpty(obj.getIp()) ) return new IPProxyBean.Obj();
        if (!isOnline(obj.getIp(), obj.getPort())) {

            setProxy(activity);
        }

        Log.e("----->", "ip?????????");
        //??????????????????
        OkHttpClient client = EasyConfig.getInstance().getClient();
        Authenticator proxyAuthenticator = new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(userName, password);
                return response.request().newBuilder()
                        .header("Proxy-Authorization", credential)
                        .build();
            }
        };
        client = client.newBuilder().proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(obj.getIp(), obj.getPort())))
                .proxyAuthenticator(proxyAuthenticator)
                .build();
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
            Log.e("----->????????????ip????????????", e.toString());
            return false;
        }
    }

}
