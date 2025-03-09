package com.example.Wonderdrug.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/*
This calls is design to perform below tasks.
1. Decrypt the AWS Keys and Read files in CSV and TXT format
2. Write file data in Map.
3. This class is design to support reading other file types as well.
4. 
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
import com.example.Wonderdrug.fileReadOps.CSVFileReader;
import com.example.Wonderdrug.fileReadOps.FileReaderContext;
import com.example.Wonderdrug.fileReadOps.FileTransfer;
import com.example.Wonderdrug.fileReadOps.TextFileReader;
import com.example.Wonderdrug.utils.Constants;

@Component
public class S3FileReader implements FileReaderService {
	private static final Logger logger = LoggerFactory.getLogger(S3FileReader.class);

	@Value("${s3bucket.name}")
	private String bucketName;
	private String accessKey =DecoderClass.decode(Constants.ACCESS_KEY);
	private String securityKey=DecoderClass.decode(Constants.SECURITY_KEY);
	
	@Override
	public void readS3Bucket()  {
		
		String complete=Constants.COMPLETE;
		String file;

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey,
				securityKey);

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1) // Change to your region
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

		ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request().withBucketName(bucketName);
		ListObjectsV2Result result;

		do {
			result = s3Client.listObjectsV2(listObjectsV2Request);// you connected to S3 bucket folder now locate
																	// subfolders

			for (S3ObjectSummary os : result.getObjectSummaries()) {
				String key = os.getKey();
				
				//Skip WAR file and completed folder for file processing
				if(key.contains("WARFiles") || key.contains("Completed")) {
					System.out.println(key);
					continue;
				}
				if (key.endsWith(".csv") ) {
					try {
						// Get the exact file name from provided bucketName and Key
						S3Object s3object = s3Client.getObject(bucketName, key);
						
						// s3Object retrun subfolder/filename so let's take file only.
						String arr[]=key.split("/").clone();
						file=arr[1];
						
						//Apply abstract factory design pattern rule here
						FileReaderContext fileReaderContext = new FileReaderContext(new CSVFileReader());
						fileReaderContext.readFile(s3object.getObjectContent());
						//Transfer the file to completed folder
			            FileTransfer.FileMove(complete, file, s3Client, key,bucketName);

			           
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if (key.endsWith(".txt")) {
					try {
						// Get the exact file name from provided bucketName and Key
						S3Object s3object = s3Client.getObject(bucketName, key);
						// s3Object retrun subfolder/filename so let's take file only.
						String arr[]=key.split("/").clone();
						file=arr[1];

						//Apply abstract factory design pattern rule here
						FileReaderContext fileReaderContext = new FileReaderContext(new TextFileReader());
						fileReaderContext.readFile(s3object.getObjectContent());
						
						//Transfer the file to completed folder
						FileTransfer.FileMove(complete, file, s3Client, key,bucketName);
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
