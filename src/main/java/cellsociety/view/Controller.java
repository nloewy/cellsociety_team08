package cellsociety.view;

import cellsociety.configuration.XmlParser;
import cellsociety.model.core.cell.Cell;
import cellsociety.model.neighborhood.MooreNeighborhood;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.neighborhood.VonNeumannNeighborhood;
import cellsociety.model.simulation.FireSimulation;
import cellsociety.model.simulation.GameOfLifeSimulation;
import cellsociety.model.simulation.PercolationSimulation;
import cellsociety.model.simulation.Records.FireRecord;
import cellsociety.model.simulation.Records.GameOfLifeRecord;
import cellsociety.model.simulation.Records.PercolationRecord;
import cellsociety.model.simulation.Records.SchellingRecord;
import cellsociety.model.simulation.Records.WatorRecord;
import cellsociety.model.simulation.SchellingSimulation;
import cellsociety.model.simulation.Simulation;
//import cellsociety.model.simulation.WatorSimulation;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class is the main driver of the simulation.
 *
 * @author Alisha Zhang
 */


public class Controller {

  private Stage stage;
  private SimulationPage simulationPage;
  private XmlParser xmlParser;
  private Simulation simulationModel;
  private Timeline animation;
  private int speed;
  private boolean simulationRunning = false;
  private ResourceBundle textConfig;
  private FileChooser fileChooser;


  //paths (will stay)
  public static final String TEXT_CONFIGURATION = "cellsociety.Text";
  public static final String DATA_FILE_FOLDER = System.getProperty("user.dir") + "/data";
  public static final String DATA_FILE_EXTENSION = "*.xml";
//  private final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);

  public static final String FIRE = "Fire";
  public static final String GAME_OF_LIFE = "GameOfLife";
  public static final String PERCOLATION = "Percolation";
  public static final String SCHELLING = "Schelling";
  public static final String WATOR = "Wator";

  public static final String UPLOAD_FILE_TEXT_KEY = "uploadFile";
  public static final String SECOND_DELAY_KEY = "SECOND_DELAY";
  public static final String FILE_SAVED_KEY = "fileSaved";
  public static final String UPLOAD_FILE_WINDOW_TITLE_KEY = "uploadFileWindowTitle";
  public static final String ABOUT_MIN_HEIGHT_KEY = "ABOUT_MIN_HEIGHT";

  /**
   * Constructs the controller class
   */
  public Controller() {
    stage = new Stage();

    textConfig = ResourceBundle.getBundle(TEXT_CONFIGURATION);
    fileChooser = makeChooser(DATA_FILE_EXTENSION);
    showMessage(AlertType.INFORMATION, String.format(textConfig.getString(UPLOAD_FILE_TEXT_KEY)));

    File dataFile = chooseFile();
    if (dataFile == null) {
      return;
    }
    xmlParser = new XmlParser();
    parseFile(dataFile.getPath());

    setSimulation(); //loads view and model

    speed = 1;

    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    double frameDuration = 1.0 / (speed * simulationPage.configDouble(
        SECOND_DELAY_KEY)); // Calculate the duration for the KeyFrame
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(frameDuration), e -> step()));
    animation.play();
  }

  /**
   * this method is responsible for calling update on the model and the view upon each step in the
   * simulation
   */
  private void step() {
    if (simulationRunning) {
      //update model
      simulationModel.transitionFunction();
      simulationModel.processUpdate();

      //update view
      simulationPage.updateView(simulationModel.getIterator());
    }
  }


  /**
   * Calls the xmlParser to read the xml file according to the specified filepath
   *
   * @param filePath: the file path to the xml file.
   */
  private void parseFile(String filePath) {
    xmlParser.readXml(filePath);
  }


  /**
   * pauses the simulation
   */
  private void pauseSimulation() {
    simulationRunning = false;
  }


  /**
   * Opens a dialog box for user to choose the xml file they want to run
   *
   * @return returns the datafile the user selected
   */
  private File chooseFile() {
    File dataFile = fileChooser.showOpenDialog(stage);
    return dataFile;
  }


  /**
   * Set up the simulation model and view
   */
  private void setSimulation() {
    String neighborhoodTypeString = xmlParser.getNeighborhoodType();
    Neighborhood neighborhoodType = getNeighborhoodObject(neighborhoodTypeString);
    System.out.println(neighborhoodTypeString);
    loadSimulationModel(xmlParser.getHeight(), xmlParser.getWidth(), neighborhoodType,
        xmlParser.getStates(), xmlParser.getType(), xmlParser.getGridEdgeType(),
        xmlParser.getCellShape());
    System.out.println(xmlParser.getType());
    loadSimulationScene(xmlParser.getType(), xmlParser.getTitle(), xmlParser.getHeight(),
        xmlParser.getWidth());
  }


  /**
   * gets the neighborhood object based on the neighborhood type string
   *
   * @param neighborhoodTypeString a string that specifies which type of neighborhood the current
   *                               simulation is using
   * @return returns the neighborhood object
   */
  private Neighborhood getNeighborhoodObject(String neighborhoodTypeString) {
    return switch (neighborhoodTypeString) {
      case "Moore" -> new MooreNeighborhood();
      case "VonNeumann" -> new VonNeumannNeighborhood();
      default -> throw new IllegalStateException("Unexpected value: " + neighborhoodTypeString);
    };
  }




  /**
   * Sets up the simulation model component
   *
   * @param numRows          the integer number of rows in the simulation grid
   * @param numCols          the integer number of columns in the simulation grid
   * @param neighborhoodType the neighborhood type object
   * @param stateList        a list that specifies the initial state of all cells in the grid
   * @param simulationType   a string that specifies the simulation type
   */

  private void loadSimulationModel(int numRows, int numCols, Neighborhood neighborhoodType,
      List<Integer> stateList, String simulationType, String gridType, String cellShape) {
    xmlParser.getParameters().forEach((key, value) -> System.out.println(key + ": " + value));

    simulationRunning = false;
    simulationModel = switch (simulationType) {
      case GAME_OF_LIFE -> new GameOfLifeSimulation(numRows, numCols, neighborhoodType, stateList,
          new GameOfLifeRecord(xmlParser.getParameters().get("aliveToAliveMin").intValue(),
          xmlParser.getParameters().get("aliveToAliveMax").intValue(),
          xmlParser.getParameters().get("deadToAliveMin").intValue(),
          xmlParser.getParameters().get("deadToAliveMax").intValue(), gridType, cellShape));
      case PERCOLATION -> new PercolationSimulation(numRows, numCols, neighborhoodType, stateList,
          new PercolationRecord(xmlParser.getParameters().get("percolatedNeighbors").intValue(),
              gridType, cellShape));
      case FIRE -> new FireSimulation(numRows, numCols, neighborhoodType, stateList,
          new FireRecord(xmlParser.getParameters().get("neighborsToIgnite").intValue(),
          xmlParser.getParameters().get("probTreeIgnites"),
          xmlParser.getParameters().get("probTreeCreated"),gridType, cellShape));
      case SCHELLING -> new SchellingSimulation(numRows, numCols, neighborhoodType, stateList,
          new SchellingRecord(xmlParser.getParameters().get("proportionNeededToStay"),gridType, cellShape));
//      case WATOR -> new WatorSimulation(numRows, numCols, neighborhoodType, stateList,
//          new WatorRecord(xmlParser.getParameters().get("fishAgeOfReproduction").intValue(),
//          xmlParser.getParameters().get("sharkAgeOfReproduction").intValue(),
//          xmlParser.getParameters().get("initialEnergy").intValue(),
//          xmlParser.getParameters().get("energyBoost").intValue(), gridType, cellShape));
      default -> null;
    };
  }


  /**
   * sets up the simulation view component
   *
   * @param simulationType a string that specifies the type of the simulation
   * @param simulationName a string that specifies the name of the simulation (type+pattern)
   * @param numRows        the integer number of rows in the grid
   * @param numCols        the integer number of columns in the grid
   */
  private void loadSimulationScene(String simulationType, String simulationName, int numRows,
      int numCols) {
    Map<String, EventHandler<ActionEvent>> handlers = makeMap();
    simulationPage = new SimulationPage(simulationType, simulationName, numRows, numCols, handlers,
        simulationModel.getIterator());
    System.out.println(simulationName);
    stage.setScene(simulationPage.getSimulationScene());
    stage.show();

    simulationPage.setSpeedSliderHandler((observable, oldValue, newValue) -> {
      speed = newValue.intValue();
      double frameDuration = 1.0 / (speed * simulationPage.configDouble(SECOND_DELAY_KEY));
      animation.setRate(speed);
      animation.setDelay(Duration.seconds(frameDuration));
      simulationPage.updateSpeedLabel(speed);
    });
  }


  /**
   * makes the map of event handlers to pass into the simulation view
   *
   * @return returns the map of handler name to the event handler
   */
  private Map<String, EventHandler<ActionEvent>> makeMap() {
    Map<String, EventHandler<ActionEvent>> map = new HashMap<>();
    map.put("newSimulationHandler", event -> onNewSimulationClicked());
    map.put("infoButtonHandler", event -> onInfoButtonClicked());
    map.put("startSimulationHandler", event -> onStartSimulation());
    map.put("saveSimulationHandler", event -> onSaveSimulation());
    map.put("pauseSimulationHandler", event -> onPauseSimulation());
    map.put("resetSimulationHandler", event -> onResetSimulation());
    return map;
  }


  /**
   * pauses the simulation when the pause button is clicked
   */
  private void onPauseSimulation() {
    pauseSimulation();
  }


  /**
   * saves the current state of the simulation as a new xml file when the save simulation button is
   * pressed
   */
  private void onSaveSimulation() {
    try {
      ArrayList<Integer> newStates = new ArrayList<>();
      Iterator<Cell> iterator = simulationModel.getIterator();
      while (iterator.hasNext()) {
        newStates.add(iterator.next().getCurrentState());
      }
      xmlParser.setStates(newStates);
      xmlParser.createXml("savedSimulation" + xmlParser.getType(),
          xmlParser.getType().toLowerCase());

      showMessage(AlertType.INFORMATION, String.format(textConfig.getString(FILE_SAVED_KEY)));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * un-pause the simulation when the start button is clicked
   */
  private void onStartSimulation() {
    simulationRunning = true;
  }


  /**
   * shows the simulation information dialog box when about button is pressed
   */
  private void onInfoButtonClicked() {
    Alert simulationInfo = new Alert(AlertType.INFORMATION);

    pauseSimulation();

    simulationInfo.setHeaderText(null);
    simulationInfo.setTitle(xmlParser.getTitle());

    TextArea textArea = new TextArea();
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setText(
        xmlParser.getDisplayDescription() + "\n\n" +
            "Author: " + xmlParser.getAuthor() + "\n\n" +
            "States: " + xmlParser.getStateColor() + "\n" +
            "Parameters: " + xmlParser.getParameters()
    );
    textArea.setMinHeight(simulationPage.configInt(ABOUT_MIN_HEIGHT_KEY));

    simulationInfo.getDialogPane().setContent(textArea);
    simulationInfo.showAndWait();
  }


  /**
   * switch simulation and reload the view and model components of the new simulation when new
   * simulation button is clicked
   */
  private void onNewSimulationClicked() {
    simulationRunning = false;
    File dataFile = chooseFile();
    if (dataFile == null) {
      return;
    }
    parseFile(dataFile.getPath());
    setSimulation();
  }


  /**
   * returns simulation to its initial state when reset button is pressed
   */
  private void onResetSimulation() {
    simulationModel.createCellsAndGrid(xmlParser.getHeight(), xmlParser.getWidth(),
        xmlParser.getStates(), simulationModel.getCellShape(xmlParser.getCellShape()), getNeighborhoodObject(
            xmlParser.getNeighborhoodType()));
    simulationPage.updateView(simulationModel.getIterator());
    simulationPage.resetGraph();
  }


  /**
   * Shows a message dialog box according to the type and message text arguments
   *
   * @param type    an AlertType object that specifies the type of the message
   * @param message a string that contains the content of the message
   */
  public void showMessage(AlertType type, String message) {
    new Alert(type, message).showAndWait();
  }


  /**
   * set up the filechooser
   *
   * @param extensionAccepted a string that specifies what type of file extensions are accepted
   * @return returns the FileChooser object.
   */
  private FileChooser makeChooser(String extensionAccepted) {
    FileChooser result = new FileChooser();
    result.setTitle(textConfig.getString(UPLOAD_FILE_WINDOW_TITLE_KEY));

    result.setInitialDirectory(new File(DATA_FILE_FOLDER));
    result.getExtensionFilters()
        .setAll(new ExtensionFilter("Data Files", extensionAccepted));
    return result;
  }
}
