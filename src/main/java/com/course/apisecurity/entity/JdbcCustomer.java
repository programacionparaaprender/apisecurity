package com.course.apisecurity.entity;

import java.time.LocalDate;

//import javax.validation.constraints.Email;
//import javax.validation.constraints.Pattern;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class JdbcCustomer {

	@Id
	private int customerId;

	private String fullName;

	@Email
	private String email;

	private LocalDate birthDate;

	@Pattern(regexp = "^[MF]$", message = "Invalid gender")
	private String gender;

}

