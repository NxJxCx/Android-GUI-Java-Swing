
package cellphoneguiapp.njc.utils;

/**
 *
 * @author Neil Jason Canete
 */
public class LocalDatabaseError extends Exception {
  public LocalDatabaseError(String databasename, String msg) {
    super("DATABASE `" + databasename + "` ERROR: " + msg);
  }
}
