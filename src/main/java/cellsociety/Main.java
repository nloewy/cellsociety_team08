package cellsociety;

import cellsociety.view.Controller;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Feel free to completely change this code or delete it entirely.
 */
public class
Main extends Application {

  // kind of data files to look for
//  public static final String DATA_FILE_EXTENSION = "*.xml";
  // default to start in the data folder to make it easy on the user to find
//  public static final String DATA_FILE_FOLDER = System.getProperty("user.dir") + "/data";
  // NOTE: make ONE chooser since generally accepted behavior is that it remembers where user left it last
//  private final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  // internal configuration file
//  public static final String INTERNAL_CONFIGURATION = "cellsociety.Version";


  /**
   * Start the program, give complete control to JavaFX.
   * <p>
   * Default version of main() is actually included within JavaFX, so this is not technically
   * necessary!
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * @see Application#start(Stage)
   */
  @Override
  public void start(Stage primaryStage) {
    new Controller();
  }
}
