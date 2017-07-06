package io.dango.repository;

import io.dango.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by MainasuK on 2017-7-6.
 */
@Repository
public class JDBCUserRepository implements UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public User getUserById(long id) {
        return jdbcTemplate.queryForObject("select * from user u where u.id = ?", (resultSet, i) -> new User(resultSet), id);
    }

}
