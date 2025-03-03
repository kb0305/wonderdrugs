package com.example.Wonderdrug.aws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/*
S3FileReader object is design to connect S3 bucket and locate CSV and TXT file stored in
Sub folders of S3 bucket
*/

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.Wonderdrug.beans.Employee;
import com.example.Wonderdrug.fileReadOps.CSVFileReader;
import com.example.Wonderdrug.fileReadOps.FileReaderContext;
import com.example.Wonderdrug.fileReadOps.FileReaderStrategy;
import com.example.Wonderdrug.fileReadOps.TextFileReader;
import com.example.Wonderdrug.utils.Constants;
import com.example.Wonderdrug.utils.Utility;

@Component
public class S3FileReader {
	private static final Logger logger = LoggerFactory.getLogger(S3FileReader.class);

	@Value("${s3bucket.name}")
	private String bucketName ;

	public void readS3Bucket() {

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(Constants.ACCESS_KEY,
				Constants.SECURITY_KEY);

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1) // Change to your region
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

		ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request().withBucketName(bucketName);
		ListObjectsV2Result result;

		do {
			result = s3Client.listObjectsV2(listObjectsV2Request);// you connected to S3 bucket folder now locate
																	// subfolders

			for (S3ObjectSummary os : result.getObjectSummaries()) {
				String key = os.getKey();
				if (key.endsWith(".csv") ) {
					try {
						// Get the exact file name from provided bucketName and Key
						S3Object s3object = s3Client.getObject(bucketName, key);
						// s3Object retrun subfolder/filename so let's take file only.
						
						//Apply abstract factory design pattern rule here
						FileReaderContext fileReaderContext = new FileReaderContext(new CSVFileReader());
						fileReaderContext.readFile(s3object.getObjectContent());
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if (key.endsWith(".txt")) {
					try {
						// Get the exact file name from provided bucketName and Key
						S3Object s3object = s3Client.getObject(bucketName, key);
						// s3Object retrun subfolder/filename so let's take file only.
						
						//Apply abstract factory design pattern rule here
						FileReaderContext fileReaderContext = new FileReaderContext(new TextFileReader());
						fileReaderContext.readFile(s3object.getObjectContent());
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("Unknown format  -- "+key);
				}
			}
			logger.info("Blank row list : "+Constants.blankRow.size());
			logger.info("Gile  row map : "+Constants.fileMap.size());
			
			listObjectsV2Request.setContinuationToken(result.getNextContinuationToken());
		} while (result.isTruncated());

	}


}
