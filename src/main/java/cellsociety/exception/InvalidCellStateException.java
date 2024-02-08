package cellsociety.exception;

/**
 * The InvalidCellStateException extends RuntimeException to handle any errors related to the states
 * of cells read from an XML configuration file
 *
 * @author Judy He
 */
public class InvalidCellStateException extends RuntimeException {

  /**
   * Initialize a new InvalidCellStateException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public InvalidCellStateException(String message) {
    super(message);
  }

  /**
   * Initialize a new InvalidCellStateException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InvalidCellStateException
   */
  public InvalidCellStateException(String message, Throwable cause) {
    super(message, cause);
  }
}
