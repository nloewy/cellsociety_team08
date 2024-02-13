package cellsociety.view;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

/**
 * this class is an object of the simulation graph
 */

public class SimulationGraph {

  private final StackPane pane;
  private final LineChart<Number, Number> lineChart;
  private final XYChart.Series<Number, Number> series;
  private final Map<Integer, XYChart.Series<Number, Number>> seriesMap;
  private int step;

  public static final String GRAPH_PANE_X_KEY = "GRAPH_PANE_X";
  public static final String STATE_KEY = "state";


  /**
   * constructs the graph object
   *
   * @param stateCount map of existing states and the number of cells in that state
   */
  public SimulationGraph(Map<Integer, Integer> stateCount) {
    step = 0;
    NumberAxis xaxis = new NumberAxis();
    NumberAxis yaxis = new NumberAxis();
    lineChart = new LineChart<>(xaxis, yaxis);

    seriesMap = new HashMap<>();

    series = new XYChart.Series<>();
    lineChart.getData().add(series);
    pane = new StackPane();
    pane.getChildren().add(lineChart);
    pane.setLayoutX(Integer.parseInt(ResourceBundle.getBundle(
        SimulationPage.DEFAULT_RESOURCE_PACKAGE +
            SimulationPage.CONFIG_RESOURCE_FILE).getString(GRAPH_PANE_X_KEY)));
    initializeChart(stateCount);
  }

  private void initializeChart(Map<Integer, Integer> stateCount) {
    getGraphSection().setVisible(false);
    for (Map.Entry<Integer, Integer> entry : stateCount.entrySet()) {
      series.getData().add(new XYChart.Data<>(step, entry.getValue()));
    }
  }


  private void addSeries(int state) {
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    series.setName(
        ResourceBundle.getBundle(Controller.TEXT_CONFIGURATION).getString(STATE_KEY) + state);
    lineChart.getData().add(series);
    seriesMap.put(state, series);
  }

  /**
   * updates the graph by creating new node on the graph according to new state counts
   *
   * @param stateCount map of existing states and the number of cells in that state
   */
  public void updateGraph(Map<Integer, Integer> stateCount) {
    for (Map.Entry<Integer, Integer> entry : stateCount.entrySet()) {
      int state = entry.getKey();
      int count = entry.getValue();
      XYChart.Series<Number, Number> series = seriesMap.get(state);
      if (series != null) {
        series.getData().add(new XYChart.Data<>(step, count));
      } else {
        addSeries(state);
        series = seriesMap.get(state);
        series.getData().add(new XYChart.Data<>(step, count));
      }
    }
    step++;
  }

  /**
   * clears the graph
   */
  public void resetGraph() {
    for (XYChart.Series<Number, Number> series : lineChart.getData()) {
      series.getData().clear();
    }
    step = 0;
  }

  /**
   * gets the graph view
   *
   * @return the stackpane contianing the graph
   */
  public StackPane getGraphSection() {
    return pane;
  }

}

