package io.dango.controller;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

public class LoginControllerTest {
    String URL = "http://localhost:8080";

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

    @Test
    public void loginToken() throws IOException {
        String url = URL + "/oauth/token";
        Content content = Request.Post(url)
                .addHeader("Accept","application/json")
                .addHeader("Authorization","Basic YW5kcm9pZDpxaWRpYW4=")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                .bodyForm(
                        Form.form()
                                .add("password", "USER")
                                .add("response_type", "code")
                                .add("scope", "read write")
                                .add("grant_type", "password")
                                .add("username", "user")
                                .add("client_secret", "qidian")
                                .add("client_id", "android")
                                .build())
                .execute().returnContent();

        System.out.println(content.toString());
        JSONObject jsonObject = new JSONObject(content.toString());
        String token = jsonObject.getString("access_token");
    }

    @Test
    public void loginFeedBack() throws IOException {
        String url = URL + "/login";
        Content content = Request.Post(url)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .bodyString("{\"username\": \"user\",\"password\": \"USER\"}", ContentType.APPLICATION_JSON)
                .execute().returnContent();

        System.out.println(content.toString());
        JSONObject jsonObject = new JSONObject(content.toString());
        String token = jsonObject.getJSONObject("auth").getString("access_token");
        boolean needface = jsonObject.getJSONObject("user").getBoolean("needface");

        System.out.println(token +" "+ needface);

    }
}