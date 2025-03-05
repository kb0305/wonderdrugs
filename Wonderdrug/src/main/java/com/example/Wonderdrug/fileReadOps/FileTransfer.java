package com.example.Wonderdrug.fileReadOps;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

public class FileTransfer {
	
	public static void FileMove(String complete, String file, AmazonS3 s3Client, String key,String bucketName) {
		CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, key, bucketName, complete+file);
		// Perform Copy Operation
		s3Client.copyObject(copyObjectRequest);
		// Step 2: Delete the original file
		s3Client.deleteObject(new DeleteObjectRequest(bucketName, key));
		System.out.println("File moved to Completed folder successfully");
	}

}
