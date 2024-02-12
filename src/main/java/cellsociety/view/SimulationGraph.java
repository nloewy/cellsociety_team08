package cellsociety.view;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class SimulationGraph {

  private final StackPane pane;
  private final LineChart<Number, Number> lineChart;
  private final XYChart.Series<Number, Number> series;
  private final Map<Integer, XYChart.Series<Number, Number>> seriesMap;

  private int step;

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
    pane.setLayoutX(400);
    initializeChart(stateCount);
  }

  private void initializeChart(Map<Integer, Integer> stateCount) {
    getGraphSection().setVisible(false);
    // Iterate over the entries of the stateCount map
    for (Map.Entry<Integer, Integer> entry : stateCount.entrySet()) {
      // Add a data point to the series for each state count
      series.getData().add(new XYChart.Data<>(step, entry.getValue()));
    }
  }

  public void addSeries(int state) {
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    series.setName("State " + state);
    lineChart.getData().add(series);
    seriesMap.put(state, series);
  }


  public void updateGraph(Map<Integer, Integer> stateCount) {
    // Iterate over the entries of the stateCount map
    for (Map.Entry<Integer, Integer> entry : stateCount.entrySet()) {
      int state = entry.getKey();
      int count = entry.getValue();
      XYChart.Series<Number, Number> series = seriesMap.get(state);
      if (series != null) {
        // Add a data point to the series for each state count
        series.getData().add(new XYChart.Data<>(step, count));
      } else {
        // If series doesn't exist for this state, create a new series and add it to the chart
        addSeries(state);
        series = seriesMap.get(state);
        series.getData().add(new XYChart.Data<>(step, count));
      }
    }
    step++;
  }

  public LineChart<Number, Number> getLineChart() {
    return lineChart;
  }

//  public void updateGraph(int step, int[] cellCounts) {
//    series.getData().add(new XYChart.Data<>(step, cellCounts));
//  }

  public void resetGraph() {
    for (XYChart.Series<Number, Number> series : lineChart.getData()) {
      series.getData().clear();
    }

    step = 0;
  }

  public StackPane getGraphSection() {
    return pane;
  }

}

