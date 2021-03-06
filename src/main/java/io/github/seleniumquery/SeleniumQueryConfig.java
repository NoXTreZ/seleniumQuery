package io.github.seleniumquery;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * THe seleniumQuery config util class.
 *
 * @author acdcjunior
 * @author ricardo-sc
 *
 * @since 0.9.0
 */
public class SeleniumQueryConfig {
	
	private static final Log LOGGER = LogFactory.getLog(SeleniumQueryConfig.class);
	
	private static final String PROP_GLOBAL_TIMEOUT = "GLOBAL_TIMEOUT";
	private static final String PROP_WAITUNTIL_TIMEOUT = "WAITUNTIL_TIMEOUT";
	private static final String PROP_WAITUNTIL_POLLING_INTERVAL = "WAITUNTIL_POLLING_INTERVAL";
	
	/**
	 * All times are in milliseconds.
	 */
	private static long globalTimeout = 1501;
	private static long waitUntilTimeout = 10001;
	private static long waitUntilPollingInterval = 901;
	
	static {
		loadPropertiesFiles();
	}
	
	private static void loadPropertiesFiles() {
		try {
			loadPropertiesFileFromClasspath();

			globalTimeout = getLongProperty(PROP_GLOBAL_TIMEOUT, globalTimeout);
			waitUntilTimeout = getLongProperty(PROP_WAITUNTIL_TIMEOUT, waitUntilTimeout);
			waitUntilPollingInterval = getLongProperty(PROP_WAITUNTIL_POLLING_INTERVAL, waitUntilPollingInterval);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadPropertiesFileFromClasspath() throws IOException {
		properties = new Properties();
		LOGGER.debug("Attempting to load properties from seleniumQuery.properties in classpath.");
		InputStream in = SeleniumQueryConfig.class.getClassLoader().getResourceAsStream("seleniumQuery.properties");
		if (in == null) {
			LOGGER.info("No seleniumQuery.properties found in classpath, falling back to defaults.");
		}
		properties.load(in);
		in.close();
	}
	
	private static long getLongProperty(String propertyName, long defaultValue) {
		String propertyAsString = SeleniumQueryConfig.properties.getProperty(propertyName);
		if (propertyAsString == null || propertyAsString.trim().isEmpty()) {
			return defaultValue;
		}
		return Long.valueOf(propertyAsString);
	}
	
	private static Properties properties;
	
	public static String get(String property) {
		return properties.getProperty(property);
	}	
	
	public static long getGlobalTimeout() {
		return globalTimeout;
	}
	public static long getWaitUntilTimeout() {
		return waitUntilTimeout;
	}
	public static long getWaitUntilPollingInterval() {
		return waitUntilPollingInterval;
	}

}