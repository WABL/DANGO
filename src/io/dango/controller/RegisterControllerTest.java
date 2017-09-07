package io.dango.controller;

import io.dango.entity.User;
import io.dango.repository.JDBCUserRepository;
import okhttp3.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by MainasuK on 2017-7-7.
 */
public class RegisterControllerTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private JDBCUserRepository jdbcUserRepository;
    private User user;

    @Before
    public void before() {
        jdbcUserRepository = new JDBCUserRepository(jdbcTemplate);
        user = new User();

        user.setUsername("unitTestUser");
        user.setPassword("unitTestPassword");

        jdbcUserRepository.removeByUsername(user.getUsername());
    }

    @Test
    public void upload() throws Exception {
        File file = new File("C:\\Users\\54472\\Desktop\\test.jpg");
        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        client = builder.build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("photo", "test.jpg", requestBody)
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:8080/face/upload/register")
                .post(multipartBody)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer 068cb86f-f77c-4731-91e4-0ae6453d385d")
                .addHeader("Content-Type", "multipart/form-data; charset=utf-8;")
                .build();

        Response response = client.newCall(request).execute();
        if(response.isSuccessful()) {
            String result = response.body().string();
            System.out.println(result);
        } else {
            System.out.println("mistake");
        }

    }

}