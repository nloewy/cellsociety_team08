package cellsociety.view;

import cellsociety.Point;
import cellsociety.configuration.XmlParser;
import cellsociety.exception.InputMissingParametersException;
import cellsociety.exception.InvalidCellStateException;
import cellsociety.exception.InvalidFileFormatException;
import cellsociety.exception.InvalidGridBoundsException;
import cellsociety.exception.InvalidValueException;
import cellsociety.model.core.cell.Cell;
import cellsociety.model.neighborhood.ExtendedMooreNeighborhood;
import cellsociety.model.neighborhood.MooreNeighborhood;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.neighborhood.VonNeumannNeighborhood;
import cellsociety.model.simulation.FallingSandSimulation;
import cellsociety.model.simulation.FireSimulation;
import cellsociety.model.simulation.GameOfLifeSimulation;
import cellsociety.model.simulation.PercolationSimulation;
import cellsociety.model.simulation.SchellingSimulation;
import cellsociety.model.simulation.Simulation;
import cellsociety.model.simulation.SimulationRecord;
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
  public static final String UPLOAD_FILE_WINDOW_TITLE_KEY = "uploadFileWindowTitle";
  public static final String ABOUT_MIN_HEIGHT_KEY = "ABOUT_MIN_HEIGHT";
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
  private Save savePanel;

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
      setSimulation();
      setAnimation();
    } catch (InvalidFileFormatException | InvalidValueException | InvalidCellStateException |
             InputMissingParametersException | InvalidGridBoundsException e) {
      showMessage(AlertType.ERROR, e.getMessage());
    }
  }

  private void setAnimation() {
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    double frameDuration = 1.0 / (simulationPage.configDouble(
        SECOND_DELAY_KEY)); // Calculate the duration for the KeyFrame
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(frameDuration), e -> step()));
    animation.play();
  }

  private void step() {
    if (simulationRunning) {
      simulationModel.transitionFunction();
      simulationModel.processUpdate();

      simulationPage.updateView(simulationModel.getIterator());
    }
  }


  private void parseFile(String filePath) throws InvalidValueException,
      InvalidFileFormatException,
      InvalidGridBoundsException,
      InputMissingParametersException,
      InvalidCellStateException {
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
    loadSimulationModel();
    loadSimulationScene();

    settingsPanel = new Settings(xmlParser.getLanguage(), xmlParser.getGridEdgeType(),
        xmlParser.getParameters(), xmlParser.getType(), event -> onApplyClicked());
    savePanel = new Save(xmlParser.getTitle(), xmlParser.getAuthor(),
        xmlParser.getDisplayDescription(), "", event -> onApplySaveClicked());
  }

  private void onApplySaveClicked() {
    savePanel.updateValues();
    savePanel.hideSavePanel();
    String title = savePanel.getTitle();
    String author = savePanel.getAuthor();
    String description = savePanel.getDescription();
    String file = savePanel.getSaveLocation();
    xmlParser.setTitle(title);
    xmlParser.setAuthor(author);
    xmlParser.setDescription(description);
    xmlParser.createXml(file + xmlParser.getType(),
        xmlParser.getType().toLowerCase());
  }

  private void onApplyClicked() {
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
  private Neighborhood getNeighborhoodObject(String neighborhoodTypeString)
      throws IllegalStateException {
    return switch (neighborhoodTypeString) {
      case "Moore" -> new MooreNeighborhood();
      case "ExtendedMoore" -> new ExtendedMooreNeighborhood();
      case "VonNeumann" -> new VonNeumannNeighborhood();
      default -> throw new IllegalStateException("Unexpected value: " + neighborhoodTypeString);
    };
  }

  /**
   * Sets up the simulation model component
   */
  private void loadSimulationModel() {
    int numRows = xmlParser.getHeight();
    int numCols = xmlParser.getWidth();
    Neighborhood neighborhoodType = getNeighborhoodObject(xmlParser.getNeighborhoodType());
    List<Integer> stateList = xmlParser.getStates();
    String simulationType = xmlParser.getType();
    String gridType = xmlParser.getGridEdgeType();
    String cellShape = xmlParser.getCellShape();
    SimulationRecord record = new SimulationRecord(xmlParser.getParameters(), gridType, cellShape);
    simulationRunning = false;
    simulationModel = switch (simulationType) {
      case GAME_OF_LIFE ->
          new GameOfLifeSimulation(numRows, numCols, neighborhoodType, stateList, record);
      case PERCOLATION ->
          new PercolationSimulation(numRows, numCols, neighborhoodType, stateList, record);
      case FIRE -> new FireSimulation(numRows, numCols, neighborhoodType, stateList, record);
      case SCHELLING ->
          new SchellingSimulation(numRows, numCols, neighborhoodType, stateList, record);
      case WATOR -> new WatorSimulation(numRows, numCols, neighborhoodType, stateList, record);
      case SUGAR -> new SugarSimulation(numRows, numCols, neighborhoodType, stateList, record);
      case FALLING ->
          new FallingSandSimulation(numRows, numCols, neighborhoodType, stateList, record);
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

    getParamsForSimulationPage();

    simulationPage = new SimulationPage(getParamsForSimulationPage(), handlers,
        simulationModel.getIterator(), allVertices);
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

  private Map<String, String> getParamsForSimulationPage() {
    Map<String, String> params = new HashMap<>();
    params.put("Language", xmlParser.getLanguage());
    params.put("InitialSlider", Integer.toString(xmlParser.getInitialSlider()));
    params.put("Simulation", xmlParser.getType());
    params.put("SimulationName", xmlParser.getTitle());
    params.put("Height", Integer.toString(xmlParser.getHeight()));
    params.put("Width", Integer.toString(xmlParser.getWidth()));
    return params;
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
    map.put("multiSimulationHandler", event -> createParallelWindow());
    return map;
  }

  private void createParallelWindow() {
    new Controller();
  }

  private void onSettingsClicked() {
    pauseSimulation();
    settingsPanel.showSettingsPanel();
  }

  private void onPauseSimulation() {
    pauseSimulation();
  }

  /**
   * show edit save window, let user edit information for the file they are saving.
   */
  private void onSaveSimulation() {
    pauseSimulation();
    savePanel.showSavePanel();
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
    TextArea textArea = formatTextAreaToDisplay();
    simulationInfo.getDialogPane().setContent(textArea);
    simulationInfo.showAndWait();
  }

  private TextArea formatTextAreaToDisplay() {
    TextArea textArea = new TextArea();
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setText(
        xmlParser.getDisplayDescription() + "\n\n" +
            "Author: " + xmlParser.getAuthor() + "\n\n" +
            "Parameters: " + xmlParser.getParameters()
    );
    textArea.setMinHeight(simulationPage.configInt(ABOUT_MIN_HEIGHT_KEY));
    return textArea;
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
    pauseSimulation();
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

  private void switchLanguage(String language) {
    this.textConfig = ResourceBundle.getBundle(
        switch (language) {
          case "French" -> FRENCH_TEXT;
          case "German" -> GERMAN_TEXT;
          case "Spanish" -> SPANISH_TEXT;
          case "Mandarin" -> MANDARIN_TEXT;
          default -> TEXT_CONFIGURATION;
        });
    simulationPage.switchTextConfig(this.textConfig);
    simulationPage.switchButtonLanguage(language);
  }

}
