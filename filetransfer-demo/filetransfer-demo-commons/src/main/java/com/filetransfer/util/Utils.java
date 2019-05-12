package com.filetransfer.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
	final static Logger logger = Logger.getLogger(Utils.class.getName());
	private static final String appProps = "application.properties";
	
	public static Properties getProperties() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream(appProps)) {
		    properties.load(resourceStream);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error trying to get properties file.", e);
		}
	    return properties;
	}
	
	public static String getProperty(String key) {
		return (String)getProperties().getProperty(key);
	}

	public static void setProperty(String key, String value) {
		Properties properties = getProperties();
		properties.setProperty(key, value);
		Path propertyFile = Paths.get("src/main/resources/"+appProps);
		try {
			Writer propWriter = Files.newBufferedWriter(propertyFile);
            properties.store(propWriter, "Application Properties");
            propWriter.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error trying to get properties file.", e);
		}		
	}
}
