package com.example.Wonderdrug.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.Wonderdrug.aws.S3FileReader;
import com.example.Wonderdrug.beans.Employee;
import com.example.Wonderdrug.beans.EndConnection;
import com.example.Wonderdrug.beans.ErrorEndSession;
import com.example.Wonderdrug.beans.GetConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class will have all the utility methods
 * 
 */
public class Utility {
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);
	// Get the session.

	public static Map<String, String> getSession() {

		try {
			// URL to which we are making the POST request
			@SuppressWarnings("deprecation")
			URL url = new URL(Constants.VAULT_URL_POST_SESSION);

			// Open a connection to the server
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set request method to POST
			connection.setRequestMethod("POST");

			// Enable input and output streams
			connection.setDoOutput(true);

			// Set content type as JSON (or whatever your API expects)
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// Create the body content as a string (e.g., JSON)
			String urlEncodedBody = Constants.CREDENTIALS;

			// Write the body content to the output stream
			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = urlEncodedBody.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			// Send the request and get the response code
			int responseCode = connection.getResponseCode();

			// Read the response body (if response code is 200, OK)
			BufferedReader in;
			if (responseCode == HttpURLConnection.HTTP_OK) {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}

			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// Print the response body
			//System.out.println("Response Body: " + response.toString());

			// read Json message here.
			Map<String, String> resoponse = readSessionIdJson(response.toString());

			return resoponse;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Map<String, String> readSessionIdJson(String jsonResponse) {
		Map<String, String> responseMap = new HashMap<String, String>();

		// Parse JSON into ApiResponse class
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			GetConnection response = objectMapper.readValue(jsonResponse, GetConnection.class);

			responseMap.put("status", response.responseStatus);
			responseMap.put("SessionID", response.sessionId);
			
			Constants.SESSIONID=response.sessionId;

			
			System.out.println("**** Session Aquiring Status **** :: "+responseMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return responseMap;
	}

	// End the session.
	@SuppressWarnings("deprecation")
	public static String endSession(String sessionId) {
		
		Map<String,String> map= new HashMap<String, String>();
		try {
			// URL to which we are making the POST request
			URL url = new URL(Constants.VAULT_URL_DELETE_SESSION);

			// Open a connection to the server
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set request method to POST
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Authorization", sessionId);
			connection.setRequestMethod("DELETE");

			// Get the HTTP response code
			int responseCode = connection.getResponseCode();

			BufferedReader in;

			if (responseCode == HttpURLConnection.HTTP_OK) {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}

			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
			
			map=readEndSessionJson(response.toString());
			
			System.out.println("**** Ending session Status **** :: "+map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Map<String,String> readEndSessionJson(String string) {
		Map<String, String> responseMap = new HashMap<String, String>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			EndConnection response = objectMapper.readValue(string, EndConnection.class);

			//System.out.println("Response Status: " + response.responseStatus);
			responseMap.put("Response status", response.responseStatus);


			// Accessing the list of vaultIds
			if(response.errors!=null) {
				for (ErrorEndSession error : response.errors) {
					responseMap.put("errorType", error.type);
					responseMap.put("message", error.message);
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return responseMap;
	}

	public static void refineFileData(String[] val) {
		Employee employee;
			Map<String,String> roleMap=Utility.readCSV("role_picklist.csv");
			Map<String,String> officeMap=Utility.readCSV("office_object.csv");

		// Employee ID|Office|First Name|Last Name|Role|On Board Date|id
		if (val.length != 0) {
			//1-Office
			String office=officeMap.get(val[1].trim());
			//4-role
			String role=roleMap.get(val[4].trim());
			employee = new Employee((val[0].isBlank() ? 0 : Long.parseLong(val[0])), val[1], val[2], val[3], val[4],
					ConvertDateFormat(val[5]),"");
			if (val[0].isBlank()) {
				// Add in blank Array
				Constants.blankRow.add(employee);
				//logger.info("Total rows whith out Employee ID :: "+Constants.blankRow.size());
			} else {
				// add in map
				employee.setOffice(office);
				employee.setRole(role);
				Constants.fileMap.put(Long.parseLong(val[0]), employee);
				//logger.info("Total rows in file  :: "+Constants.fileMap.size());
			}
		}
	}

	private static String ConvertDateFormat(String string) {
		String newDateFormat=null;
		try {
			//Convert String
			
		    DateTimeFormatter currentFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		    DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    newDateFormat=LocalDate.parse(string, currentFormat).format(newFormat);
		}catch(DateTimeParseException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return newDateFormat;
	}

	public static Map<String,String> readCSV(String filename){
		Map<String,String> role_Office=new HashMap<String,String>();

	        try (InputStream inputStream = Utility.class.getClassLoader().getResourceAsStream(filename);
	                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

	               // Ensure the file exists
	               if (inputStream == null) {
	                   System.out.println("File not found in resources folder.");
	                   return null;
	               }

	               String line;
	               // Read the file line by line
	               while ((line = br.readLine()) != null) {
	                   // Split the line by colon (":") delimiter
	                   String[] values = line.split(":");
	                   role_Office.put(values[0], values[1]);
	                   // You can process the values further here, depending on your needs
	               }
	               
	           } catch (IOException e) {
	               e.printStackTrace();
	           }
            return role_Office;

	}
	
	   public static String decode(String encodedKey) {
	        return new String(Base64.getDecoder().decode(encodedKey));
	    }
	
}
