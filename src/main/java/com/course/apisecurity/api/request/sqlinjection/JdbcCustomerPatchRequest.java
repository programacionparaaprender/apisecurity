package com.course.apisecurity.api.request.sqlinjection;


public class JdbcCustomerPatchRequest {

	private String newFullName;

	public String getNewFullName() {
		return newFullName;
	}

	public void setNewFullName(String newFullName) {
		this.newFullName = newFullName;
	}

}

