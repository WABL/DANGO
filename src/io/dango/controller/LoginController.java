package io.dango.controller;

import io.dango.repository.JDBCUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 54472 on 2017/7/28.
 */

@RestController
public class LoginController {
    @Autowired
    JDBCUserRepository jdbcUserRepository;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Map<String, String> login(@RequestBody String json) {
        boolean isVerified;
        Map<String, String> map = new HashMap<>();
        String jwtToken = "";
        JSONObject jsonObject = new JSONObject(json);

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        try {
            isVerified = jdbcUserRepository.verify(username, password);
            if (isVerified) {
                jwtToken = Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, "secretKey").compact();
                map.put("token", jwtToken);
                map.put("status", "OK");
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("status","NO");
        return map;
    }
}
