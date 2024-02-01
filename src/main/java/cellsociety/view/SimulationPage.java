package cellsociety.view;


import cellsociety.model.core.Cell;
import cellsociety.model.core.Grid;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Iterator;
import java.util.ResourceBundle;


public class SimulationPage {

    private GridPane grid;
    private Scene scene;
    private Group root;

    Button backButton;
    Button newSimulationButton;
    public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";

//Iterator<Cell> gridModelIterator
    public SimulationPage(String simulationName, int numRows, int numCols, int cellWidth, int cellHeight) {
//        buttonLables = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "buttonLables");
        root = new Group();
        grid = new GridPane();

        CellView[][] board = new CellView[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j ++){
                board[i][j] = new CellView(j*cellWidth, i*cellHeight);
                grid.add(board[i][j].getCellGraphic(), j, i);
            }
        }

        scene = new Scene(root,700,700);
        root.getChildren().add(grid);

//        backButton = new Button(buttonLables.getString("BackButton"));
//        newSimulationButton = new Button(buttonLables.getString("NewSimulation"));
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


}
