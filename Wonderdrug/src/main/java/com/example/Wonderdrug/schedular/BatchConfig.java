package com.example.Wonderdrug.schedular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.Wonderdrug.aws.S3FileReader;
import com.example.Wonderdrug.utils.Constants;
import com.example.Wonderdrug.veevasdk.VeevaSdks;

@Component
public class BatchConfig {

//	 private String bucketName;
	@Autowired
	S3FileReader reader;
	
	@Autowired
	VeevaSdks VeevaSdks;

	@Scheduled(cron = "${job.scheduler}")
	public void runTask() {
		System.out.println("Running scheduled task at ");
		// Call the method to connect AWS S3 bucket from this line
		reader.readS3Bucket();

		// if S3Bucket having the file with data then proceed with next API operations
		if (Constants.fileMap.size() != 0) {
			VeevaSdks.processVeevaRequest();
		}

	}

}