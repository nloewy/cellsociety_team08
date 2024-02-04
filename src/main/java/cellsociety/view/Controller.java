package cellsociety.view;

import cellsociety.configuration.XMLParser;
import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.*;
import cellsociety.model.simulation.*;

import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.Iterator;
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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This class is the main driver of the simulation.
 * @author Alisha Zhang
 */


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
    if (dataFile == null){
      return;
    }
    xmlParser = new XMLParser();
    parseFile(dataFile.getPath());

    setSimulation(); //loads view and model

    speed = 1;

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
    System.out.println(neighborhoodTypeString);
    loadSimulationModel(xmlParser.getHeight(), xmlParser.getWidth(), neighborhoodType, xmlParser.getStates(), xmlParser.getType());
    System.out.println(xmlParser.getType());
    loadSimulationScene(xmlParser.getType(), xmlParser.getTitle(), xmlParser.getHeight(),xmlParser.getWidth());
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
      case PERCOLATION -> new PercolationSimulation(numRows, numCols, neighborhoodType, stateList, xmlParser.getParameters().get("percolatedNeighbors").intValue());
      case FIRE -> new FireSimulation(numRows, numCols, neighborhoodType, stateList, xmlParser.getParameters().get("neighborsToIgnite").intValue(), xmlParser.getParameters().get("probTreeIgnites"), xmlParser.getParameters().get("probTreeCreated"));
      case SCHELLING -> new SchellingSimulation(numRows, numCols, neighborhoodType, stateList, xmlParser.getParameters().get("proportionNeededToStay"));
      case WATOR -> new WatorSimulation(numRows, numCols, neighborhoodType, stateList, xmlParser.getParameters().get("fishAgeOfReproduction").intValue(), xmlParser.getParameters().get("sharkAgeOfReproduction").intValue(), xmlParser.getParameters().get("initialEnergy").intValue(),  xmlParser.getParameters().get("energyBoost").intValue());
      default -> null;
    };
  }


  private void loadSimulationScene(String simulationType, String simulationName, int numRows, int numCols) {
    Map<String, EventHandler<ActionEvent>> handlers = makeMap();
    simulationPage = new SimulationPage(simulationType, simulationName, numRows, numCols, handlers, simulationModel.getIterator());
    System.out.println(simulationName);
    stage.setScene(simulationPage.getSimulationScene());
    stage.show();

    simulationPage.setSpeedSliderHandler((observable, oldValue, newValue) -> {
      speed = newValue.intValue();
      double frameDuration = 1.0 / (speed * SECOND_DELAY);
      animation.setRate(speed);
      animation.setDelay(Duration.seconds(frameDuration));
      simulationPage.updateSpeedLabel(speed);
    });

  }

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

  private void onPauseSimulation() {
    pauseSimulation();
  }

  private void onSaveSimulation() {
    try {
      ArrayList<Integer> newStates = new ArrayList<>();
      Iterator<Cell> iterator = simulationModel.getIterator();
      while (iterator.hasNext()) {
        newStates.add(iterator.next().getCurrentState());
      }
      xmlParser.setStates(newStates);
      xmlParser.createXML("savedSimulation"+xmlParser.getType(), xmlParser.getType().toLowerCase());

      showMessage(AlertType.INFORMATION, String.format("File saved \u2713"));
    }
    catch (ParserConfigurationException | TransformerException e){
      e.printStackTrace();
    }
  }


  private void onStartSimulation() {
    simulationRunning = true;
  }


  private void onInfoButtonClicked() {
    showMessage(AlertType.INFORMATION, String.format(xmlParser.getDisplayDescription()));
  }


  private void onNewSimulationClicked() {
    simulationRunning = false;
    File dataFile = chooseFile();
    if (dataFile == null){
      return;
    }
    parseFile(dataFile.getPath());
    setSimulation();
  }

  private void onResetSimulation() {
    simulationRunning = false;
    simulationModel.initializeMyGrid(xmlParser.getHeight(), xmlParser.getWidth(), xmlParser.getStates());
    simulationPage.updateView(simulationModel.getIterator());
  }

  /**
   *
   * @param type
   * @param message
   */
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
