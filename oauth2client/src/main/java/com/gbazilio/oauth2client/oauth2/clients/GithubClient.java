package com.gbazilio.oauth2client.oauth2.clients;

import com.gbazilio.oauth2client.oauth2.clients.interfaces.ScopeOAuth2Client;

import org.json.JSONException;
import org.json.JSONObject;

public class GithubClient implements ScopeOAuth2Client {

    private static final String CLIENT_ID = "7ac62eb7f5db860fb274";
    private static final String CLIENT_SECRET = "014d8a83034d040d4c209705db70fd29c1b60218";
    private static final String GITHUB_BASE_AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";

    private String scopes = "";

    @Override
    public String getAuthorizationUrl() {
        String url = GITHUB_BASE_AUTHORIZE_URL + "?client_id=" + CLIENT_ID;

        if (!scopes.isEmpty()) {
            url += "&scope=" + scopes;
        }

        return url;
    }

    @Override
    public String getTokenRequestBody(String code) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_id", CLIENT_ID);
            jsonObject.put("client_secret", CLIENT_SECRET);
            jsonObject.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @Override
    public String getTokenRequestUrl() {
        return GITHUB_TOKEN_URL;
    }

    @Override
    public String getProvider() {
        return "Github";
    }

    @Override
    public void setScope(String scope) {
        this.scopes = scope;
    }

}
