package cellsociety.view;

import java.util.Map;
import java.util.Stack;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SimulationGraph {
  private StackPane pane;
  private LineChart<Number, Number> lineChart;
  private XYChart.Series<Number, Number> series;
  private int step;

  public SimulationGraph(Map<Integer, Integer> stateCount) {
    step = 0;
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    lineChart = new LineChart<>(xAxis, yAxis);
    series = new XYChart.Series<>();
    lineChart.getData().add(series);
    pane = new StackPane();
    pane.getChildren().add(lineChart);
    pane.setLayoutX(400);
    initializeChart(stateCount);
  }

  private void initializeChart(Map<Integer, Integer> stateCount) {
    // Iterate over the entries of the stateCount map
    for (Map.Entry<Integer, Integer> entry : stateCount.entrySet()) {
      // Add a data point to the series for each state count
      series.getData().add(new XYChart.Data<>(step, entry.getValue()));
    }
  }

  public LineChart<Number, Number> getLineChart() {
    return lineChart;
  }

//  public void updateGraph(int step, int[] cellCounts) {
//    series.getData().add(new XYChart.Data<>(step, cellCounts));
//  }

  public void resetGraph(){

  }

  public StackPane getGraphSection(){
    return pane;
  }

}

