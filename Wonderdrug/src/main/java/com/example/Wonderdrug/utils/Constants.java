package com.example.Wonderdrug.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Wonderdrug.beans.Employee;

public class Constants {

	public static List<Employee> blankRow = new ArrayList<Employee>();
	public static Map<Long, Employee> fileMap = new HashMap<Long, Employee>();
	public static Map<Long, Employee> veevaObjMap = new HashMap<Long, Employee>();
	public static String SESSIONID = null;


	public static final String ACCESS_KEY = "QUtJQTRNSTJKWFI1NlRER1NMWDI=";
	public static final String SECURITY_KEY = "UjVSZDhPVFBYb2VNQURPMmM1Ukh2UDdvOURUZXkyUXI1UDhINlBQVg==";
	
	public static final String CONTENT_TYPE="Content-Type";
	
	public static final String REQUEST_FORMAT_URL_ENCODED="application/x-www-form-urlencoded";
	public static final String REQUEST_FORMAT_JSON="application/json";
	
	
	public static final String CREDENTIALS = "username=karan-bhatt@mssandbox.com&password=Veeva1234";
	
	public static final String VAULT_URL_DELETE_SESSION = "https://mssandbox-clinical.veevavault.com/api/v24.3/session";
	public static final String VAULT_URL_POST_SESSION = "https://mssandbox-clinical.veevavault.com/api/v24.3/auth";
	public static final String VAULT_URL_GET_OBJECT = "https://mssandbox-clinical.veevavault.com/api/v24.3/vobjects/employee__c?"
														+ "fields=id,first_name__c,last_name__c,"
														+ "employee_id__c,on_board_date__c,name__v,role__c";
	public static final String VAULT_URL_CRUDE_OBJECT = "https://mssandbox-clinical.veevavault.com/api/v24.3/vobjects/employee__c";
														 

}
