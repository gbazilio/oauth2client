package com.gbazilio.oauth2client.oauth2;


import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.gbazilio.oauth2client.R;
import com.gbazilio.oauth2client.listeners.AccessTokenRequested;
import com.gbazilio.oauth2client.listeners.CodeCapturedListener;
import com.gbazilio.oauth2client.oauth2.clients.interfaces.OAuth2Client;
import com.gbazilio.oauth2client.oauth2.model.Token;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OAuth2LoginFragment extends DialogFragment implements CodeCapturedListener {

    private WebView webView;
    private OAuth2Client oAuth2Client;

    private AccessTokenRequested accessTokenRequestedListener;
    private final OkHttpClient client = new OkHttpClient();

    public static OAuth2LoginFragment newInstance(OAuth2Client oAuth2Client) {
        OAuth2LoginFragment fragment = new OAuth2LoginFragment();
        fragment.oAuth2Client = oAuth2Client;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        String authorizationURl = oAuth2Client.getAuthorizationUrl();
        webView.loadUrl(authorizationURl);

        OAuth2WebViewClient oAuth2WebViewClient = new OAuth2WebViewClient();
        oAuth2WebViewClient.setOnCodeCapturedListener(this);
        webView.setWebViewClient(oAuth2WebViewClient);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {

        View webViewLayout = layoutInflater.inflate(R.layout.oauth2_fragment, container, false);
        webView = (WebView) webViewLayout.findViewById(R.id.webview);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return webViewLayout;
    }

    public void setOnAccessTokenRequestedListener(AccessTokenRequested listener) {
        this.accessTokenRequestedListener = listener;
    }

    @Override
    public void onCodeCaptured(final String code) {

        webView.setVisibility(View.INVISIBLE);

        if (code == null) {
            accessTokenRequestedListener.onAccessTokenRequested(null);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, oAuth2Client.getTokenRequestBody(code));

                Request request = new Request.Builder()
                        .url(oAuth2Client.getTokenRequestUrl())
                        .post(body)
                        .header("Accept", "application/json")
                        .build();

                String jsonResponse = "";
                try {
                    Response response = client.newCall(request).execute();
                    jsonResponse = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("HTTP Request", e.getMessage());
                } finally {
                    Gson gson = new Gson();
                    Token token = gson.fromJson(jsonResponse, Token.class);
                    token.setProvider(oAuth2Client.getProvider());

                    accessTokenRequestedListener.onAccessTokenRequested(token);
                }
            }
        }).start();

    }

}