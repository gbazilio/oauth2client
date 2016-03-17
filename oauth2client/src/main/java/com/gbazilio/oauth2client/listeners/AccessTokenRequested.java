package com.gbazilio.oauth2client.listeners;

import com.gbazilio.oauth2client.oauth2.model.Token;

public interface AccessTokenRequested {

    void onAccessTokenRequested(Token token);

}
