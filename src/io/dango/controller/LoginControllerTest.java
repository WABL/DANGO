package io.dango.controller;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.*;

public class LoginControllerTest {

    @Test
    public void login() throws Exception {
        String username = "user";
        String password = "USER";

        OAuthClientRequest request = null;
        try {
            request = OAuthClientRequest
                    .authorizationLocation("http://localhost:8080/oauth/authorize")
                    .setClientId("android")
                    .setResponseType("code")
                    .buildQueryMessage();
        }
        catch (OAuthSystemException e) {
            System.out.println(e.getLocalizedMessage());
        }

        System.out.println(request.getLocationUri());
    }

}