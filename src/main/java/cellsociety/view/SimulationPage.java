package cellsociety.view;

import cellsociety.Point;
import cellsociety.model.core.Cell;
import cellsociety.view.CellView.CellView;
import cellsociety.view.CellView.FireCellView;
import cellsociety.view.CellView.GameOfLifeCellView;
import cellsociety.view.CellView.PercolationCellView;
import cellsociety.view.CellView.SchellingCellView;
import cellsociety.view.CellView.WatorCellView;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class controls the simulation's view component, controls the simulation grid view, and
 * starting/pausing/saving/changing speed/switching, etc, of simulation
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
  private ResourceBundle configProperties;

//  public static final double SCENE_HEIGHT = 800;
//  public static final double SCENE_WIDTH = 1000;
//  public static final double GRID_HEIGHT = .6 * SCENE_HEIGHT;
//  public static final double GRID_WIDTH = .6 * SCENE_WIDTH;
//  public static final double GRID_START_Y = .2 * SCENE_HEIGHT;
//  public static final double GRID_START_X = .05 * SCENE_WIDTH;
//  public static final int BUTTON_START_X = (int) (SCENE_WIDTH - (
//      (SCENE_WIDTH - GRID_START_X - GRID_WIDTH) / 2.0));
//  public static final int TITLE_X = (int) Math.round(.5 * SCENE_HEIGHT);
//  public static final int TITLE_Y = (int) Math.round(GRID_START_Y * .5);
//  public static final int NEW_SIMULATION_BUTTON_Y = (int) Math.round(.325 * SCENE_HEIGHT);
//  public static final int INFO_BUTTON_Y = (int) Math.round(.375 * SCENE_HEIGHT);
//  public static final int START_BUTTON_Y = (int) Math.round(.425 * SCENE_HEIGHT);
//  public static final int SAVE_BUTTON_Y = (int) Math.round(.475 * SCENE_HEIGHT);
//  public static final int PAUSE_BUTTON_Y = (int) Math.round(.525 * SCENE_HEIGHT);
//  public static final int RESET_BUTTON_Y = (int) Math.round(.575 * SCENE_HEIGHT);
//  public static final int SPEED_LABEL_Y = (int) Math.round(.625 * SCENE_HEIGHT);
//  public static final int SPEED_SLIDER_Y = (int) Math.round(.675 * SCENE_HEIGHT);

  //paths (will stay as constants)
  public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  public static final String STYLESHEET = "StyleSheet.css";

  //button label keys (will stay as constants)
  public static final String NEW_SIMULATION_BUTTON_KEY = "NewSimulationButton";
  public static final String RESET_BUTTON_KEY = "ResetButton";
  public static final String START_BUTTON_KEY = "StartButton";
  public static final String PAUSE_BUTTON_KEY = "PauseButton";
  public static final String ABOUT_BUTTON_KEY = "SimulationInfoButton";
  public static final String SAVE_BUTTON_KEY = "SaveSimulationButton";

  /**
   * Constructs the view component of the simulation
   * @param simulationType a string that specifies the simulation type
   * @param simulationName a string that specifies the name of the simulation
   * @param numRows the integer number of rows in the grid
   * @param numCols the integer number of columns in the grid
   * @param eventHandlers the map of event handlers for buttons
   * @param gridIterator and iterator of the grid model for Cell model objects
   */
  public SimulationPage(String simulationType, String simulationName, int numRows, int numCols,
      Map<String, EventHandler<ActionEvent>> eventHandlers,
      Iterator<Cell> gridIterator) {

    configProperties = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "config");

    root = new Group();
    grid = new GridPane();
//    scene = new Scene(root, Double.parseDouble(configProperties.getString("SCENE_WIDTH"), Double.parseDouble(configProperties.getString("SCENE_HEIGHT"))));
    scene = new Scene(root, Double.parseDouble(configProperties.getString("SCENE_WIDTH")),
        Double.parseDouble(configProperties.getString("SCENE_HEIGHT")));

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
      board[row][col] = initializeCellView(simulationType, state, Double.parseDouble(configProperties.getString("GRID_WIDTH")) / numCols,
          Double.parseDouble(configProperties.getString("GRID_HEIGHT")) / numRows);

      grid.add(board[row][col].getCellGraphic(), col, row);
      index++;
    }

    grid.setLayoutY(Double.parseDouble(configProperties.getString("GRID_START_Y")));
    grid.setLayoutX(Double.parseDouble(configProperties.getString("GRID_START_X")));

    initializeButtons(eventHandlers);
    initializeSlider();

    simulationTitleDisplay = new Text(simulationName);
<<<<<<< HEAD
    simulationTitleDisplay.setX(TITLE_X - simulationTitleDisplay.getLayoutBounds().getWidth() / 2);
    System.out.println("Scenewidth" + SCENE_WIDTH);
    System.out.println("text width" + simulationTitleDisplay.getLayoutBounds().getWidth());
    simulationTitleDisplay.setY(TITLE_Y);
=======
//    simulationTitleDisplay.setX((Integer.parseInt(configProperties.getString("SCENE_WIDTH")) - simulationTitleDisplay.getLayoutBounds().getWidth())/2);
//    simulationTitleDisplay.setY((Integer.parseInt(configProperties.getString("SCENE_HEIGHT")) - (Integer.parseInt(configProperties.getString("GRID_START_Y")) - simulationTitleDisplay.getLayoutBounds().getHeight())/2));
    simulationTitleDisplay.setX(Integer.parseInt(configProperties.getString("TITLE_X_OFFSET")));
    simulationTitleDisplay.setY(Integer.parseInt(configProperties.getString("TITLE_Y_OFFSET")));
>>>>>>> 7dac26f513b6b7b867bb726037d0b24f2f79d394
    simulationTitleDisplay.setFont(new Font(40));

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

  /**
   * set up the speed slider and the speed label
   */
  private void initializeSlider() {
    speedSlider = new Slider((Integer.parseInt(configProperties.getString("SLIDER_MIN"))), (Integer.parseInt(configProperties.getString("SLIDER_MAX"))), (Integer.parseInt(configProperties.getString("SLIDER_DEFAULT"))));
    speedSlider.setLayoutX(Integer.parseInt(configProperties.getString("BUTTON_START_X")));
    speedSlider.setLayoutY(Integer.parseInt(configProperties.getString("SPEED_SLIDER_Y")));

    speedLabel = new Label("Speed: " + (int) speedSlider.getValue());
    speedLabel.setLayoutX(Integer.parseInt(configProperties.getString("BUTTON_START_X")));
    speedLabel.setLayoutY(Integer.parseInt(configProperties.getString("SPEED_LABEL_Y")));
  }

  /**
   * initialize the buttons
   * @param eventHandlers a map of event handlers for the buttons
   */
  private void initializeButtons(Map<String, EventHandler<ActionEvent>> eventHandlers) {
    newSimulationButton = makeButton(buttonLabels.getString(NEW_SIMULATION_BUTTON_KEY),
        eventHandlers.get("newSimulationHandler"), Integer.parseInt(configProperties.getString("BUTTON_START_X")),
        Integer.parseInt(configProperties.getString("NEW_SIMULATION_BUTTON_Y")));
    simulationInfoButton = makeButton(buttonLabels.getString(ABOUT_BUTTON_KEY),
        eventHandlers.get("infoButtonHandler"), Integer.parseInt(configProperties.getString("BUTTON_START_X")), Integer.parseInt(configProperties.getString("INFO_BUTTON_Y")));
    startSimulationButton = makeButton(buttonLabels.getString(START_BUTTON_KEY),
        eventHandlers.get("startSimulationHandler"), Integer.parseInt(configProperties.getString("BUTTON_START_X")),
        Integer.parseInt(configProperties.getString("START_BUTTON_Y")));
    saveSimulationButton = makeButton(buttonLabels.getString(SAVE_BUTTON_KEY),
        eventHandlers.get("saveSimulationHandler"), Integer.parseInt(configProperties.getString("BUTTON_START_X")),
        Integer.parseInt(configProperties.getString("SAVE_BUTTON_Y")));
    pauseSimulationButton = makeButton(buttonLabels.getString(PAUSE_BUTTON_KEY),
        eventHandlers.get("pauseSimulationHandler"), Integer.parseInt(configProperties.getString("BUTTON_START_X")),
        Integer.parseInt(configProperties.getString("PAUSE_BUTTON_Y")));
    resetSimulationButton = makeButton(buttonLabels.getString(RESET_BUTTON_KEY),
        eventHandlers.get("resetSimulationHandler"), Integer.parseInt(configProperties.getString("BUTTON_START_X")),
        Integer.parseInt(configProperties.getString("RESET_BUTTON_Y")));
  }

  /**
   * initialize the cellview objects in the grid according to the current simulation
   * @param simulationType a string that specifies the simulation type
   * @param state an integer that specifies the state the cell is in
   * @param width a double that specifies the width of a cell
   * @param height a double that specifies the height of a cell
   * @return returns a CellView object
   */
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

  /**
   * adds an event listener for the speed slider
   * @param speedSliderHandler listens to the change in number of the slider
   */
  public void setSpeedSliderHandler(ChangeListener<Number> speedSliderHandler) {
    speedSlider.valueProperty().addListener(speedSliderHandler);
  }

  /**
   * updates the speed label of the speed slider
   * @param speed an integer that specifies the current speed on the slider
   */
  public void updateSpeedLabel(int speed) {
    speedLabel.setText("Speed: " + speed);
  }

  /**
   * makes a button
   * @param buttonText a string of the text on the button
   * @param handler an event handler that gets hooked on the button
   * @param xPos an integer of the x position of the button
   * @param yPos an integer of the y position of the button
   * @return returns the button object
   */
  private Button makeButton(String buttonText, EventHandler<ActionEvent> handler, int xPos,
      int yPos) {
    Button ret = new Button(buttonText);
    ret.setOnAction(handler);
    ret.setLayoutX(xPos);
    ret.setLayoutY(yPos);
    return ret;
  }

  /**
   * gets the view component of the grid
   * @return returns the grid pane
   */
  public GridPane getGrid() {
    return grid;
  }

  /**
   * gets the scene of the view component of the simulation
   * @return returns the scene object
   */
  public Scene getSimulationScene() {
    return scene;
  }

  /**
   * updates the view component of the grid and the simulation
   * @param gridIterator the cell model iterator of the grid model
   */
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
