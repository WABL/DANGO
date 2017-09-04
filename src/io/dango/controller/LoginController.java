package io.dango.controller;

import io.dango.entity.User;
import io.dango.pojo.DangoError;
import io.dango.repository.JDBCUserRepository;
import io.dango.utility.JSONTool;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    JDBCUserRepository jdbcUserRepository;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DangoError unknown(Exception e) {
        System.out.println(e.getLocalizedMessage());
        e.printStackTrace();
        return new DangoError(102,"未知错误");
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(@RequestBody String json) throws IOException {
        JSONObject jsonObject = new JSONObject(json);
        final String username = jsonObject.getString("username");
        final String password = jsonObject.getString("password");

        final String URL = "http://localhost:8080";
        String url = URL + "/oauth/token";

        String authString = "android" + ":" + "qidian";
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes("UTF-8")));

        Content content = Request.Post(url)
                .addHeader("Accept","application/json")
                .addHeader("Authorization","Basic " + authStringEnc)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                .bodyForm(
                        Form.form()
                                .add("username", username)
                                .add("password", password)
                                .add("response_type", "code")
                                .add("scope", "read write")
                                .add("grant_type", "password")
                                .add("client_secret", "qidian")
                                .add("client_id", "android")
                                .build())
                .execute().returnContent();

        User user = jdbcUserRepository.getUserByUsername(username);
        JSONObject object = new JSONObject(content.toString());
        Map<String, Object> auth = JSONTool.toMap(object);

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("auth", auth);


        return ResponseEntity.ok(map);
    }

    @RequestMapping(path = "/userinfo", method = RequestMethod.GET)
    public ResponseEntity<User> userinfo(Principal principal) {

        String username = principal.getName();
        return ResponseEntity.ok(jdbcUserRepository.getUserByUsername(username));
    }
}
