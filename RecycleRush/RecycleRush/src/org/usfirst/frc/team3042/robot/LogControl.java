package org.usfirst.frc.team3042.robot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogControl {
	//root name for logging
	private static final String LOG_ROOT = "RecycleRush";

	//formatting strings for the file
	private static final String LOGGING_FOLDER = "/usr/local/frc/log/";
    private static final String FOLDER_FORMAT = "yyyy/MM";
    private static final String FILE_FORMAT = "/dd-HHmmss";

    //Create the logger
	public static final Logger logger = Logger.getLogger(LOG_ROOT);
	
	// #### THIS LINE SETS WHAT MESSAGES WE RETAIN AND WHAT MESSAGES WE DISCARD ####
	public static final Level LOG_DEPTH = Level.ALL;
	// #### END ####
	
	// static block to set up the logger for the methods
	static {
		//Set the system reporting format
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT.%1$tL][%3$s][%4$s] %5$s%n");
		logger.setLevel(LOG_DEPTH);
	}

	/**
	 * Get a logger with an source other than root.
	 */
	public static Logger getLogger(Class<?> cls){
		return Logger.getLogger(LOG_ROOT + "." + cls.getSimpleName());
	}
	
	private static ConsoleHandler addHandler(final OutputStream out) {
		ConsoleHandler handler = new ConsoleHandler() {{
			setOutputStream(out);
		}};
		handler.setLevel(LOG_DEPTH);
		logger.addHandler(handler);
		return handler;
	}
	
	/**
	 * Start the console output
	 */
	public static void startConsole() {
		//add the console to the logger handler list
		addHandler(System.out);
	}
	
	/**
	 * create a string for the folder path of the log file.
	 * Uses the current date to organize logs.
	 * @param now
	 * @return
	 */
	private static String getFolderName(Date now){
	    final DateFormat folderTimeStamp = new SimpleDateFormat(FOLDER_FORMAT);
		return LOGGING_FOLDER + folderTimeStamp.format(now);
	}
	
	/**
	 * create a string for the logging file name
	 */
	private static String getFileName(Date now) {
		final DateFormat logTimeStamp = new SimpleDateFormat(FILE_FORMAT);
		return logTimeStamp.format(now) + ".log";
	}
	
	/**
	 * Start the file output
	 */
	public static void startFile() {
		//Get the current date and time and create the folder and file names for the log
		final Date now = Calendar.getInstance(TimeZone.getTimeZone("America/Chicago"), Locale.US).getTime();
		String folderName = getFolderName(now);
		String fileName = getFileName(now);
		
		//create the directory and file objects
		final File dir = new File(folderName);
		final File file = new File(folderName + fileName);	
	
		//Try to open the file
		ConsoleHandler handler = null;
		try {
			dir.mkdirs(); 			//create the directory if it doesn't exist
			file.createNewFile();	//create the new file
						
			//add the file to the logger handler list
			handler = addHandler(new PrintStream(new FileOutputStream(file)));
		} catch (IOException e) {
			if (handler != null) logger.removeHandler(handler);
			logger.log(Level.SEVERE, "Failed to create logger file", e);
		}
	}
}