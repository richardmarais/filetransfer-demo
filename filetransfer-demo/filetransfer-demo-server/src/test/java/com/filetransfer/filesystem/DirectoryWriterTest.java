package com.filetransfer.filesystem;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class DirectoryWriterTest {

	private static final String ROOT_DIRECTORY = "src/test/resources/testdirectory";

	@Test
	public void testWriteNodes() {
		// write to the directory
		Map<String,List<String>> nodes = new TreeMap<>();
		List<String> data1 = new ArrayList<>();
		data1.add(ROOT_DIRECTORY+"/directory1/file1.txt");
		data1.add(ROOT_DIRECTORY+"/directory1/file2.txt");
		nodes.put(ROOT_DIRECTORY+"/directory1", data1);
		List<String> data2 = new ArrayList<>();
		data2.add(ROOT_DIRECTORY+"/directory2/file3.txt");
		data2.add(ROOT_DIRECTORY+"/directory2/file4.txt");
		nodes.put(ROOT_DIRECTORY+"/directory2", data2);
		DirectoryWriter.writeNodes(ROOT_DIRECTORY, nodes);
		
		// read from the directory
		fail("Not yet implemented");
	}

}
