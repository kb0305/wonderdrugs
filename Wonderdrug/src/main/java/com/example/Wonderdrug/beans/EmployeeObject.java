package com.example.Wonderdrug.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class EmployeeObject {
	
	@JsonProperty("responseStatus")
	public String responseStatus;
	
	@JsonProperty("data")
	public List<EmpData> data;
}
