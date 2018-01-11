package com.demo.gps.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.demo.gps.demo.model.GPS;
import com.demo.gps.demo.model.MetaGPS;

@Component
public class GPSRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	class GPSRowMapper implements RowMapper<GPS> {
		public GPSRowMapper(Class<GPS> class1) {
		}

		@Override
		public GPS mapRow(ResultSet rs, int rowNum) throws SQLException {
			GPS gps = new GPS();
			gps.setUserName(rs.getString("userName"));
			gps.setGpxFileName(rs.getString("fileName"));
			gps.setGpsId(rs.getString("id"));
			MetaGPS meta = new MetaGPS();
			meta.setAuthor(rs.getString("author"));
			meta.setDate(rs.getTimestamp("datetime"));
			meta.setDesc(rs.getString("description"));
			meta.setName(rs.getString("name"));
			gps.setMeta(meta);
			return gps;
		}
	}

	public GPS findByUser(String userName) {
		return jdbcTemplate.queryForObject("select * from GPS where userName=?", new Object[] { userName },
				new GPSRowMapper(GPS.class));
	}
	
	public List<GPS> findListGPSByUser(String userName) {
		return jdbcTemplate.query("select * from GPS where userName=?", new Object[] { userName },
				new GPSRowMapper(GPS.class));
	}

	public int insert(GPS gps) {
		return jdbcTemplate.update(
				"insert into GPS (id,userName, fileName, name, description, author, datetime) "
						+ "values(?,  ?, ?,?,?,?,?)",
				new Object[] { gps.getGpsId(), gps.getUserName(), gps.getGpxFileName(), gps.getMeta().getName(),
						gps.getMeta().getDesc(), gps.getMeta().getAuthor(), gps.getMeta().getDate() });
	}
}
