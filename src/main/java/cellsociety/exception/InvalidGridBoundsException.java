package cellsociety.exception;

/**
 * The InvalidGridBoundsException class handles the error when the user loads a configuration file
 * that has cell locations specified outside the grid’s bounds
 *
 * @author Judy He
 */
public class InvalidGridBoundsException extends RuntimeException {

  /**
   * Initializes an InvalidGridBoundsException given an error message
   *
   * @param message, message to be displayed when exception is thrown
   */
  public InvalidGridBoundsException(String message) {
    super(message);
  }

  /**
   * Initializes an InvalidGridBoundsException based on a caught exception with a different message.
   *
   * @param message, message to be displayed when exception is thrown
   * @param cause, the other exception that caused this exception
   */
  public InvalidGridBoundsException(String message, Throwable cause) {
    super(message, cause);
  }
}
