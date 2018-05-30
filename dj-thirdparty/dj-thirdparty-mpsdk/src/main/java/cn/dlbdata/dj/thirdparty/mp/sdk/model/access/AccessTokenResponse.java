package cn.dlbdata.dj.thirdparty.mp.sdk.model.access;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.dlbdata.dj.thirdparty.mp.sdk.model.common.BaseMpApiResponse;

public class AccessTokenResponse extends BaseMpApiResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
