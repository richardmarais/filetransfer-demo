package com.filetransfer.transfer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.filetransfer.util.Utils;

/**
 * Used to create a simple TCP/IP socket in order to send an object to a server
 * and receive a response.
 * 
 * @author richardmarais
 *
 */
public class FileRequester extends FileTransfer {

	private final static Logger logger = Logger.getLogger(FileRequester.class.getName());

	/**
	 * Used to make a request for a file.
	 * 
	 * @param fileName
	 */
	public static void getFile(String fileName) {
		try (Socket socket = new Socket(hostname, file_port)) {
			processFile(socket, fileName);
			socket.close();
		} catch (UnknownHostException ex) {
			logger.log(Level.SEVERE, "Server not found (" + hostname + ":" + file_port + ").", ex);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "I/O exeption (" + hostname + ":" + file_port + ").", ex);
		}
	}
	
	/**
	 * Used to make a request for a file.
	 * 
	 * @param fileName
	 */
	public static void processFile(Socket socket, String fileName) {
		OutputStream os = null;
		InputStream is = null;
		OutputStream fos = null;
		BufferedOutputStream bos = null;
	    int bytesRead;
	    int current = 0;
		try {
			// request the file
			os = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(os, true);
			writer.println(fileName);

			// receive the file
			try {
				int bufferSize = socket.getReceiveBufferSize();
				is = socket.getInputStream();				
				byte [] mybytearray  = new byte [bufferSize];
				fos = new FileOutputStream(Utils.getProperty("rootDirectory") + fileName);
				bos = new BufferedOutputStream(fos);
				bytesRead = is.read(mybytearray,0,mybytearray.length);
				current = bytesRead;				
				do {
					bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
					if(bytesRead >= 0) current += bytesRead;
				} while(bytesRead > -1);				
				bos.write(mybytearray, 0 , current);
				bos.flush();
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error receiveing file " + fileName + ".", e);
			}
		} catch (UnknownHostException ex) {
			logger.log(Level.SEVERE, "Server not found (" + hostname + ":" + file_port + ").", ex);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "I/O exeption (" + hostname + ":" + file_port + ").", ex);
		} finally {
			try {
				if (is != null) is.close();
				if (os != null)	os.close();
				if (fos != null) fos.close();
			} catch (IOException e) {
	        	logger.log(Level.SEVERE, "I/O exeption closing stream ("+hostname+":"+file_port+").", e);
			}
		}
	}
}