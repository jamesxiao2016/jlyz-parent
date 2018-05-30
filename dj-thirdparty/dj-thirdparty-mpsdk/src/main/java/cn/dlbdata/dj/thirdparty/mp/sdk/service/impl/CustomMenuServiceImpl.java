package cn.dlbdata.dj.thirdparty.mp.sdk.service.impl;

import cn.dlbdata.dj.common.DangjianException;
import cn.dlbdata.dj.thirdparty.mp.sdk.constant.RequestUrls;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.common.BaseMpApiResponse;
import cn.dlbdata.dj.thirdparty.mp.sdk.service.CustomMenuService;
import cn.dlbdata.dj.thirdparty.mp.sdk.service.TokenBasedService;
import cn.dlbdata.dj.thirdparty.mp.sdk.util.TokenBasedHttpClient;


public class CustomMenuServiceImpl extends TokenBasedService implements CustomMenuService {

    public CustomMenuServiceImpl(TokenBasedHttpClient client) {
        super(client);
    }

    @Override
    public BaseMpApiResponse createMenu(String json) throws DangjianException {
        return  postAtomEntity(RequestUrls.GREATE_MENU_PREFIX, json, BaseMpApiResponse.class);
    }

}
