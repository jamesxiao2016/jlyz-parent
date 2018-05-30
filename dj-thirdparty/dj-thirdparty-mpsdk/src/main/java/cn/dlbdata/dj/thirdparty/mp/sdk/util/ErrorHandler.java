package cn.dlbdata.dj.thirdparty.mp.sdk.util;

import cn.dlbdata.dj.common.DangjianException;

/**
 * @Description: 错误码
 */
public class ErrorHandler {
    public static final int PARSE_TO_OBJECT_ERROR = 110000;
    public static final int ACCESS_TOKEN_NOT_SET = 110001;

    public static DangjianException accessTokenNotSet() {
        return new DangjianException(ACCESS_TOKEN_NOT_SET, "This method needs access token to gain accessability");
    }
}
