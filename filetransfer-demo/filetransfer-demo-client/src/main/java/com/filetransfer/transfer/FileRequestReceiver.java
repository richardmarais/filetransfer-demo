package com.filetransfer.transfer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 * Used to create a simple TCP/IP socket in order to send an object to a server.
 * 
 * @author richardmarais
 *
 */
public class FileRequestReceiver extends FileTransfer {
 
	final static Logger logger = Logger.getLogger(FileRequestReceiver.class.getName());
    
	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(file_port)) {
    		logger.log(Level.INFO, "Server is listening on port " + file_port); 
            while (true) {
                Socket socket = serverSocket.accept();
                logger.log(Level.INFO, "New client connected ("+hostname+":"+file_port+")."); 
                new FileReceiverThread(socket).start();
            } 
        } catch (IOException ex) {
        	logger.log(Level.SEVERE, "Server exception ("+hostname+":"+file_port+").", ex);
        }
    }
}