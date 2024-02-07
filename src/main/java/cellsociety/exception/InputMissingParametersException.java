package cellsociety.exception;

public class InputMissingParametersException extends RuntimeException {

  public InputMissingParametersException(String message) {
    super(message);
  }

  public InputMissingParametersException(String message, Throwable cause) {
    super(message, cause);
  }
}
