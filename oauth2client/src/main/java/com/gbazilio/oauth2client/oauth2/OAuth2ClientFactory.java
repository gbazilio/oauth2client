package com.gbazilio.oauth2client.oauth2;

import com.gbazilio.oauth2client.oauth2.clients.GithubClient;
import com.gbazilio.oauth2client.oauth2.clients.interfaces.OAuth2Client;

public class OAuth2ClientFactory {

    public enum Provider {
        GITHUB, ENVATO
    }

    public static OAuth2Client newInstance(Provider provider) {
        switch (provider) {
            case GITHUB:
                return new GithubClient();
            default:
                return new GithubClient();
        }
    }

}
