package com.course.apisecurity.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.course.apisecurity.entity.JdbcCustomer;

@Repository
public class JdbcCustomerDangerRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcCustomer findCustomerByEmail(String email) {
		var sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer WHERE email = '" + email
				+ "' AND email is not null";

		return jdbcTemplate.queryForObject(sql, this::mapToCustomer);
	}

	public List<JdbcCustomer> findCustomersByGender(String genderCode) {
		var sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer WHERE gender = '"
				+ genderCode + "'";

		return jdbcTemplate.query(sql, this::mapToCustomer);
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
		var sql = "INSERT INTO jdbc_customer(full_name, email, gender, birth_date) " + "VALUES ('"
				+ newCustomer.getFullName() + "', '" + newCustomer.getEmail() + "', '" + newCustomer.getGender()
				+ "', '" + newCustomer.getBirthDate() + "');";

		jdbcTemplate.execute(sql);
	}

	public void updateCustomerFullName(int customerId, String newFullName) {
		var sql = "CALL update_jdbc_customer(" + customerId + ", '" + newFullName + "')";

		jdbcTemplate.execute(sql);
	}

}
