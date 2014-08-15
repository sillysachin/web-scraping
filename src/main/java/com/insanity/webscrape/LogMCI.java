package com.insanity.webscrape;

import java.io.IOException;

import org.apache.log4j.Logger;

public class LogMCI {
	static Logger LOGGER = Logger.getLogger(LogMCI.class.getName());

	public static void main(String[] args) throws InterruptedException,
			IOException {
		// Properties props = new Properties();
		// props.load(LogMCI.class.getResourceAsStream("/log4j.properties"));
		// PropertyConfigurator.configure(props);
		//
		// LOGGER.info("Testing Log");
		// LOGGER.severe("Severe Msg");
		// try {
		// throw new RuntimeException();
		// } finally {
		// LOGGER.log(Level.SEVERE, "Severe");
		// }

		// PatternLayout layout = new PatternLayout();
		// String conversionPattern = "[%p] %d %c %M - %m%n";
		// layout.setConversionPattern(conversionPattern);
		//
		// // creates daily rolling file appender
		// DailyRollingFileAppender rollingAppender = new
		// DailyRollingFileAppender();
		// rollingAppender.setFile("app.log");
		// rollingAppender.setDatePattern("'.'yyyy-MM-dd");
		// rollingAppender.setLayout(layout);
		// rollingAppender.activateOptions();
		//
		// // configures the root logger
		// Logger rootLogger = Logger.getRootLogger();
		// rootLogger.setLevel(Level.DEBUG);
		// rootLogger.addAppender(rollingAppender);

		LOGGER.debug("this is a debug log message");
		LOGGER.info("this is a information log message");
		LOGGER.warn("this is a warning log message");
	}
}