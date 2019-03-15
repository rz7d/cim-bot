package milktea.cim.bot.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class AutoLogger {

  private static final Logger LOGGER = Logger
    .getLogger("cim-root://cf.azuredev.cim/bot/query?type=identity&class=" + AutoLogger.class);

  public static void info(String message) {
    LOGGER.log(new LogRecord(Level.INFO, message));
  }

  public static void warn(String message) {
    LOGGER.warning(message);
  }

  public static void fail(String message) {
    LOGGER.severe(message);
  }

  public static void trace(String message) {
    LOGGER.fine(message);
  }

  public static void debug(String message) {
    LOGGER.finest(message);
  }

}
