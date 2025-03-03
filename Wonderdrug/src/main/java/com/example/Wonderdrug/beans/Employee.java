package com.example.Wonderdrug.beans;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
	

	private long empId;
	private String office;
	private String firstName;
	private String lastName;
	private String role;
	private String onBoardDate;
	private String id;
	
	public Employee(long empId, String office, String firstName, String lastName, String role, String onBoardDate,String id) {
		super();
		this.empId = empId;
		this.office = office;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.onBoardDate = onBoardDate;
		this.id = id;
	}
	
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOnBoardDate() {
		return onBoardDate;
	}
	public void setOnBoardDate(String onBoardDate) {
		this.onBoardDate = onBoardDate;
	}
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", office=" + office + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", role=" + role + ", onBoardDate=" + onBoardDate + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
