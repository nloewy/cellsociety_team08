package cellsociety.exception;

/**
 * The InvalidFileFormatException extends RuntimeException to handle any errors related to the file
 * format, file extension or file path
 *
 * @author Judy He
 */
public class InvalidFileFormatException extends RuntimeException{

  /**
   * Initialize a new InvalidFileFormatException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public InvalidFileFormatException(String message) {
    super(message);
  }

  /**
   * Initialize a new InvalidFileFormatException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause, the exception that prompted the InvalidFileFormatException
   */
  public InvalidFileFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
