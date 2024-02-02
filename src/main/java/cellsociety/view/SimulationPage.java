package cellsociety.view;

import cellsociety.model.core.Cell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

/**
 * This class controls the simulation's view component, controls the simulation grid view, and starting/pausing/saving/changing speed/switching of simulation
 * @author Alisha Zhang
 */

public class SimulationPage {

    private GridPane grid;
    private Scene scene;
    private Group root;
    private CellView[][] board;
    private Button newSimulationButton;
    private Button simulationInfoButton;
    private Button startSimulationButton;
    private Button pauseSimulationButton;
    private Button saveSimulationButton;
    private Text simulationTitleDisplay;
    private int numRows;
    private int numCols;

    public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";
    public static final int GRID_X_OFFSET = 100;
    public static final int GRID_Y_OFFSET = 100;

    public SimulationPage(String simulationName, int numRows, int numCols, EventHandler<ActionEvent> newSimulationHandler, EventHandler<ActionEvent> infoButtonHandler, EventHandler<ActionEvent> startSimulationHandler, EventHandler<ActionEvent> saveSimulationHandler, EventHandler<ActionEvent> pauseSimulationHandler, Iterator<Cell> gridIterator) {
//        buttonLables = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "buttonLables");
        root = new Group();
        grid = new GridPane();
        scene = new Scene(root,500,500);
        System.out.println("creating new simulation: " + simulationName);
        this.numRows = numRows;
        this.numCols = numCols;

        board = new CellView[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j ++){
                board[j][i] = new CellView(j, i, gridIterator.next().getCurrentState()); //TODO: read state from grid iterator;
                grid.add(board[j][i].getCellGraphic(), j, i);
            }
        }

        grid.setLayoutY(150);

        newSimulationButton = makeButton("New Simulation", newSimulationHandler, 0, 100);
        simulationInfoButton = makeButton("About", infoButtonHandler, 300, 300);
        startSimulationButton = makeButton("Start", startSimulationHandler, 300, 250);
        saveSimulationButton = makeButton("Save Simulation", saveSimulationHandler, 300, 200);
        pauseSimulationButton = makeButton("Pause", pauseSimulationHandler, 300, 150);

        simulationTitleDisplay = new Text(simulationName);

        root.getChildren().addAll(
            grid,
            newSimulationButton,
            simulationInfoButton,
            startSimulationButton,
            saveSimulationButton,
            pauseSimulationButton,
            simulationTitleDisplay
        );
    }

    private Button makeButton(String buttonText, EventHandler<ActionEvent> handler, int xPos, int yPos){
        Button ret = new Button(buttonText);
        ret.setOnAction(handler);
        ret.setLayoutX(xPos);
        ret.setLayoutY(yPos);
        return ret;
    }

    public GridPane getGrid() {
        return grid;
    }

    public Scene getSimulationScene(){
        return scene;
    }


    public void updateView(Iterator<Cell> gridIterator){
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j ++){
                board[j][i].updateState(gridIterator.next().getCurrentState());
            }
        }
    }
}
