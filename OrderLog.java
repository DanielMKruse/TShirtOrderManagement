import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class OrderLog {

		public Logger logger;
		public LogManager logManager;
		FileHandler fHandle;
		
		public OrderLog(String filename) throws SecurityException, IOException {
			
			File f = new File(filename);
			
			if(!f.exists()) {
				f.createNewFile();
			}
			
			fHandle = new FileHandler(filename, true);
			
			logger = Logger.getLogger("test");
			logger.addHandler(fHandle);
			
			logManager = LogManager.getLogManager();
			
			SimpleFormatter format = new SimpleFormatter();
			fHandle.setFormatter(format);
			
		}

}


