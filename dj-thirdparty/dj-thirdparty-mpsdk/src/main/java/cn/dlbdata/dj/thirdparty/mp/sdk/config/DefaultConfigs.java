package cn.dlbdata.dj.thirdparty.mp.sdk.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfigs {

  public static String API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/";

  public void setApiUrlPrefix(String value) {
    API_URL_PREFIX = value;
  }

}
