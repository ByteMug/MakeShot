package net.makeshot.logs;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
	public static Logger logger;

	static {
		try {
			boolean append = true;
			FileHandler fh = new FileHandler(
					System.getProperty("user.home") + File.separator
							+ ".MakeShot" + File.separator + "log.txt", append);
		
			fh.setFormatter(new SimpleFormatter());
			logger = Logger.getLogger("TestLog");
			logger.addHandler(fh);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
