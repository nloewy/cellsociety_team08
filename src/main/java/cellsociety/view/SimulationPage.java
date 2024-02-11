package cellsociety.view;

import cellsociety.Point;
import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.cell.SugarCell;
import cellsociety.view.cellview.CellView;
import cellsociety.view.cellview.FireCellView;
import cellsociety.view.cellview.GameOfLifeCellView;
import cellsociety.view.cellview.PercolationCellView;
import cellsociety.view.cellview.SchellingCellView;
import cellsociety.view.cellview.SugarCellView;
import cellsociety.view.cellview.WatorCellView;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class controls the simulation's view component, controls the simulation grid view, and
 * starting/pausing/saving/changing speed/switching, etc, of simulation
 *
 * @author Alisha Zhang
 */

public class SimulationPage {

  private Scene scene;
  private Group root;
  private CellView[][] board;
  private Button newSimulationButton;
  private Button simulationInfoButton;
  private Button startSimulationButton;
  private Button pauseSimulationButton;
  private Button saveSimulationButton;
  private Button resetSimulationButton;
  private Button simulationGraphButton;
  private Text simulationTitleDisplay;
  private Slider speedSlider;
  private Label speedLabel;
  private ResourceBundle buttonLabels;
  private ResourceBundle configProperties;
  private ResourceBundle textProperties;
  private SimulationGraph graph;
  private Map<Integer, Integer> stateCount;
  private int totalCellCount;

  //config file number keys
  public static final String SCENE_HEIGHT_KEY = "SCENE_HEIGHT";
  public static final String SCENE_WIDTH_KEY = "SCENE_WIDTH";
  public static final String GRID_HEIGHT_KEY = "GRID_HEIGHT";
  public static final String GRID_WIDTH_KEY = "GRID_WIDTH";
  public static final String GRID_START_Y_KEY = "GRID_START_Y";
  public static final String GRID_START_X_KEY = "GRID_START_X";
  public static final String BUTTON_START_X_KEY = "BUTTON_START_X";
  public static final String TITLE_X_OFFSET_KEY = "TITLE_X_OFFSET";
  public static final String TITLE_Y_OFFSET_KEY = "TITLE_Y_OFFSET";
  public static final String NEW_SIMULATION_BUTTON_Y_KEY = "NEW_SIMULATION_BUTTON_Y";
  public static final String INFO_BUTTON_Y_KEY = "INFO_BUTTON_Y";
  public static final String START_BUTTON_Y_KEY = "START_BUTTON_Y";
  public static final String SAVE_BUTTON_Y_KEY = "SAVE_BUTTON_Y";
  public static final String PAUSE_BUTTON_Y_KEY = "PAUSE_BUTTON_Y";
  public static final String RESET_BUTTON_Y_KEY = "RESET_BUTTON_Y";
  public static final String SPEED_LABEL_Y_KEY = "SPEED_LABEL_Y";
  public static final String SPEED_SLIDER_Y_KEY = "SPEED_SLIDER_Y";
  public static final String SPEED_SLIDER_MIN_KEY = "SLIDER_MIN";
  public static final String SPEED_SLIDER_MAX_KEY = "SLIDER_MAX";
  public static final String SLIDER_DEFAULT_KEY = "SLIDER_DEFAULT";

  //paths
  public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  public static final String STYLESHEET = "StyleSheet.css";

  //button label keys
  public static final String NEW_SIMULATION_BUTTON_KEY = "NewSimulationButton";
  public static final String RESET_BUTTON_KEY = "ResetButton";
  public static final String START_BUTTON_KEY = "StartButton";
  public static final String PAUSE_BUTTON_KEY = "PauseButton";
  public static final String ABOUT_BUTTON_KEY = "SimulationInfoButton";
  public static final String SAVE_BUTTON_KEY = "SaveSimulationButton";
  public static final String SPEED_LABEL_TEXT_KEY = "speedLabel";

  private Map<String, Double> gridProperties;

  /**
   * Constructs the view component of the simulation
   *
   * @param simulationType a string that specifies the simulation type
   * @param simulationName a string that specifies the name of the simulation
   * @param numRows        the integer number of rows in the grid
   * @param numCols        the integer number of columns in the grid
   * @param eventHandlers  the map of event handlers for buttons
   * @param gridIterator   and iterator of the grid model for Cell model objects
   */
  public SimulationPage(String simulationType, String simulationName, int numRows,
      int numCols,
      Map<String, EventHandler<ActionEvent>> eventHandlers,
      Iterator<Cell> gridIterator, List<List<Point>> allVertices) {


    textProperties = ResourceBundle.getBundle(Controller.TEXT_CONFIGURATION);
    configProperties = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "config");


    gridProperties = new HashMap<>();
    gridProperties.put("gridStartX", configDouble(GRID_START_X_KEY));
    gridProperties.put("gridStartY", configDouble(GRID_START_Y_KEY));

    gridProperties.put("gridWidth", configDouble(GRID_WIDTH_KEY));
    gridProperties.put("gridHeight", configDouble(GRID_HEIGHT_KEY));

    stateCount = new HashMap<>();
    graph = new SimulationGraph(stateCount);

    root = new Group();
    scene = new Scene(root, configDouble(SCENE_WIDTH_KEY),
        configDouble(SCENE_HEIGHT_KEY));

    buttonLabels = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "buttonLabelsEnglish");
    scene.getStylesheets()
        .add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    board = new CellView[numRows][numCols];
    int ind = 0;
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        double width = gridProperties.get("gridWidth") / numCols;
        double height = gridProperties.get("gridHeight") / numRows;
        board[row][col] = switch (simulationType) {
          case Controller.FIRE ->
              new FireCellView(width, height, allVertices.get(ind), gridProperties);
          case Controller.GAME_OF_LIFE ->
              new GameOfLifeCellView(width, height, allVertices.get(ind), gridProperties);
          case Controller.PERCOLATION ->
              new PercolationCellView(width, height, allVertices.get(ind), gridProperties);
          case Controller.SCHELLING ->
              new SchellingCellView(width, height, allVertices.get(ind), gridProperties);
          case Controller.WATOR ->
              new WatorCellView(width, height, allVertices.get(ind), gridProperties);
          case Controller.SUGAR ->
              new SugarCellView(width, height, allVertices.get(ind), gridProperties);
          default -> throw new IllegalStateException("Unexpected value: " + simulationType);
        };

        Shape shape = board[row][col].getCellGraphic();
        root.getChildren().add(shape);
        root.getChildren().add(board[row][col].getTextBox());
        board[row][col].getTextBox().setLayoutX(board[row][col].getBoxLocationX());
        board[row][col].getTextBox().setLayoutY(board[row][col].getBoxLocationY());
        ind++;
      }
    }
    updateView(gridIterator);

    initializeButtons(eventHandlers);
    initializeSlider();

    simulationTitleDisplay = new Text(simulationName);
    simulationTitleDisplay.setId("simulation-title");
    simulationTitleDisplay.getStyleClass().add("simulation-title");
    simulationTitleDisplay.setX(configInt(TITLE_X_OFFSET_KEY));
    simulationTitleDisplay.setY(configInt(TITLE_Y_OFFSET_KEY));
    simulationTitleDisplay.setFont(new Font(40));

    root.getChildren().addAll(
        newSimulationButton,
        simulationInfoButton,
        startSimulationButton,
        saveSimulationButton,
        pauseSimulationButton,
        resetSimulationButton,
        simulationGraphButton,
        simulationTitleDisplay,
        speedSlider,
        speedLabel
    );
  }


  /**
   * set up the speed slider and the speed label
   */
  private void initializeSlider() {
    speedSlider = new Slider(configInt(SPEED_SLIDER_MIN_KEY),
        (configInt(SPEED_SLIDER_MAX_KEY)),
        (configInt(SLIDER_DEFAULT_KEY)));
    speedSlider.setLayoutX(configInt(BUTTON_START_X_KEY));
    speedSlider.setLayoutY(configInt(SPEED_SLIDER_Y_KEY));

    speedLabel = new Label(
        textProperties.getString(SPEED_LABEL_TEXT_KEY) + (int) speedSlider.getValue());
    speedLabel.setLayoutX(configInt(BUTTON_START_X_KEY));
    speedLabel.setLayoutY(configInt(SPEED_LABEL_Y_KEY));

  }


  /**
   * initialize the buttons
   *
   * @param eventHandlers a map of event handlers for the buttons
   */
  private void initializeButtons(Map<String, EventHandler<ActionEvent>> eventHandlers) {
    newSimulationButton = makeButton(buttonLabels.getString(NEW_SIMULATION_BUTTON_KEY),
        eventHandlers.get("newSimulationHandler"),
        configInt(BUTTON_START_X_KEY),
        configInt(NEW_SIMULATION_BUTTON_Y_KEY));
    simulationInfoButton = makeButton(buttonLabels.getString(ABOUT_BUTTON_KEY),
        eventHandlers.get("infoButtonHandler"),
        configInt(BUTTON_START_X_KEY),
        configInt(INFO_BUTTON_Y_KEY));
    startSimulationButton = makeButton(buttonLabels.getString(START_BUTTON_KEY),
        eventHandlers.get("startSimulationHandler"),
        configInt(BUTTON_START_X_KEY),
        configInt(START_BUTTON_Y_KEY));
    saveSimulationButton = makeButton(buttonLabels.getString(SAVE_BUTTON_KEY),
        eventHandlers.get("saveSimulationHandler"),
        configInt(BUTTON_START_X_KEY),
        configInt(SAVE_BUTTON_Y_KEY));
    pauseSimulationButton = makeButton(buttonLabels.getString(PAUSE_BUTTON_KEY),
        eventHandlers.get("pauseSimulationHandler"),
        configInt(BUTTON_START_X_KEY),
        configInt(PAUSE_BUTTON_Y_KEY));
    resetSimulationButton = makeButton(buttonLabels.getString(RESET_BUTTON_KEY),
        eventHandlers.get("resetSimulationHandler"),
        configInt(BUTTON_START_X_KEY),
        configInt(RESET_BUTTON_Y_KEY));
    simulationGraphButton = new Button("Show Graph");
    simulationGraphButton.setOnAction(event -> toggleGraphVisibility());
  }

  public void resetGraph() {
    graph.resetGraph();
  }

  public void toggleGraphVisibility() {
    if (graph.getGraphSection().isVisible()) {
      graph.getGraphSection().setVisible(false);
      simulationGraphButton.setText("Show Graph");
      closeGraph();
    } else {
      graph.getGraphSection().setVisible(true);
      showGraph();
      simulationGraphButton.setText("Close Graph");
    }
  }

  private void showGraph() {
    root.getChildren().add(graph.getGraphSection());
  }

  private void closeGraph() {
    root.getChildren().remove(graph.getGraphSection());
  }


  /**
   * adds an event listener for the speed slider
   *
   * @param speedSliderHandler listens to the change in number of the slider
   */
  public void setSpeedSliderHandler(ChangeListener<Number> speedSliderHandler) {
    speedSlider.valueProperty().addListener(speedSliderHandler);
  }


  /**
   * updates the speed label of the speed slider
   *
   * @param speed an integer that specifies the current speed on the slider
   */
  public void updateSpeedLabel(int speed) {

    speedLabel.setText(textProperties.getString(SPEED_LABEL_TEXT_KEY) + speed);
  }


  /**
   * makes a button
   *
   * @param buttonText a string of the text on the button
   * @param handler    an event handler that gets hooked on the button
   * @param colPos     an integer of the x position of the button
   * @param rowPos     an integer of the y position of the button
   * @return returns the button object
   */
  private Button makeButton(String buttonText, EventHandler<ActionEvent> handler, int colPos,
      int rowPos) {
    Button ret = new Button(buttonText);
    ret.setOnAction(handler);
    ret.setLayoutX(colPos);
    ret.setLayoutY(rowPos);
    return ret;
  }


  /**
   * gets the scene of the view component of the simulation
   *
   * @return returns the scene object
   */
  public Scene getSimulationScene() {
    return scene;
  }


  /**
   * updates the view component of the grid and the simulation
   *
   * @param gridIterator the cell model iterator of the grid model
   */
  public void updateView(Iterator<Cell> gridIterator) {
    stateCount.clear();

    while (gridIterator.hasNext()) {
      Cell c = gridIterator.next();
      Point location = c.getLocation();
      int col = (int) location.getCol();
      int row = (int) location.getRow();
      int state = c.getCurrentState();
      board[row][col].updateState(state, c.getText());
      if (!stateCount.containsKey(state)) {
        stateCount.put(state, 0);
      }
      stateCount.replace(state, stateCount.get(state) + 1);
      board[row][col].getTextBox().setLayoutX(board[row][col].getBoxLocationX());
      board[row][col].getTextBox().setLayoutY(board[row][col].getBoxLocationY());
    }

    graph.updateGraph(stateCount);
  }

  /**
   * gets integer value from config file according to its key string
   *
   * @param key a string that specifies the key for the target int value
   * @return returns the integer value
   */
  public Integer configInt(String key) {
    return Integer.parseInt(configProperties.getString(key));
  }

  /**
   * gets double value from config file according to its key string
   *
   * @param key a string that specifies the key for the target double value
   * @return returns the double value
   */
  public Double configDouble(String key) {
    return Double.parseDouble(configProperties.getString(key));
  }

}
