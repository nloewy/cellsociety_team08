package cellsociety.view.cellview;

import cellsociety.Point;
import java.util.List;
import java.util.Map;

/**
 * the view component of cells in the Schelling's model of segregation simulation
 */
public class SchellingCellView extends CellView {

  public SchellingCellView(double width, double height, List<Point> vertices,
      Map<String, Double> gridProperties) {
    super(width, height, vertices, gridProperties);
  }

  /**
   * sets the color of the view component of the cell according to their current state
   *
   * @param state an integer that specifies which state the cell is in
   */
  @Override
  public void setColors(int state) {
    System.out.println("curr state: " + state);
    switch (state) {
      case 0 -> getCss("agent");
      case 1 -> getCss("agentX");
      case 2 -> getCss("no-agent");
      default -> throw new IllegalStateException("Unexpected value: " + state);
    }
  }
}
