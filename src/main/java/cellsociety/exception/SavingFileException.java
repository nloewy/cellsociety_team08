package cellsociety.exception;

/**
 * The FileSavingException extends RuntimeException to handle any errors related to the converting
 * data to XML format and saving a new XML file.
 *
 * @author Judy He
 */
public class SavingFileException extends RuntimeException {

  /**
   * Initialize a new FileSavingException given an error message
   *
   * @param message, error message to be displayed by GUI
   */
  public SavingFileException(String message) {
    super(message);
  }

  /**
   * Initialize a new FileSavingException given an error message and another exception
   *
   * @param message, error message to be displayed by GUI
   * @param cause,   the exception that prompted the FileSavingException
   */
  public SavingFileException(String message, Throwable cause) {
    super(message, cause);
  }

}
