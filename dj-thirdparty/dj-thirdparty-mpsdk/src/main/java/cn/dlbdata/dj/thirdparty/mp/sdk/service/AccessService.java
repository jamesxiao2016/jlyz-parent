package cn.dlbdata.dj.thirdparty.mp.sdk.service;

import cn.dlbdata.dj.common.DangjianException;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.AccessTokenResponse;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.GetaAccessTokenParam;

/**
 * @Description: 腾讯公众平台访问服务
 */
public interface AccessService {
    AccessTokenResponse getAccessToken(GetaAccessTokenParam param) throws DangjianException;
}
