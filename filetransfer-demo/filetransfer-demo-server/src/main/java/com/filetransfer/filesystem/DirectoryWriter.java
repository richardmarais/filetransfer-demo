package com.filetransfer.filesystem;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.filetransfer.transfer.FileRequester;

/**
 * Used to write the directories defined in the object to the file system.
 * 
 * @author richardmarais
 *
 */
public class DirectoryWriter {

	final static Logger logger = Logger.getLogger(DirectoryWriter.class.getName());

	/**
	 * Write the nodes to the file system.
	 * 
	 * @param rootDirectory
	 * @param nodes
	 */
	public static void writeNodes(String rootDirectory, Map<String, List<String>> nodes) {
		for (Map.Entry<String, List<String>> entry : nodes.entrySet()) {
			boolean createdOrExists = makeDir(rootDirectory+entry.getKey());
			if (createdOrExists) {
				List<String> data = entry.getValue();
				for (String fileName : data) {
					getAndCreateFile(fileName);
				}
			} else {
				logger.log(Level.WARNING, "Directory not created: "+rootDirectory+entry.getKey());
			}
		}
	}

	/**
	 * Create a directory on the file system.
	 * 
	 * @param path
	 * @return
	 */
	private static boolean makeDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return true;
	}
	
	/**
	 * Retrieves a file and writes it to the file system.
	 * 
	 * @param fileName
	 */
	private static void getAndCreateFile(String fileName) {
		new FileRequester().getFile(fileName);
	}

}
