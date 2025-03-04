/**
 * This interface is developed to support extension features in future.
 * currently only CSV and TXT files needs to parse but it can be extended for other 
 * file types also
 */
package com.example.Wonderdrug.fileReadOps;

import java.io.InputStream;

public interface FileReaderStrategy {
	
	/* Read file takes InputStream as an Input so it can accept any type of file. */
    void readFile(InputStream inputStream);
}

