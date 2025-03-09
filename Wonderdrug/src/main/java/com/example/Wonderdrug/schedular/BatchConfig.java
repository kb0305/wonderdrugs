package com.example.Wonderdrug.schedular;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.Wonderdrug.aws.FileReaderService;
import com.example.Wonderdrug.utils.Constants;
import com.example.Wonderdrug.veevasdk.VeevaService;

@Component
public class BatchConfig {

//	 private String bucketName;
	private final FileReaderService fileReaderService;
	private final VeevaService veevaService;

	public BatchConfig(FileReaderService fileReaderService, VeevaService veevaService) {
		this.fileReaderService = fileReaderService;
		this.veevaService = veevaService;

	}

	@Scheduled(cron = "${job.scheduler}")
	public void runTask() {
		System.out.println("Running scheduled task at ");
		// Call the method to connect AWS S3 bucket from this line
		fileReaderService.readS3Bucket();

		// if S3Bucket having the file with data then proceed with next API operations
		if (Constants.fileMap.size() != 0) {
			veevaService.processVeevaRequest();
		}

	}

}