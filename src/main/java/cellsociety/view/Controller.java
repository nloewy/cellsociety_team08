package cellsociety.view;

import cellsociety.model.neighborhood.AdjacentNeighborhood;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.*;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {
    private Stage stage;
    private SceneManager sceneManager;
    private SimulationPage simulationPage;


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

    public Controller(){
        stage = new Stage();

        ResourceBundle Text = ResourceBundle.getBundle(TEXT_CONFIGURATION);
        showMessage(Alert.AlertType.INFORMATION, String.format(Text.getString("uploadFile")));
        chooseFile();
    }

    private void chooseFile() {
        File dataFile = FILE_CHOOSER.showOpenDialog(stage);
//        XMLParser xmlParser = new XMLParser(); //pass in data file
        setSimulation();
    }

    private void setSimulation() {
        loadSimulationScene("Fire", 10, 10); //get name from xmlpaser

        Neighborhood test = new AdjacentNeighborhood();
        List<Integer> testList = new ArrayList<>();
//        loadSimulationModel(10, 10, test, testList, "fire");
    }

    private Simulation loadSimulationModel(int numRows, int numCols, Neighborhood neighborhoodType,
                                             List<Integer> stateList, String simulationType) {
        Simulation simulation = switch (simulationType) {
            case GAME_OF_LIFE -> new GameOfLifeSimulation(numRows, numCols, neighborhoodType, stateList);
            case PERCOLATION -> new PercolationSimulation(numRows, numCols, neighborhoodType, stateList);
            case FIRE -> new FireSimulation(numRows, numCols, neighborhoodType, stateList);
            case SCHELLING -> new SchellingSimulation(numRows, numCols, neighborhoodType, stateList);
            case WATOR -> new WatorSimulation(numRows, numCols, neighborhoodType, stateList);
            default -> null;
        };
        return simulation;
    }

    private void loadSimulationScene(String simulationName, int numRows, int numCols) {
        simulationPage = new SimulationPage(simulationName, numRows, numCols, 10,10);
        stage.setScene(simulationPage.getSimulationScene());
        stage.show();
    }


    public void showMessage (Alert.AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }

    public double getVersion () {
        ResourceBundle resources = ResourceBundle.getBundle(INTERNAL_CONFIGURATION);
        return Double.parseDouble(resources.getString("Version"));
    }

    private static FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        result.setInitialDirectory(new File(DATA_FILE_FOLDER));
        result.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Data Files", extensionAccepted));
        return result;
    }
}
