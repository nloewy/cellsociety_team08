package cellsociety.view;

import cellsociety.Point;
import cellsociety.configuration.XmlParser;
import cellsociety.exception.InputMissingParametersException;
import cellsociety.exception.InvalidCellStateException;
import cellsociety.exception.InvalidFileFormatException;
import cellsociety.exception.InvalidGridBoundsException;
import cellsociety.exception.InvalidValueException;
import cellsociety.model.core.cell.Cell;
import cellsociety.model.neighborhood.MooreNeighborhood;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.neighborhood.VonNeumannNeighborhood;
import cellsociety.model.simulation.FallingSandSimulation;
import cellsociety.model.simulation.FireSimulation;
import cellsociety.model.simulation.GameOfLifeSimulation;
import cellsociety.model.simulation.PercolationSimulation;
import cellsociety.model.simulation.Records.FallingSandRecord;
import cellsociety.model.simulation.Records.FireRecord;
import cellsociety.model.simulation.Records.GameOfLifeRecord;
import cellsociety.model.simulation.Records.PercolationRecord;
import cellsociety.model.simulation.Records.SchellingRecord;
import cellsociety.model.simulation.Records.SugarRecord;
import cellsociety.model.simulation.Records.WatorRecord;
import cellsociety.model.simulation.SchellingSimulation;
import cellsociety.model.simulation.Simulation;
import cellsociety.model.simulation.SugarSimulation;
import cellsociety.model.simulation.WatorSimulation;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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

  //paths (will stay)
  public static final String TEXT_CONFIGURATION = "cellsociety.Text";
  public static final String FRENCH_TEXT = "cellsociety.TextFrench";
  public static final String GERMAN_TEXT = "cellsociety.TextGerman";
  public static final String MANDARIN_TEXT = "cellsociety.TextMandarin";
  public static final String SPANISH_TEXT = "cellsociety.TextSpanish";

  public static final String DATA_FILE_FOLDER = System.getProperty("user.dir") + "/data";
  public static final String DATA_FILE_EXTENSION = "*.xml";
  public static final String FIRE = "Fire";
  public static final String GAME_OF_LIFE = "GameOfLife";
  public static final String PERCOLATION = "Percolation";
  public static final String SCHELLING = "Schelling";
  public static final String WATOR = "Wator";
  public static final String SUGAR = "Sugar";
  public static final String FALLING = "Falling";
  public static final String UPLOAD_FILE_TEXT_KEY = "uploadFile";
  public static final String SECOND_DELAY_KEY = "SECOND_DELAY";
  public static final String FILE_SAVED_KEY = "fileSaved";
  public static final String UPLOAD_FILE_WINDOW_TITLE_KEY = "uploadFileWindowTitle";
  public static final String ABOUT_MIN_HEIGHT_KEY = "ABOUT_MIN_HEIGHT";
//  private final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  private Stage stage;
  private SimulationPage simulationPage;
  private XmlParser xmlParser;
  private Simulation simulationModel;
  private Timeline animation;
  private int speed;
  private boolean simulationRunning = false;
  private ResourceBundle textConfig;
  private FileChooser fileChooser;
  private Settings settingsPanel;
  private Boolean settingsChanged = false;
  private int sliderStart;

  /**
   * Constructs the controller class
   */
  public Controller() {
    try {
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
    } catch (InvalidFileFormatException | InvalidValueException | InvalidCellStateException |
             InputMissingParametersException | InvalidGridBoundsException e) {
      showMessage(AlertType.ERROR, e.getMessage());
      Platform.exit();
    }
  }

  private void step() {
    if (simulationRunning) {
      simulationModel.transitionFunction();
      simulationModel.processUpdate();

      simulationPage.updateView(simulationModel.getIterator());
    }
  }


  private void parseFile(String filePath)
      throws InvalidValueException, InvalidFileFormatException, InvalidGridBoundsException, InputMissingParametersException, InvalidCellStateException {
    xmlParser = new XmlParser();
    xmlParser.readXml(filePath);
  }

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

  private void setSimulation() {
    String neighborhoodTypeString = xmlParser.getNeighborhoodType();
    Neighborhood neighborhoodType = getNeighborhoodObject(neighborhoodTypeString);
    System.out.println(neighborhoodTypeString);
    loadSimulationModel(xmlParser.getHeight(), xmlParser.getWidth(), neighborhoodType,
        xmlParser.getStates(), xmlParser.getType(), xmlParser.getGridEdgeType(),
        xmlParser.getCellShape());
    System.out.println(xmlParser.getType());
    loadSimulationScene();
    settingsPanel = new Settings(xmlParser.getLanguage(), xmlParser.getGridEdgeType(), xmlParser.getParameters(),
        event -> onApplyClicked());
  }

  private void onApplyClicked() {
    settingsChanged = true;
    settingsPanel.saveChanges();
    settingsPanel.closeSettingsPanel();
    simulationModel.setParams(settingsPanel.getNewParameters());
    simulationModel.setEdgeType(settingsPanel.getNewEdgeType());
    simulationPage.toggleOnOffCellOutlines(settingsPanel.getOutlineType());
    switchLanguage(settingsPanel.getNewLanguage());
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
              xmlParser.getParameters().get("probTreeCreated"), gridType, cellShape));
      case SCHELLING -> new SchellingSimulation(numRows, numCols, neighborhoodType, stateList,
          new SchellingRecord(xmlParser.getParameters().get("proportionNeededToStay"), gridType,
              cellShape));
      case WATOR -> new WatorSimulation(numRows, numCols, neighborhoodType, stateList,
          new WatorRecord(xmlParser.getParameters().get("fishAgeOfReproduction").intValue(),
              xmlParser.getParameters().get("sharkAgeOfReproduction").intValue(),
              xmlParser.getParameters().get("initialEnergy").intValue(),
              xmlParser.getParameters().get("energyBoost").intValue(), gridType, cellShape));
      case SUGAR -> new SugarSimulation(numRows, numCols, neighborhoodType, stateList,
          new SugarRecord(xmlParser.getParameters().get("minVision").intValue(),
              xmlParser.getParameters().get("maxVision").intValue(),
              xmlParser.getParameters().get("minInitialSugar").intValue(),
              xmlParser.getParameters().get("maxInitialSugar").intValue(),
              xmlParser.getParameters().get("minMetabolism").intValue(),
              xmlParser.getParameters().get("maxMetabolism").intValue(),
              xmlParser.getParameters().get("growBackRate").intValue(),
              xmlParser.getParameters().get("numAgents").intValue(), gridType, cellShape));
      case FALLING -> new FallingSandSimulation(numRows, numCols, neighborhoodType, stateList,
          new FallingSandRecord(gridType, cellShape));
      default -> null;
    };
  }


  /**
   * sets up the simulation view component
   */
  private void loadSimulationScene() {
    Map<String, EventHandler<ActionEvent>> handlers = makeHandlersMap();
    Iterator<Cell> iter = simulationModel.getIterator();
    List<List<Point>> allVertices = new ArrayList<>();
    while (iter.hasNext()) {
      allVertices.add(iter.next().getVertices());
    }

    simulationPage = new SimulationPage(xmlParser.getInitialSlider(), xmlParser.getType(), xmlParser.getTitle(),
        xmlParser.getHeight(), xmlParser.getWidth(), handlers, simulationModel.getIterator(),
        allVertices);
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
  private Map<String, EventHandler<ActionEvent>> makeHandlersMap() {
    Map<String, EventHandler<ActionEvent>> map = new HashMap<>();
    map.put("newSimulationHandler", event -> onNewSimulationClicked());
    map.put("infoButtonHandler", event -> onInfoButtonClicked());
    map.put("startSimulationHandler", event -> onStartSimulation());
    map.put("saveSimulationHandler", event -> onSaveSimulation());
    map.put("pauseSimulationHandler", event -> onPauseSimulation());
    map.put("resetSimulationHandler", event -> onResetSimulation());
    map.put("settingsHandler", event -> onSettingsClicked());
    return map;
  }

  private void onSettingsClicked() {
    pauseSimulation();
    settingsPanel.showSettingsPanel();
  }

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

      if (settingsChanged) {
        xmlParser.setParameters(settingsPanel.getNewParameters());
      }

      xmlParser.createXml("savedSimulation" + xmlParser.getType(),
          xmlParser.getType().toLowerCase());

      showMessage(AlertType.INFORMATION, String.format(textConfig.getString(FILE_SAVED_KEY)));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void onStartSimulation() {
    simulationRunning = true;
    animation.setRate(simulationPage.getSliderValue());
  }

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
            "Parameters: " + xmlParser.getParameters()
    );
    textArea.setMinHeight(simulationPage.configInt(ABOUT_MIN_HEIGHT_KEY));

    simulationInfo.getDialogPane().setContent(textArea);
    simulationInfo.showAndWait();
  }


  private void onNewSimulationClicked() {
    try {
      simulationRunning = false;
      File dataFile = chooseFile();
      if (dataFile == null) {
        return;
      }
      parseFile(dataFile.getPath());
      setSimulation();
    } catch (InvalidFileFormatException | InvalidValueException | InvalidCellStateException |
             InputMissingParametersException | InvalidGridBoundsException e) {
      showMessage(AlertType.ERROR, e.getMessage());
    }
  }

  private void onResetSimulation() {
    simulationModel.createCellsAndGrid(xmlParser.getHeight(), xmlParser.getWidth(),
        xmlParser.getStates(), simulationModel.getCellShape(xmlParser.getCellShape()),
        getNeighborhoodObject(
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

  private void switchLanguage(String language){
    System.out.println("Language selected: " + language); // Print language value
    this.textConfig = ResourceBundle.getBundle(switch (language){
      case "French" -> FRENCH_TEXT;
      case "German" -> GERMAN_TEXT;
      case "Spanish" -> SPANISH_TEXT;
      case "Mandarin" -> MANDARIN_TEXT;
      default -> TEXT_CONFIGURATION;
    });
    simulationPage.switchTextConfig(this.textConfig);
    simulationPage.switchButtonConfig(language);
  }

}
