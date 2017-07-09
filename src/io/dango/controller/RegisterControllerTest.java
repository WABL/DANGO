package io.dango.controller;

import io.dango.entity.User;
import io.dango.repository.JDBCUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.*;

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
    public void register() throws Exception {

    }

}