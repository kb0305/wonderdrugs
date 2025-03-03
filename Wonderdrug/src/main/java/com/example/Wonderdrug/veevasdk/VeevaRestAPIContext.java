package com.example.Wonderdrug.veevasdk;

import java.util.List;

import com.example.Wonderdrug.beans.Employee;

public class VeevaRestAPIContext {

	private VeevaRestAPI object;

	public VeevaRestAPIContext(VeevaRestAPI object) {

		this.object = object;
	}

	public String createUpDateObjects(List<Employee> list) {

		return object.createUpDateObjects(list);

	}

}
