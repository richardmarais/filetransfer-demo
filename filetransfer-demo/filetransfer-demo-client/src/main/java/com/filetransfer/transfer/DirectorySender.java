package com.filetransfer.transfer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 * Used to create a simple TCP/IP socket in order to send an object to a server.
 * 
 * @author richardmarais
 *
 */
public class DirectorySender extends FileTransfer {
 
	final static Logger logger = Logger.getLogger(DirectorySender.class.getName());
    
    public static boolean sendObject(Map<String,List<String>> nodes) {
        try (Socket socket = new Socket(hostname, dir_port)) {
        	ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        	os.writeObject(nodes);            
           	socket.close();
           	return true;
        } catch (UnknownHostException ex) {
        	logger.log(Level.SEVERE, "Server not found ("+hostname+":"+dir_port+").", ex);
        	return false;
        } catch (IOException ex) {
        	logger.log(Level.SEVERE, "I/O exeption ("+hostname+":"+dir_port+").", ex);
        	return false;
        }
    }
}