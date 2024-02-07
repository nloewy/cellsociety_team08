package cellsociety.exception;

public class InvalidCellStateException extends RuntimeException{

  public InvalidCellStateException(String message) {
    super(message);
  }

  public InvalidCellStateException(String message, Throwable cause) {
    super(message, cause);
  }
}
