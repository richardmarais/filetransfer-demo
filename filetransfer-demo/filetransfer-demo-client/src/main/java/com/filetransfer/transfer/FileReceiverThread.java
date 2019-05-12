package com.filetransfer.transfer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.filetransfer.filesystem.DirectoryReader;
 
/**
 * This thread is responsible to handle processing of the object received by FileRequestReceiver.
 * @see FileRequestReceiver
 *
 * @author richardmarais
 */
public class FileReceiverThread extends Thread {
	final static Logger logger = Logger.getLogger(FileReceiverThread.class.getName());
    private Socket socket;
 
    public FileReceiverThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		DataOutputStream dos = null;
		String fileName = "";
		try {
			// receive a file name
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			fileName = reader.readLine();
			if (fileName != null && !fileName.isEmpty()) {
				// get the file from the file system
				final File file = DirectoryReader.getFile(fileName);

				// send the file
				byte[] byteArray = new byte[8192];
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				bis.read(byteArray,0,byteArray.length);
				DataInputStream dis = new DataInputStream(bis);
				os = socket.getOutputStream();
		        os.write(byteArray,0,byteArray.length);
		        os.flush();
			} else {
				fileName = "";
			}
			socket.close();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "I/O exception trying to send file "+fileName+".", ex);
		} finally {
			try {
				if (fis != null) fis.close();
				if (bis != null) bis.close();
				if (os != null) os.close();
				if (dos != null) dos.close();
				if (socket != null) socket.close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, "I/O exception trying to close stream.", e);
			}
		}
	}
}
