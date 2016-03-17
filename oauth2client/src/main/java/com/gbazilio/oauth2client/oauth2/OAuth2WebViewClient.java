package com.gbazilio.oauth2client.oauth2;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gbazilio.oauth2client.listeners.CodeCapturedListener;

import static com.gbazilio.oauth2client.utils.UrlQueryStringHelper.getParameter;
import static com.gbazilio.oauth2client.utils.UrlQueryStringHelper.hasParameterInUrl;

public class OAuth2WebViewClient extends WebViewClient {

    private static final String CODE_PARAMETER = "code";
    private static final String ERROR_PARAMETER = "error";

    private CodeCapturedListener onCodeCapturedListener;

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {

        if (hasParameterInUrl(CODE_PARAMETER, url)) {
            String code = getParameter(CODE_PARAMETER, url);
            this.onCodeCapturedListener.onCodeCaptured(code);
        } else if (hasParameterInUrl(ERROR_PARAMETER, url)) {
            this.onCodeCapturedListener.onCodeCaptured(null);
        }

        return false;
    }

    public void setOnCodeCapturedListener(CodeCapturedListener onCodeCapturedListener) {
        this.onCodeCapturedListener = onCodeCapturedListener;
    }
}
