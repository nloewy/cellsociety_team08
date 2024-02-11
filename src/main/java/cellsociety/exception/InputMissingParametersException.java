package cellsociety.exception;

/**
 * The InputMissingParametersException extends RuntimeException to handle any errors related to the
 * parameters in the XML configuration file.
 *
 * @author Judy He
 */
public class InputMissingParametersException extends RuntimeException {

  /**
   * Initialize a new InputMissingParametersException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public InputMissingParametersException(String message) {
    super(message);
  }

  /**
   * Initialize a new InputMissingParametersException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the InputMissingParametersException
   */
  public InputMissingParametersException(String message, Throwable cause) {
    super(message, cause);
  }
}
