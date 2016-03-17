package com.gbazilio.oauth2client;

import com.gbazilio.oauth2client.utils.UrlQueryStringHelper;

import junit.framework.Assert;

import org.junit.Test;


/**
 * Created by gbazilio on 3/8/16.
 */

public class UrlQueryStringHelperTest {

    @Test
    public void shouldReturnQueryStringValue() {
        String expected = "1230323fdfd";
        String result = UrlQueryStringHelper.getParameter("code", "https://github.com/?code=" + expected);
        Assert.assertEquals(expected, result);
    }
}
