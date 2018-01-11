package com.demo.gps.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.demo.gps.demo.model.User;

@Component
public class UserRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public User findByUser(String userName) {
		String sql = "select * from Users where userName=?";
		List<User> users = jdbcTemplate.query(sql, new Object[] { userName }, new BeanPropertyRowMapper<User>(User.class));
		return users != null & !users.isEmpty() ? users.get(0) : null;
	}

	public int insert(User user) {
		return jdbcTemplate.update(
				"insert into Users (userName, latestLatitude, latestLongitude, latestUpdated) " + "values(?,  ?, ?, ?)",
				new Object[] { user.getUserName(), user.getLatestLatitude(), user.getLatestLongitude(), user.getLatestUpdated() });
	}

	public int update(User user) {
		return jdbcTemplate.update("update Users set latestLatitude = ?, latestLongitude = ?, latestUpdated=?  where userName = ?",
				new Object[] { user.getLatestLatitude(), user.getLatestLongitude(), user.getLatestUpdated(), user.getUserName()});
	}
}
