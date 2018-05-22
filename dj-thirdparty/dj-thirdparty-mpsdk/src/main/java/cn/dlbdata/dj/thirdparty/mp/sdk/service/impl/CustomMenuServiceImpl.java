package cn.dlbdata.dj.thirdparty.mp.sdk.service.impl;

import cn.dlbdata.dj.common.DangjianException;
import cn.dlbdata.dj.common.rest.HttpWebApi;
import cn.dlbdata.dj.thirdparty.mp.sdk.constant.RequestUrls;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.AccessTokenResponse;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.common.BaseMpApiResponse;
import cn.dlbdata.dj.thirdparty.mp.sdk.service.BaseService;
import cn.dlbdata.dj.thirdparty.mp.sdk.service.CustomMenuService;
import cn.dlbdata.dj.thirdparty.mp.sdk.service.TokenBasedService;
import cn.dlbdata.dj.thirdparty.mp.sdk.util.TokenBasedHttpClient;

import org.springframework.stereotype.Service;

/**
 * @Author: linfujun
 * @Description:
 * @Date: Created on 16:57 2018/3/25
 */
public class CustomMenuServiceImpl extends TokenBasedService implements CustomMenuService {

    public CustomMenuServiceImpl(TokenBasedHttpClient client) {
        super(client);
    }

    @Override
    public BaseMpApiResponse createMenu(String json) throws DangjianException {
        return  postAtomEntity(RequestUrls.GREATE_MENU_PREFIX, json, BaseMpApiResponse.class);
    }

}
