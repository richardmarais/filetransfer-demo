package com.filetransfer.filesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.filetransfer.transfer.DirectorySender;
import com.filetransfer.transfer.FileRequestReceiver;

/**
 * Used to test the methods on DirectoryReader and processing of the files.
 * 
 * @see com.filetransfer.filesystem.DirectoryReader
 * @author richardmarais
 */
public class DirectoryReaderTest {
	
	private static final String ROOT_DIRECTORY = "src/test/resources/testdirectory";
	
	/**
	 * used to test the directory reader
	 */
	@Test
	public void testGetNodes() {
		Map<String,List<String>> nodes = DirectoryReader.getNodes(ROOT_DIRECTORY);
		assertNotNull(nodes);
		assertEquals(5, nodes.size());
		assertEquals(3, nodes.get("/directory1").size());
		assertEquals(1, nodes.get("/directory2").size());
		assertEquals(0, nodes.get("/directory2/directory3").size());
		assertEquals(1, nodes.get("/directory2/directory3/directory4").size());
		for (Map.Entry<String, List<String>> entry : nodes.entrySet()) {
		    System.out.println(entry.getKey());
		    for (String data : entry.getValue()) {
				System.out.println("     "+data);
		    }
		}
	}

	/**
	 * used to test the processing of the files set in the ROOT_DIRECTORY.
	 * This needs to have DirectoryReceiver (filetransfer-demo-server module) & FileRequestReceiver running.
	 * @see DirectoryReceiver
	 * @see FileRequestReceiver
	 */
	@Test
	public void testProcessNodes() {
		Map<String,List<String>> nodes = DirectoryReader.getNodes(ROOT_DIRECTORY);
		assertNotNull(nodes);
		boolean success = DirectorySender.sendObject(nodes);
		assertTrue(success);
	}
}