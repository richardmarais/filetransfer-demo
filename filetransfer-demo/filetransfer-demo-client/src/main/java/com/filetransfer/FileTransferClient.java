package com.filetransfer;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.filetransfer.filesystem.DirectoryReader;
import com.filetransfer.transfer.DirectorySender;
import com.filetransfer.transfer.FileRequestReceiver;

/**
 * This can be used to access the appliction. You can call the main method with 
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
    	//new FileRequestReceiver().receiveFiles();
    	Map<String,List<String>> nodes = DirectoryReader.getNodes(args[0]);
		boolean success = DirectorySender.sendObject(nodes);
		logger.log(Level.INFO, "success: "+success);
	}

}
