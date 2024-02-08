package cellsociety.exception;
/**
 * The InvalidValueException class handles the error an invalid value is read for a certain parameter in the XML configuration file
 *
 * @author Judy He
 */
public class InvalidValueException extends RuntimeException{

  /**
   * Initializes an InvalidValueException given an error message
   *
   * @param message, message to be displayed when exception is thrown
   */
  public InvalidValueException(String message) {
    super(message);
  }

  /**
   * Initializes an InvalidValueException based on a caught exception with a different message.
   *
   * @param message, message to be displayed when exception is thrown
   * @param cause, the other exception that caused this exception
   */
  public InvalidValueException(String message, Throwable cause) {
    super(message, cause);
  }
}
