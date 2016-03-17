package com.gbazilio.oauth2client;

import com.gbazilio.oauth2client.oauth2.clients.GithubClient;

import org.junit.Test;

import static com.gbazilio.oauth2client.utils.UrlQueryStringHelper.getParameter;
import static junit.framework.Assert.assertEquals;

/**
 * Created by bazilio on 14/03/16.
 */
public class GithubClientTest {
    
    @Test
    public void shouldAppendScopeInUrlWhenSetByClient() {
        String expectedScope = "user";
        
        GithubClient sut = new GithubClient();
        sut.setScope("user");
        
        assertEquals(expectedScope, getParameter("scope", sut.getAuthorizationUrl()));
    }
    
}
