package cn.dlbdata.dj.thirdparty.mp.sdk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: linfujun
 * @Description: 默认配置常量
 * @Date: Created on 18:09 2017/12/12
 */
@Configuration
public class DefaultConfigs {

  public static String API_URL_PREFIX;

  public void setApiUrlPrefix(String value) {
    API_URL_PREFIX = value;
  }

}
