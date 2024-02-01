package cellsociety.view;

import cellsociety.configuration.XMLParser;
import cellsociety.model.neighborhood.AdjacentNeighborhood;
import cellsociety.model.neighborhood.CardinalNeighborhood;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {

  private Stage stage;
  private SceneManager sceneManager;
  private SimulationPage simulationPage;
  private XMLParser xmlParser;
  private Simulation simulationModel;

  public static final String INTERNAL_CONFIGURATION = "cellsociety.Version";
  public static final String TEXT_CONFIGURATION = "cellsociety.Text";
  public static final String DATA_FILE_FOLDER = System.getProperty("user.dir") + "/data";
  public static final String DATA_FILE_EXTENSION = "*.xml";
  private final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  public static final String FIRE = "fire";
  public static final String GAME_OF_LIFE = "gameOfLife";
  public static final String PERCOLATION = "percolation";
  public static final String SCHELLING = "Schelling";
  public static final String WATOR = "wator";

  public Controller() {
    stage = new Stage();

    ResourceBundle Text = ResourceBundle.getBundle(TEXT_CONFIGURATION);
    showMessage(AlertType.INFORMATION, String.format(Text.getString("uploadFile")));

    File dataFile = chooseFile();
    xmlParser = new XMLParser();
    parseFile(dataFile.getPath());

    setSimulation(); //loads view and model
  }

  private void parseFile(String filePath) {
    xmlParser.readXML(filePath);
  }

  private File chooseFile() {
    File dataFile = FILE_CHOOSER.showOpenDialog(stage);
    return dataFile;
  }

  private void setSimulation() {
    loadSimulationScene(xmlParser.getType(), xmlParser.getWidth(), xmlParser.getHeight());

    String neighborhoodTypeString = xmlParser.getNeighborhoodType();
    Neighborhood neighborhoodType = getNeighborhoodObject(neighborhoodTypeString);
    loadSimulationModel(xmlParser.getWidth(), xmlParser.getHeight(), neighborhoodType, xmlParser.getStates(), xmlParser.getType());
  }

  private Neighborhood getNeighborhoodObject(String neighborhoodTypeString) {
    return switch (neighborhoodTypeString){
      case "adjacent" -> new AdjacentNeighborhood();
      case "cardinal" -> new CardinalNeighborhood();
      default -> throw new IllegalStateException("Unexpected value: " + neighborhoodTypeString);
    };
  }

  private void loadSimulationModel(int numRows, int numCols, Neighborhood neighborhoodType,
      List<Integer> stateList, String simulationType) {
      simulationModel = switch (simulationType) {
      case GAME_OF_LIFE -> new GameOfLifeSimulation(numRows, numCols, neighborhoodType, stateList);
      case PERCOLATION -> new PercolationSimulation(numRows, numCols, neighborhoodType, stateList);
      case FIRE -> new FireSimulation(numRows, numCols, neighborhoodType, stateList);
      case SCHELLING -> new SchellingSimulation(numRows, numCols, neighborhoodType, stateList);
      case WATOR -> new WatorSimulation(numRows, numCols, neighborhoodType, stateList);
      default -> null;
    };
  }

  private void loadSimulationScene(String simulationName, int numRows, int numCols) {
    simulationPage = new SimulationPage(simulationName, numRows, numCols, 10, 10);
    stage.setScene(simulationPage.getSimulationScene());
    stage.show();
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
