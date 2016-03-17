package com.gbazilio.oauth2client.oauth2.clients.interfaces;

public interface OAuth2Client {

    String getAuthorizationUrl();

    String getTokenRequestBody(String code);

    String getTokenRequestUrl();

    String getProvider();

}
