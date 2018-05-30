package cn.dlbdata.dj.thirdparty.mp.sdk.config;

import org.springframework.context.annotation.Configuration;

/**
 * @Author: linfujun
 * @Description: 默认配置常量
 * @Date: Created on 18:09 2017/12/12
 */
@Configuration
public class DefaultConfigs {

  public static String API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/";

  public void setApiUrlPrefix(String value) {
    API_URL_PREFIX = value;
  }

}
