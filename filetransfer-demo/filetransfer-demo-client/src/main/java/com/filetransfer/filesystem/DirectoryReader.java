package com.filetransfer.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.filetransfer.util.Utils;

/**
 * This class is used to read files from a directory and construct an object representing the structure.
 * 
 * @author richardmarais
 *
 */
public class DirectoryReader {

	final static Logger logger = Logger.getLogger(DirectoryReader.class.getName());
	private static TreeMap<String,List<String>> nodes = new TreeMap<>();

	/**
	 * Recursively walk through the directory structure and return an object representing the structure.
	 * 
	 * The returned object is just a map containing String representing the bare minimum in order to
	 * keep the network payload as small as possible. It just contains the directory path as the key
	 * and a list of files for each directory.
	 * 
	 * @param rootDirectory
	 * @return Map<String,List<String>> - key: dirPath ,value: list of files
	 */
	public static Map<String,List<String>> getNodes(String rootDirectory) {
		Utils.setProperty("rootDirectory", rootDirectory);
		Path path = Paths.get(rootDirectory);		
		try	{
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes bfas) throws IOException {
					if (bfas.isDirectory()) {
						nodes.put(file.toString().replace(rootDirectory, ""), new ArrayList<String>());
					}
					return FileVisitResult.CONTINUE;
				}				
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes bfas) throws IOException {
					if (!bfas.isDirectory()) {
						List<String> data = nodes.get(file.getParent().toString().replace(rootDirectory, ""));
						data.add(file.toString().replace(rootDirectory, ""));
					}
					return FileVisitResult.CONTINUE;
				}
			});
		}
		catch(IOException e) {
			logger.log(Level.SEVERE, "Error trying to explore directory.", e);
		}
		return nodes;
	}
	
	/**
	 * Get the file from the file system.
	 * 
	 * @param fileName
	 * @return
	 */
	public static File getFile(String fileName) {
		String rootDirectory = Utils.getProperty("rootDirectory");
		File file = new File(rootDirectory+fileName);		
		return file;
	}

}
