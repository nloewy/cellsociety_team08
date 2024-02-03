package cellsociety.view;

import cellsociety.Point;
import cellsociety.model.core.Cell;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
    private Slider speedSlider;
    private Label speedLabel;
    private int numRows;
    private int numCols;

    public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";
    public static final int GRID_X_OFFSET = 100;
    public static final int GRID_Y_OFFSET = 100;

    public SimulationPage(String simulationName, int numRows, int numCols,
        EventHandler<ActionEvent> newSimulationHandler, EventHandler<ActionEvent> infoButtonHandler,
        EventHandler<ActionEvent> startSimulationHandler,
        EventHandler<ActionEvent> saveSimulationHandler,
        EventHandler<ActionEvent> pauseSimulationHandler, Iterator<Cell> gridIterator) {
//        buttonLables = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "buttonLables");
        root = new Group();
        grid = new GridPane();
        scene = new Scene(root, 500, 500);
        System.out.println("creating new simulation: " + simulationName);
        this.numRows = numRows;
        this.numCols = numCols;

        board = new CellView[numRows][numCols];
        System.out.println("numcols:"+numCols+" numrows:"+numRows);
        int index=0;

        while (gridIterator.hasNext()) {
            Cell c = gridIterator.next();
            Point location = c.getLocation();
            int state = c.getCurrentState();
            int col = location.getY();
            int row = location.getX();
            System.out.println(row+","+col);
            System.out.println(index);
//            System.out.println("("+row+","+col+")"+" state " + state + " @index " + index);
            board[row][col] = new CellView(state);
            grid.add(board[row][col].getCellGraphic(), col, row);
            index++;
        }

        grid.setLayoutY(150);

        newSimulationButton = makeButton("New Simulation", newSimulationHandler, 0, 100);
        simulationInfoButton = makeButton("About", infoButtonHandler, 300, 300);
        startSimulationButton = makeButton("Start", startSimulationHandler, 300, 250);
        saveSimulationButton = makeButton("Save Simulation", saveSimulationHandler, 300, 200);
        pauseSimulationButton = makeButton("Pause", pauseSimulationHandler, 300, 150);

        simulationTitleDisplay = new Text(simulationName);
        simulationTitleDisplay.setY(50);
        simulationTitleDisplay.setX(100);

        speedSlider = new Slider(1,10,5); //min speed = 1; max speed = 10; default speed when loaded = 5;
        speedSlider.setLayoutY(400);
        speedSlider.setLayoutX(300);

        speedLabel = new Label("Speed: " + (int) speedSlider.getValue());
        speedLabel.setLayoutX(400);
        speedLabel.setLayoutY(370);

        root.getChildren().addAll(
            grid,
            newSimulationButton,
            simulationInfoButton,
            startSimulationButton,
            saveSimulationButton,
            pauseSimulationButton,
            simulationTitleDisplay,
            speedSlider,
            speedLabel
        );
    }

    public void setSpeedSliderHandler(ChangeListener<Number> speedSliderHandler) {
        speedSlider.valueProperty().addListener(speedSliderHandler);
    }

    public void updateSpeedLabel(int speed){
        speedLabel.setText("Speed: " + speed);
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
        int index=0;
        while (gridIterator.hasNext()) {
            Cell c = gridIterator.next();
            Point location = c.getLocation();
            int state = c.getCurrentState();
            int col = location.getY();
            int row = location.getX();
            board[row][col].updateState(state);
            index++;
        }
    }
}
