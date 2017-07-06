package io.dango.repository;

import io.dango.config.DBConfig;
import io.dango.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

/**
 * Created by MainasuK on 2017-7-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DBConfig.class })
public class JDBCUserRepositoryTest {

    @Autowired
    JDBCUserRepository userRepository;

    @Test
    public void getUserById() throws Exception {
        User user = userRepository.getUserById(1);
        Assert.notNull(user, "root");

        System.out.println(user.getPassword());
    }

}