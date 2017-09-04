package io.dango.controller;

import io.dango.entity.User;
import io.dango.repository.JDBCUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    JDBCUserRepository jdbcUserRepository;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> map = new HashMap();
        try {
            User user = jdbcUserRepository.verify(username, password);
            if (null != user) {
                map.put("data", user);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }
}
