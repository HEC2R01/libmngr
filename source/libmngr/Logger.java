package libmngr;

import java.io.PrintStream;
import java.lang.StringBuilder;

public class Logger {
  private static PrintStream[] outStreams;

  public static void initialize(PrintStream[] streams) {
    outStreams = streams;
  }

  public static void info(String msg, Object... args) {
    StringBuilder builder = new StringBuilder("[info]: ").append(String.format(msg, args));
    for (PrintStream stream : outStreams) {
      stream.println(builder.toString());
    }
  }
}
