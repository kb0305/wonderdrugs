package com.example.Wonderdrug.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class EndConnection {

	@JsonProperty("responseStatus")
	public String responseStatus;

	@JsonProperty("errors")
	public List<ErrorEndSession> errors;

}
