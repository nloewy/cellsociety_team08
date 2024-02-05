package cellsociety.view;

import cellsociety.Point;
import cellsociety.model.core.Cell;
import cellsociety.view.CellView.CellView;
import cellsociety.view.CellView.FireCellView;
import cellsociety.view.CellView.GameOfLifeCellView;
import cellsociety.view.CellView.PercolationCellView;
import cellsociety.view.CellView.SchellingCellView;
import cellsociety.view.CellView.WatorCellView;
import java.util.Map;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class controls the simulation's view component, controls the simulation grid view, and
 * starting/pausing/saving/changing speed/switching of simulation
 *
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
  private Button resetSimulationButton;
  private Text simulationTitleDisplay;
  private Slider speedSlider;
  private Label speedLabel;
  private ResourceBundle buttonLabels;


  public static final double SCENE_HEIGHT = 800;
  public static final double SCENE_WIDTH = 1000;
  public static final double GRID_HEIGHT = .6 * SCENE_HEIGHT;
  public static final double GRID_WIDTH = .6 * SCENE_WIDTH;
  public static final double GRID_START_Y = .2 * SCENE_HEIGHT;
  public static final double GRID_START_X = .05 * SCENE_WIDTH;
  public static final int BUTTON_START_X = (int) (SCENE_WIDTH - (
      (SCENE_WIDTH - GRID_START_X - GRID_WIDTH) / 2.0));
  public static final int TITLE_X = (int) Math.round(.5 * SCENE_HEIGHT);
  public static final int TITLE_Y = (int) Math.round(GRID_START_Y * .5);
  public static final int NEW_SIMULATION_BUTTON_Y = (int) Math.round(.325 * SCENE_HEIGHT);
  public static final int INFO_BUTTON_Y = (int) Math.round(.375 * SCENE_HEIGHT);
  public static final int START_BUTTON_Y = (int) Math.round(.425 * SCENE_HEIGHT);
  public static final int SAVE_BUTTON_Y = (int) Math.round(.475 * SCENE_HEIGHT);
  public static final int PAUSE_BUTTON_Y = (int) Math.round(.525 * SCENE_HEIGHT);
  public static final int RESET_BUTTON_Y = (int) Math.round(.575 * SCENE_HEIGHT);
  public static final int SPEED_LABEL_Y = (int) Math.round(.625 * SCENE_HEIGHT);
  public static final int SPEED_SLIDER_Y = (int) Math.round(.675 * SCENE_HEIGHT);
  public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  public static final String STYLESHEET = "StyleSheet.css";

  public static final String NEW_SIMULATION_BUTTON_KEY = "NewSimulationButton";
  public static final String RESET_BUTTON_KEY = "ResetButton";
  public static final String START_BUTTON_KEY = "StartButton";
  public static final String PAUSE_BUTTON_KEY = "PauseButton";
  public static final String ABOUT_BUTTON_KEY = "SimulationInfoButton";
  public static final String SAVE_BUTTON_KEY = "SaveSimulationButton";


  public SimulationPage(String simulationType, String simulationName, int numRows, int numCols,
      Map<String, EventHandler<ActionEvent>> eventHandlers,
      Iterator<Cell> gridIterator) {

    root = new Group();
    grid = new GridPane();
    scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

    buttonLabels = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "buttonLabels");
    scene.getStylesheets()
        .add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    System.out.println("creating new simulation: " + simulationName);

    board = new CellView[numRows][numCols];
    System.out.println("numcols:" + numCols + " numrows:" + numRows);
    int index = 0;

    while (gridIterator.hasNext()) {
      Cell c = gridIterator.next();
      Point location = c.getLocation();
      int state = c.getCurrentState();
      int col = location.getY();
      int row = location.getX();
      System.out.println(row + "," + col);
      System.out.println(index);
//            System.out.println("("+row+","+col+")"+" state " + state + " @index " + index);
      board[row][col] = initializeCellView(simulationType, state, GRID_WIDTH / numCols,
          GRID_HEIGHT / numRows);

      grid.add(board[row][col].getCellGraphic(), col, row);
      index++;
    }

    grid.setLayoutY(GRID_START_Y);
    grid.setLayoutX(GRID_START_X);

    newSimulationButton = makeButton(buttonLabels.getString(NEW_SIMULATION_BUTTON_KEY),
        eventHandlers.get("newSimulationHandler"), BUTTON_START_X,
        NEW_SIMULATION_BUTTON_Y);
    simulationInfoButton = makeButton(buttonLabels.getString(ABOUT_BUTTON_KEY),
        eventHandlers.get("infoButtonHandler"), BUTTON_START_X, INFO_BUTTON_Y);
    startSimulationButton = makeButton(buttonLabels.getString(START_BUTTON_KEY),
        eventHandlers.get("startSimulationHandler"), BUTTON_START_X,
        START_BUTTON_Y);
    saveSimulationButton = makeButton(buttonLabels.getString(SAVE_BUTTON_KEY),
        eventHandlers.get("saveSimulationHandler"), BUTTON_START_X,
        SAVE_BUTTON_Y);
    pauseSimulationButton = makeButton(buttonLabels.getString(PAUSE_BUTTON_KEY),
        eventHandlers.get("pauseSimulationHandler"), BUTTON_START_X,
        PAUSE_BUTTON_Y);
    resetSimulationButton = makeButton(buttonLabels.getString(RESET_BUTTON_KEY),
        eventHandlers.get("resetSimulationHandler"), BUTTON_START_X,
        RESET_BUTTON_Y);

    speedSlider = new Slider(1, 10,
        1); //min speed = 1; max speed = 10; default speed when loaded = 5;
    speedSlider.setLayoutX(BUTTON_START_X);
    speedSlider.setLayoutY(SPEED_SLIDER_Y);

    speedLabel = new Label("Speed: " + (int) speedSlider.getValue());
    speedLabel.setLayoutX(BUTTON_START_X);
    speedLabel.setLayoutY(SPEED_LABEL_Y);

    simulationTitleDisplay = new Text(simulationName);
    simulationTitleDisplay.setX(TITLE_X - simulationTitleDisplay.getLayoutBounds().getWidth()/2);
    System.out.println("Scenewidth" + SCENE_WIDTH);
    System.out.println("text width" + simulationTitleDisplay.getLayoutBounds().getWidth());
    simulationTitleDisplay.setY(TITLE_Y);
    simulationTitleDisplay.setFont(new Font(40));
//    simulationTitleDisplay.setId("simulation-title");
//    simulationTitleDisplay.getStyleClass().add("simulation-title");

    root.getChildren().addAll(
        grid,
        newSimulationButton,
        simulationInfoButton,
        startSimulationButton,
        saveSimulationButton,
        pauseSimulationButton,
        resetSimulationButton,
        simulationTitleDisplay,
        speedSlider,
        speedLabel
    );
  }

  private CellView initializeCellView(String simulationType, int state, double width,
      double height) {
    return switch (simulationType) {
      case Controller.FIRE -> new FireCellView(state, width, height);
      case Controller.GAME_OF_LIFE -> new GameOfLifeCellView(state, width, height);
      case Controller.PERCOLATION -> new PercolationCellView(state, width, height);
      case Controller.SCHELLING -> new SchellingCellView(state, width, height);
      case Controller.WATOR -> new WatorCellView(state, width, height);
      default -> throw new IllegalStateException("Unexpected value: " + simulationType);
    };
  }

  public void setSpeedSliderHandler(ChangeListener<Number> speedSliderHandler) {
    speedSlider.valueProperty().addListener(speedSliderHandler);
  }

  public void updateSpeedLabel(int speed) {
    speedLabel.setText("Speed: " + speed);
  }

  private Button makeButton(String buttonText, EventHandler<ActionEvent> handler, int xPos,
      int yPos) {
    Button ret = new Button(buttonText);
    ret.setOnAction(handler);
    ret.setLayoutX(xPos);
    ret.setLayoutY(yPos);
    return ret;
  }

  public GridPane getGrid() {
    return grid;
  }

  public Scene getSimulationScene() {
    return scene;
  }


  public void updateView(Iterator<Cell> gridIterator) {
    while (gridIterator.hasNext()) {
      Cell c = gridIterator.next();
      Point location = c.getLocation();
      int state = c.getCurrentState();
      int col = location.getY();
      int row = location.getX();
      board[row][col].updateState(state);
    }
  }
}
