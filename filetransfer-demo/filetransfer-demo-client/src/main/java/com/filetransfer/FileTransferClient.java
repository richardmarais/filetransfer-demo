package com.filetransfer;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.filetransfer.filesystem.DirectoryReader;
import com.filetransfer.transfer.DirectorySender;

/**
 * This can be used to access the application. You can call the main method with 
 * a parameter indicating the root of the directory you would like to process.
 * 
 * This will then copy the directory and files to the corresponding root directory 
 * defined in module filetransfer-demo-server src/main/resources/application.properties.
 * 
 * @author richardmarais
 *
 */
public class FileTransferClient {
	final static Logger logger = Logger.getLogger(DirectoryReader.class.getName());

	public static void main(String[] args) {
		if (args.length != 1) {
			logger.log(Level.SEVERE, "FileTransferClient needs to be called with one argument contaiing the root directory.");
			return;
		}
		String rootDirectory = args[0];
		logger.log(Level.INFO, "Progessing files from root directory: "+rootDirectory);
    	Map<String,List<String>> nodes = DirectoryReader.getNodes(rootDirectory);
		boolean success = DirectorySender.sendObject(nodes);
		logger.log(Level.INFO, "Success: "+success);
	}

}
