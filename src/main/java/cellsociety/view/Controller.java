package cellsociety.view;

import cellsociety.configuration.XMLParser;
import cellsociety.model.neighborhood.*;
import cellsociety.model.simulation.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;


public class Controller {

  private Stage stage;
  private SceneManager sceneManager;
  private SimulationPage simulationPage;
  private XMLParser xmlParser;
  private Simulation simulationModel;
  private Timeline animation;
  private int speed;
  private boolean simulationRunning = false;

  public static final String INTERNAL_CONFIGURATION = "cellsociety.Version";
  public static final String TEXT_CONFIGURATION = "cellsociety.Text";
  public static final String DATA_FILE_FOLDER = System.getProperty("user.dir") + "/data";
  public static final String DATA_FILE_EXTENSION = "*.xml";
  private final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  public static final String FIRE = "Fire";
  public static final String GAME_OF_LIFE = "GameOfLife";
  public static final String PERCOLATION = "Percolation";
  public static final String SCHELLING = "Schelling";
  public static final String WATOR = "Wator";
  private static final double SECOND_DELAY = 1.0;



  public Controller() {
    stage = new Stage();

    ResourceBundle Text = ResourceBundle.getBundle(TEXT_CONFIGURATION);
    showMessage(AlertType.INFORMATION, String.format(Text.getString("uploadFile")));

    File dataFile = chooseFile();
    xmlParser = new XMLParser();
    parseFile(dataFile.getPath());
    System.out.println("in constructor: "+ xmlParser.getAuthor());

    setSimulation(); //loads view and model

    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    double frameDuration = 1.0 / (speed * SECOND_DELAY); // Calculate the duration for the KeyFrame
    animation.getKeyFrames().add(new KeyFrame(Duration.seconds(frameDuration), e -> step(SECOND_DELAY)));
    animation.play();
  }


  private void step(double secondDelay) {
    if (simulationRunning) {
      //update model
      simulationModel.transitionFunction();
      simulationModel.processUpdate();

      //update view
      simulationPage.updateView(simulationModel.getIterator());
    }
  }


  private void parseFile(String filePath) {
    xmlParser.readXML(filePath);
  }

  private void pauseSimulation(){
    simulationRunning = false;
  }

  private File chooseFile() {
    File dataFile = FILE_CHOOSER.showOpenDialog(stage);
    return dataFile;
  }


  private void setSimulation() {
    String neighborhoodTypeString = xmlParser.getNeighborhoodType();
    Neighborhood neighborhoodType = getNeighborhoodObject(neighborhoodTypeString);
    loadSimulationModel(xmlParser.getWidth(), xmlParser.getHeight(), neighborhoodType, xmlParser.getStates(), xmlParser.getType());
//
//    if (simulationModel == null) {
//      showMessage(AlertType.ERROR, "Error loading simulation model.");
//      return;
//    }
    loadSimulationScene(xmlParser.getTitle(), xmlParser.getWidth(), xmlParser.getHeight());
  }


  private Neighborhood getNeighborhoodObject(String neighborhoodTypeString) {
    System.out.println("getting neighborhood type");
    return switch (neighborhoodTypeString){
      case "adjacent" -> new AdjacentNeighborhood();
      case "cardinal" -> new CardinalNeighborhood();
      default -> throw new IllegalStateException("Unexpected value: " + neighborhoodTypeString);
    };
  }


  private void loadSimulationModel(int numRows, int numCols, Neighborhood neighborhoodType,
      List<Integer> stateList, String simulationType) {
    xmlParser.getParameters().forEach((key, value) -> System.out.println(key + ": " + value));

    simulationRunning = false;
      simulationModel = switch (simulationType) {
      case GAME_OF_LIFE -> new GameOfLifeSimulation(numRows, numCols, neighborhoodType, stateList, xmlParser.getParameters().get("aliveToAliveMin").intValue(), xmlParser.getParameters().get("deadToAliveMax").intValue(), xmlParser.getParameters().get("aliveToAliveMax").intValue(), xmlParser.getParameters().get("deadToAliveMin").intValue());
      case PERCOLATION -> new PercolationSimulation(numRows, numCols, neighborhoodType, stateList, xmlParser.getParameters().get("neighborsPercolatedRequired").intValue());
      case FIRE -> new FireSimulation(numRows, numCols, neighborhoodType, stateList, xmlParser.getParameters().get("neighborsToIgnite").intValue(), xmlParser.getParameters().get("probTreeIgnites"), xmlParser.getParameters().get("probTreeCreated"));
      case SCHELLING -> new SchellingSimulation(numRows, numCols, neighborhoodType, stateList, xmlParser.getParameters().get("proportionNeededToStay").intValue());
      case WATOR -> new WatorSimulation(numRows, numCols, neighborhoodType, stateList, xmlParser.getParameters().get("fishReproductionTime").intValue(), xmlParser.getParameters().get("sharkReproductionTime").intValue(), xmlParser.getParameters().get("energyFromFish").intValue(), xmlParser.getParameters().get("sharkInitialEnergy").intValue());
      default -> null;
    };
  }


  private void loadSimulationScene(String simulationName, int numRows, int numCols) {
    simulationPage = new SimulationPage(simulationName, numRows, numCols, event -> onNewSimulationClicked(), event -> onInfoButtonClicked(), simulationModel.getIterator());
    stage.setScene(simulationPage.getSimulationScene());
    stage.show();
  }


  private void onInfoButtonClicked() {
    showMessage(AlertType.INFORMATION, String.format(xmlParser.getDisplayDescription()));
  }


  private void onNewSimulationClicked() {
    //TODO: how to pause current simulation? do we need to save current simulation?
    File dataFile = chooseFile();
//    String rawPath = dataFile.getPath();
//    String[] parts = rawPath.split("cellsociety_team08");
//    parseFile(parts[1]);
    parseFile(dataFile.getPath());
    setSimulation();
  }


  public void showMessage(AlertType type, String message) {
    new Alert(type, message).showAndWait();
  }


  public double getVersion() {
    ResourceBundle resources = ResourceBundle.getBundle(INTERNAL_CONFIGURATION);
    return Double.parseDouble(resources.getString("Version"));
  }


  private static FileChooser makeChooser(String extensionAccepted) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    result.setInitialDirectory(new File(DATA_FILE_FOLDER));
    result.getExtensionFilters()
        .setAll(new ExtensionFilter("Data Files", extensionAccepted));
    return result;
  }
}
