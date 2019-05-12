package com.filetransfer.transfer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 * A simple TCP/IP socket server used to receive objects.
 * 
 * This needs to be run in order to listen for incoming objects.
 * 
 * Once received, responsibility to process the object will be delegated to ReceiverThread.
 * @see DirectoryReceiverThread
 * 
 * @author richardmarais
 *
 */
public class DirectoryReceiver extends FileTransfer {
	 
	final static Logger logger = Logger.getLogger(DirectoryReceiver.class.getName());

	public static void main(String[] args) {
    	try (ServerSocket serverSocket = new ServerSocket(dir_port)) {
    		logger.log(Level.INFO, "Server is listening on port " + dir_port); 
            while (true) {
                Socket socket = serverSocket.accept();
                logger.log(Level.INFO, "New client connected ("+hostname+":"+dir_port+")."); 
                new DirectoryReceiverThread(socket).start();
            } 
        } catch (IOException ex) {
        	logger.log(Level.SEVERE, "Server exception ("+hostname+":"+dir_port+").", ex);
        }
    }
}
