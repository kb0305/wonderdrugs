package com.example.Wonderdrug.veevasdk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.example.Wonderdrug.beans.Employee;
import com.opencsv.CSVWriter;

public class GenerateReport implements VeevaRestAPI {

	@Override
	public String createUpDateObjects(List<Employee> list) {
		// TODO Auto-generated method stub
		String csvReport="src/main/report/CSVReport.csv";
	
		try {
		File file=new File(csvReport);
		FileWriter fileWriter= new FileWriter(file);
		
		try (BufferedWriter bufferWriter = new BufferedWriter(fileWriter)) {
			bufferWriter.write("Employee ID,Office,First Name,Last Name,Role,On Board Date,id");
			bufferWriter.newLine();
			
			for(Employee employee:list) {
				String str=employee.getEmpId()+","+employee.getOffice()+","+employee.getFirstName()+","
						+employee.getFirstName()+","+employee.getRole()+","+employee.getOnBoardDate();
				bufferWriter.write(str);
				bufferWriter.newLine();
			}
		}
		System.out.println("CSV file 'CSVReport.csv' has been generated successfully.");
        
		}catch(IOException e) {
			e.printStackTrace();
		}
	
		
		
		return null;
		
	}

}
