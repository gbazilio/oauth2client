Custom OAuth2 Android Client
==============================

The library was developed to fit specific needs of a client, which is to complete the OAuth Authorization Code Flow using Github as the OAuth2 Provider.

# Using the library

Get an instance of a provider's client:
```java
OAuth2Client oauthClient = OAuth2ClientFactory.newInstance(OAuth2ClientFactory.Provider.ENVATO);
```

Build the dialog fragment to show the login/authorization screen:
```java
OAuth2LoginFragment oAuth2LoginFragment = OAuth2LoginFragment.newInstance(oauthClient);
```

Set a listener responsible for intercepting the access token when appropriate:
```java
oAuth2LoginFragment.setOnAccessTokenRequestedListener(new AccessTokenRequested() {
    @Override
    public void onAccessTokenRequested(Token token) {
		Log.v("Provider", token.getProvider());
        Log.v("AccessToken", token.getAccessToken());        
    }
});
```

Be sure to make the fragment visible whenever you want (e.g. on clicking a button):
```java
FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
fragmentTransaction.addToBackStack(null);

oAuth2LoginFragment.show(fragmentTransaction, "oauth2_dialog");
```

# OAuth2 Clients

Implement the base class `com.gbazilio.oauth2client.oauth2.clients.interfaces.OAuth2Client` if more clients are needed.
