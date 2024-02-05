package cellsociety.exception;

/**
 * The InvalidGridDimensionException class handles the error in which the grid dimension does not
 * match the total number of states read from the XML configuration file.
 *
 * @author Judy He
 */
public class InvalidGridDimensionException extends Exception {

    /**
     *  Constructor for initializing an InvalidGridDimensionException given an error message
     */
    public InvalidGridDimensionException(String message) {
        super(message);
    }

}
