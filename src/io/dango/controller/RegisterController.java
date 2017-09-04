package io.dango.controller;

import io.dango.entity.User;
import io.dango.pojo.DangoError;
import io.dango.repository.JDBCUserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 54472 on 2017/7/7.
 */

@RestController
public class RegisterController {

    @Autowired
    JDBCUserRepository jdbcUserRepository;

    @ExceptionHandler(JSONException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DangoError forbidden(JSONException e) {
        return new DangoError(100,"用户请求格式错误");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DangoError duiplicate(DuplicateKeyException e) {
        return new DangoError(101,"用户名被占用");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DangoError duiplicate(Exception e) {
        System.out.println(e.getLocalizedMessage());
        return new DangoError(102,"未知错误");
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public Map<String, Boolean> register(@RequestBody String json) throws JSONException {
            JSONObject jsonObject = new JSONObject(json);

            User user = new User();

            user.setUsername(jsonObject.getString("username"));
            user.setPassword(jsonObject.getString("password"));

            jdbcUserRepository.saveUser(user);

            System.out.println(user);

            Map<String, Boolean> map = new HashMap<>();
            map.put("success", true);

            return map;
    }
}
