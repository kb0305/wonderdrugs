package com.example.Wonderdrug.veevasdk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.Wonderdrug.beans.ApiResponse;
import com.example.Wonderdrug.beans.Employee;
import com.example.Wonderdrug.utils.Constants;
import com.example.Wonderdrug.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class VeevaSdksService implements VeevaService {
	// Get session
	@SuppressWarnings("deprecation")
	@Override
	public void processVeevaRequest() {
		Employee emp = null;
		try {

			// URL to which we are making the POST request
			URL url = new URL(Constants.VAULT_URL_GET_OBJECT);

			Utility.getSession();
			// Open a connection to the server
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set request method to POST
			connection.setRequestProperty("Accept", Constants.REQUEST_FORMAT_JSON);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", Constants.SESSIONID);

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

			System.out.println("Response from API :: " + response);
			in.close();

			// Read JSON Object here
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			ApiResponse empRes = objectMapper.readValue(response.toString(), ApiResponse.class);

			// Got the Veeva Objects in ApiResponse object , save it in map and then proceed
			// with comparison.
			for (ApiResponse.Employee employee : empRes.getData()) {
				emp = new Employee(Long.parseLong(employee.getEmployeeId()), "", employee.getFirstName(),
						employee.getLastName(), employee.getRole()[0], employee.getOnBoardDate(), employee.getId());
				Constants.veevaObjMap.put(Long.parseLong(employee.getEmployeeId()), emp);
			}

			// Veeva and file data are received
			// if veeva data exists then proceed with data comparison
			if (Constants.fileMap.size() != 0)
				CompareObjects(Constants.veevaObjMap, Constants.fileMap);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// read Json message here.

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (Constants.SESSIONID != null) {
				Utility.endSession(Constants.SESSIONID);
			}
		}

	}

	private static void CompareObjects(Map<Long, Employee> veevaObjMap, Map<Long, Employee> fileMap) {
		List<Employee> createList = new ArrayList<Employee>();
		List<Employee> updateList = new ArrayList<Employee>();

		try {

			// Compare file object with veeva object.
			// If employeeId matched go for update
			System.out.println("fileMap size :: " + fileMap.size());
			System.out.println("VeevaMap size :: " + veevaObjMap.size());
			fileMap.forEach((k, v) -> {
				// K ->key
				if (veevaObjMap.containsKey(k)) {
					v.setId(veevaObjMap.get(k).getId());// set Id explicitly from VeevaFiled map to FileMap since file
														// is not
														// Providing Id in TXT or CSV
					updateList.add(v);
				} else {
					createList.add(v);
				}
			});
			// if employeeID did not matched got for Create
			System.out.println("List for create size :: " + createList.size());
			System.out.println("List for update size :: " + updateList.size());
			// call Create method
			if (createList.size() > 0) {
				VeevaRestAPIContext context = new VeevaRestAPIContext(new CreateObject());
				context.createUpDateObjects(createList);

			}
			// Call and update method
			if (updateList.size() > 0) {
				VeevaRestAPIContext context = new VeevaRestAPIContext(new UpdateObject());
				context.createUpDateObjects(updateList);

			}

			// Generate CSV file if EmployeeID is blank
			if (Constants.blankRow.size() > 0) {
				VeevaRestAPIContext context = new VeevaRestAPIContext(new GenerateReport());
				context.createUpDateObjects(Constants.blankRow);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
