package myapplication.modules.config;

import java.io.Serializable;
import java.lang.String;

public class ConfigBean implements Serializable {
  private String qq;

  private String wsServer;

  private String fileServerSwitch;

  private String rtcHost;

  private String coTurnUserName;

  private String signurl;

  private String secret;

  private OSS OSS;

  private String fileServer;

  private String fileToken;

  private String coTurnPwd;

  private String coTurnServer;

  private String longurl;

  private String appkey;

  private String signServer;

  private String orgServer;

  private AWS AWS;

  private String mainServer;

  public String getQq() {
    return this.qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getWsServer() {
    return this.wsServer;
  }

  public void setWsServer(String wsServer) {
    this.wsServer = wsServer;
  }

  public String getFileServerSwitch() {
    return this.fileServerSwitch;
  }

  public void setFileServerSwitch(String fileServerSwitch) {
    this.fileServerSwitch = fileServerSwitch;
  }

  public String getRtcHost() {
    return this.rtcHost;
  }

  public void setRtcHost(String rtcHost) {
    this.rtcHost = rtcHost;
  }

  public String getCoTurnUserName() {
    return this.coTurnUserName;
  }

  public void setCoTurnUserName(String coTurnUserName) {
    this.coTurnUserName = coTurnUserName;
  }

  public String getSignurl() {
    return this.signurl;
  }

  public void setSignurl(String signurl) {
    this.signurl = signurl;
  }

  public String getSecret() {
    return this.secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public OSS getOSS() {
    return this.OSS;
  }

  public void setOSS(OSS OSS) {
    this.OSS = OSS;
  }

  public String getFileServer() {
    return this.fileServer;
  }

  public void setFileServer(String fileServer) {
    this.fileServer = fileServer;
  }

  public String getFileToken() {
    return this.fileToken;
  }

  public void setFileToken(String fileToken) {
    this.fileToken = fileToken;
  }

  public String getCoTurnPwd() {
    return this.coTurnPwd;
  }

  public void setCoTurnPwd(String coTurnPwd) {
    this.coTurnPwd = coTurnPwd;
  }

  public String getCoTurnServer() {
    return this.coTurnServer;
  }

  public void setCoTurnServer(String coTurnServer) {
    this.coTurnServer = coTurnServer;
  }

  public String getLongurl() {
    return this.longurl;
  }

  public void setLongurl(String longurl) {
    this.longurl = longurl;
  }

  public String getAppkey() {
    return this.appkey;
  }

  public void setAppkey(String appkey) {
    this.appkey = appkey;
  }

  public String getSignServer() {
    return this.signServer;
  }

  public void setSignServer(String signServer) {
    this.signServer = signServer;
  }

  public String getOrgServer() {
    return this.orgServer;
  }

  public void setOrgServer(String orgServer) {
    this.orgServer = orgServer;
  }

  public AWS getAWS() {
    return this.AWS;
  }

  public void setAWS(AWS AWS) {
    this.AWS = AWS;
  }

  public String getMainServer() {
    return this.mainServer;
  }

  public void setMainServer(String mainServer) {
    this.mainServer = mainServer;
  }

  public static class OSS implements Serializable {
    private String downLoadUrl;

    private String bucketName;

    private String endpoint;

    private String appServerUrl;

    public String getDownLoadUrl() {
      return this.downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
      this.downLoadUrl = downLoadUrl;
    }

    public String getBucketName() {
      return this.bucketName;
    }

    public void setBucketName(String bucketName) {
      this.bucketName = bucketName;
    }

    public String getEndpoint() {
      return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
      this.endpoint = endpoint;
    }

    public String getAppServerUrl() {
      return this.appServerUrl;
    }

    public void setAppServerUrl(String appServerUrl) {
      this.appServerUrl = appServerUrl;
    }
  }

  public static class AWS implements Serializable {
    private String bucketName;

    private String SecretKey;

    private String endpoint;

    private String apiUrl;

    private String AccessKey;

    public String getBucketName() {
      return this.bucketName;
    }

    public void setBucketName(String bucketName) {
      this.bucketName = bucketName;
    }

    public String getSecretKey() {
      return this.SecretKey;
    }

    public void setSecretKey(String SecretKey) {
      this.SecretKey = SecretKey;
    }

    public String getEndpoint() {
      return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
      this.endpoint = endpoint;
    }

    public String getApiUrl() {
      return this.apiUrl;
    }

    public void setApiUrl(String apiUrl) {
      this.apiUrl = apiUrl;
    }

    public String getAccessKey() {
      return this.AccessKey;
    }

    public void setAccessKey(String AccessKey) {
      this.AccessKey = AccessKey;
    }
  }
}
