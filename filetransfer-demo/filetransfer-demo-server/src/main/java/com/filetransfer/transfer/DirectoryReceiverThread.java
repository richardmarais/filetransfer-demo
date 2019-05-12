package com.filetransfer.transfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.filetransfer.filesystem.DirectoryWriter;
import com.filetransfer.util.Utils;
 
/**
 * This thread is responsible to handle processing of the object received by DirectoryReceiver.
 * @see DirectoryReceiver
 *
 * @author richardmarais
 */
public class DirectoryReceiverThread extends Thread {
	final static Logger logger = Logger.getLogger(DirectoryReceiverThread.class.getName());
    private Socket socket;
 
    public DirectoryReceiverThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			Map<String, List<String>> nodes = (Map<String, List<String>>) is.readObject();
			process(nodes);
            socket.close();
		} catch (ClassNotFoundException e) {
        	logger.log(Level.SEVERE, "Error reading object.", e);
        } catch (IOException ex) {
        	logger.log(Level.SEVERE, "I/O exception.", ex);
        }
    }
    
    private static void process(Map<String,List<String>> nodes) {
    	String rootDirectory = Utils.getProperty("rootDirectory");
    	logger.log(Level.INFO, "about to process files (rootDirectory: "+rootDirectory+"): " + nodes);
    	DirectoryWriter.writeNodes(rootDirectory, nodes);
    }
}
