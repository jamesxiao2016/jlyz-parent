package cn.dlbdata.dj.thirdparty.mp.sdk.service.impl;

import cn.dlbdata.dj.common.DangjianException;
import cn.dlbdata.dj.common.rest.HttpWebApi;
import cn.dlbdata.dj.thirdparty.mp.sdk.constant.RequestUrls;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.AccessTokenResponse;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.GetaAccessTokenParam;
import cn.dlbdata.dj.thirdparty.mp.sdk.service.AccessService;
import cn.dlbdata.dj.thirdparty.mp.sdk.service.BaseService;

import org.springframework.stereotype.Service;

/**
 * @Author: linfujun
 * @Description: 腾讯公众平台访问服务实现
 * @Date: Created on 0:44 2018/3/25
 */
public class AccessServiceImpl extends BaseService implements AccessService {
    public AccessServiceImpl(HttpWebApi client) {
        super(client);
    }

    @Override
    public AccessTokenResponse getAccessToken(GetaAccessTokenParam param) throws DangjianException {
        return getResponse(RequestUrls.GAIN_ACCESS_TOKEN_PREFIX, param, AccessTokenResponse.class);
    }
}
