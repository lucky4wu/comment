package accew.modules.logger;
import org.slf4j.LoggerFactory;

public class Logger {
	
	public Logger() {
	}

	public static org.slf4j.Logger getLog(Class clazz) {
		return  LoggerFactory.getLogger(clazz);
	}
	
	
}
