package com.gbazilio.oauth2client.utils;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlQueryStringHelper {

    public static String getParameter(String parameterName, String url) {
        String pattern = "\\?.*" + parameterName + "=(.*)";
        Pattern r = Pattern.compile(pattern);

        Matcher matcher = r.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public static boolean hasParameterInUrl(String parameter, String url) {
        try {
            URL parsedUrl = new URL(url);
            return (parsedUrl.getQuery().contains(parameter + "=") ? true : false);
        } catch (Exception e) {
            return false;
        }
    }

}
