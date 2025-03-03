package com.example.Wonderdrug.fileReadOps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.amazonaws.services.s3.model.S3Object;
import com.example.Wonderdrug.utils.Utility;

public class TextFileReader implements FileReaderStrategy {

	@Override
	public void readFile(InputStream input) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
			String line;
			String txtSplitBy = "\\|"; // Delimiter for TXT
			int count = 0;
			while ((line = br.readLine()) != null) { // Iterate row of TXT file
				count++;
				if (count == 1)// Skip the first line , it is header
					continue;
				String val[] = line.split(txtSplitBy);// Split all values by PIPE
				
				//refine the lines of TXT file add them in map , if employeeId is found blank then add in list
				//So later it can be write in Report
				FileDataProcessor.process(val);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
