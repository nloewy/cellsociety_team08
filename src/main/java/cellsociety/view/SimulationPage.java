package cellsociety.view;


import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.scene.text.Text;
import javax.swing.Action;


public class SimulationPage {

    private GridPane grid;
    private Scene scene;
    private Group root;
    private CellView[][] board;
    private Button newSimulationButton;
    private Button simulationInfoButton;
    private Button startSimulationButton;
    private Text simulationTitleDisplay;
    private int numRows;
    private int numCols;

    public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";
    public static final int GRID_X_OFFSET = 100;
    public static final int GRID_Y_OFFSET = 100;

    public SimulationPage(String simulationName, int numRows, int numCols, EventHandler<ActionEvent> newSimulationHandler, EventHandler<ActionEvent> infoButtonHandler, EventHandler<ActionEvent> startSimulationHandler, Iterator<Cell> gridIterator) {
//        buttonLables = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "buttonLables");
        root = new Group();
        grid = new GridPane();
        scene = new Scene(root,700,700);

        this.numRows = numRows;
        this.numCols = numCols;

        board = new CellView[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j ++){
                board[i][j] = new CellView(i, j, gridIterator.next().getCurrentState()); //TODO: read state from grid iterator;
                grid.add(board[i][j].getCellGraphic(), i, j);
            }
        }

        grid.setLayoutY(150);

        newSimulationButton = makeButton("New Simulation", newSimulationHandler, 0, 100);
//        newSimulationButton = new Button("NewSimulation");
//        newSimulationButton.setOnAction(newSimulationHandler);
//        newSimulationButton.setLayoutY(100);

        simulationInfoButton = makeButton("About", infoButtonHandler, 300, 280);
//        simulationInfoButton = new Button("About");
//        simulationInfoButton.setOnAction(infoButtonHandler);
//        simulationInfoButton.setLayoutX(200);

        startSimulationButton = makeButton("Start", startSimulationHandler, 300, 300);

        simulationTitleDisplay = new Text(simulationName);

        root.getChildren().addAll(grid, newSimulationButton, simulationInfoButton, startSimulationButton, simulationTitleDisplay);
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
                board[i][j].updateState(gridIterator.next().getCurrentState());
            }
        }
    }
}
