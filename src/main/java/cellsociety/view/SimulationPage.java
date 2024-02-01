package cellsociety.view;


import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.scene.text.Text;


public class SimulationPage {

    private GridPane grid;
    private Scene scene;
    private Group root;
    private CellView[][] board;
    private Button newSimulationButton;
    private Button simulationInfoButton;
    private Text simulationTitleDisplay;

    public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";
    public static final int GRID_X_OFFSET = 100;
    public static final int GRID_Y_OFFSET = 100;

    public SimulationPage(String simulationName, int numRows, int numCols, int cellWidth, int cellHeight, EventHandler<ActionEvent> newSimulationHandler, EventHandler<ActionEvent> infoButtonHandler) {
//        buttonLables = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "buttonLables");
        root = new Group();
        grid = new GridPane();
        scene = new Scene(root,700,700);

        board = new CellView[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j ++){
                board[i][j] = new CellView(i, j);
                grid.add(board[i][j].getCellGraphic(), i, j);
            }
        }

        newSimulationButton = new Button("NewSimulation");
        newSimulationButton.setOnAction(newSimulationHandler);
        newSimulationButton.setLayoutY(100);

        simulationInfoButton = new Button("About");
        simulationInfoButton.setOnAction(infoButtonHandler);

        simulationTitleDisplay = new Text(simulationName);

        root.getChildren().addAll(grid, newSimulationButton, simulationInfoButton, simulationTitleDisplay);
    }

    public GridPane getGrid() {
        return grid;
    }

    public Scene getSimulationScene(){
        return scene;
    }
//    public Iterator<Cell> getIterator(){
//        Iterator<T> iterator = gridModel.iterator();
//        while (iterator.hasNext()) {
//            T cell = iterator.next();
//            cell.updateStates();
//        }
//    }

    public void updateView(Iterator gridIterator){
        if (gridIterator.hasNext()){
            gridIterator.next();
        }
    }


}
