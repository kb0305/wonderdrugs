package com.example.Wonderdrug.veevasdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.Wonderdrug.beans.Employee;
import com.example.Wonderdrug.utils.Constants;
import com.example.Wonderdrug.utils.Utility;

public class UpdateObject implements VeevaRestAPI {
	private static final Logger logger = LoggerFactory.getLogger(UpdateObject.class);
	


	@Override
	public String createUpDateObjects(List<Employee> list) {
		logger.info("Inside update object method..");
		String urlStr= Constants.VAULT_URL_CRUDE_OBJECT;
		StringBuilder res = new StringBuilder();
		StringBuilder csvData= new StringBuilder();
		
		//Convert HashMap in CSV Data
		csvData.append("employee_id__c,office__c,first_name__c,last_name__c,role__c,on_board_date__c,name__v,id");
		for(Employee emp:list) {
			csvData.append("\n");
			csvData.append(String.valueOf(emp.getEmpId())+","+emp.getOffice()+","+emp.getFirstName()+","+emp.getLastName()+","+emp.getRole()+","+emp.getOnBoardDate()
			+","+emp.getFirstName()+" "+emp.getLastName()+","+emp.getId());
		}
		System.out.println(csvData.toString());
		// Prepare request to send data 
		URL url;
		try {
			url = new URL(urlStr);
		
		HttpURLConnection connection=(HttpURLConnection) url.openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Authorization",Constants.SESSIONID); // Session ID for auth
        connection.setRequestProperty("Content-Type", "text/csv"); // Content-Type for CSV
        connection.setDoOutput(true);

        //Write the CSV data to request's body
        try {
        	OutputStream os=connection.getOutputStream();
        	byte[] byteData=csvData.toString().getBytes(StandardCharsets.UTF_8);
        	os.write(byteData);
        }catch(IOException e) {
        	e.printStackTrace();
        }
        
        //Read the response
        int response=connection.getResponseCode();
        System.out.println("Response code is : "+ response);
        
        
		BufferedReader in;
		if (response == HttpURLConnection.HTTP_OK) {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		}

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			res.append(inputLine);
		}
		in.close();
        
		} catch ( MalformedURLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res.toString();
	}

}
