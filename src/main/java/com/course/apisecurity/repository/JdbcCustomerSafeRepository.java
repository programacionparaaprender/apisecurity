package com.course.apisecurity.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.course.apisecurity.entity.JdbcCustomer;

@Repository
public class JdbcCustomerSafeRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcCustomer findCustomerByEmail(String email) {
		var sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer "
				+ "WHERE email = ? AND email is not null";

		return jdbcTemplate.queryForObject(sql, this::mapToCustomer, email);
	}

	public List<JdbcCustomer> findCustomersByGender(String genderCode) {
		var sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer WHERE gender = ?";

		return jdbcTemplate.query(sql, this::mapToCustomer, genderCode);
	}

	private JdbcCustomer mapToCustomer(ResultSet rs, long rowNum) throws SQLException {
		var customer = new JdbcCustomer();

		Optional.ofNullable(rs.getDate("birth_date")).ifPresent(b -> customer.setBirthDate(b.toLocalDate()));
		customer.setCustomerId(rs.getInt("customer_id"));
		customer.setEmail(rs.getString("email"));
		customer.setFullName(rs.getString("full_name"));
		customer.setGender(rs.getString("gender"));

		return customer;
	}

	public void createCustomer(JdbcCustomer newCustomer) {
		var sql = "INSERT INTO jdbc_customer(full_name, email, birth_date, gender) "
				+ "VALUES (:fullName, :email, :birthDate, :gender)";
		var sqlParameters = new MapSqlParameterSource().addValue("fullName", newCustomer.getFullName())
				.addValue("email", newCustomer.getEmail()).addValue("birthDate", newCustomer.getBirthDate())
				.addValue("gender", newCustomer.getGender());

		namedParameterJdbcTemplate.update(sql, sqlParameters);
	}

	public void updateCustomerFullName(int customerId, String newFullName) {
		var sql = "CALL update_jdbc_customer(:customerId, :newFullName)";
		var sqlParameters = new MapSqlParameterSource().addValue("customerId", customerId).addValue("newFullName",
				newFullName);

		namedParameterJdbcTemplate.update(sql, sqlParameters);
	}

}

