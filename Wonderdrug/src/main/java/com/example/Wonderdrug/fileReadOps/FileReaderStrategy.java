/**
 * 
 */
package com.example.Wonderdrug.fileReadOps;

import java.io.InputStream;

import com.amazonaws.services.s3.model.S3Object;

/**
 * This interface is developed to support extension features in future.
 * currently only CSV and TXT files needs to parse but it can be extended for other 
 * file types also
 */
public interface FileReaderStrategy {
    void readFile(InputStream inputStream);
}

