package net.makeshot.logs;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LOG {
	public static void error(Exception e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			Logging.logger.warning(sw.toString());
		}
	}
}
