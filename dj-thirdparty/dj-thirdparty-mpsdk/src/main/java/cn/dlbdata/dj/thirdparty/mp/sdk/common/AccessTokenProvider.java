package cn.dlbdata.dj.thirdparty.mp.sdk.common;

import cn.dlbdata.dj.common.DangjianException;

public interface AccessTokenProvider {
    public String getAccessToken() throws DangjianException;
}
