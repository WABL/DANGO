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

    JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(long id) {
        return jdbcTemplate.queryForObject("select * from user u where u.id = ?", (resultSet, i) -> new User(resultSet), id);
    }

    @Override
    public User getUserByUsername(String username) {
        return jdbcTemplate.queryForObject("select * from user u where u.username = ?", (resultSet, i) -> new User(resultSet), username);
    }

    @Override
    public void saveUser(User user) {
        // TODO:
    }

    @Override
    public boolean verify(String username, String passworld) {
        return (null == jdbcTemplate.queryForObject("select * from user u where u.username = ? AND u.password = ?", (resultSet, i) -> new User(resultSet), username, passworld)) ? false : true;

    }

}
