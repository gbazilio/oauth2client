package com.gbazilio.oauth2client.oauth2.model;

public class Token {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;

    private String provider;

    public String getAccessToken() {
        return access_token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public String getTokenType() {
        return token_type;
    }

    public int getExpiresIn() {
        return expires_in;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }
}
