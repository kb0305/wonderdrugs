package com.example.Wonderdrug.fileReadOps;

import java.io.InputStream;

import com.amazonaws.services.s3.model.S3Object;

public class FileReaderContext {

    private FileReaderStrategy fileReaderStrategy;
    
    public FileReaderContext(FileReaderStrategy fileReaderStrategy) {
    	this.fileReaderStrategy=fileReaderStrategy;
    }


    public void readFile(InputStream input) {
    	fileReaderStrategy.readFile(input);
    }
}

