package cellsociety.exception;

/**
 * The InvalidStateException class handles the error in which a state read from the XML
 * configuration file is not a valid state for the simulation.
 *
 * @author Judy He
 */
public class InvalidStateException extends Exception {

  /**
   * Constructor for initializing an InvalidStateException given an error message
   */
  public InvalidStateException(String message) {
    super(message);
  }


}
