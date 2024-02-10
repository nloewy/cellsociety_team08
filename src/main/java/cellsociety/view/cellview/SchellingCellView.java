package cellsociety.view.cellview;

import cellsociety.Point;
import java.util.List;

/**
 * the view component of cells in the Schelling's model of segregation simulation
 */
public class SchellingCellView extends CellView {

  public SchellingCellView(String shape, int state, double width, double height, List<Point> vertices) {
    super(shape, state, width, height, vertices);
  }

  /**
   * sets the color of the view component of the cell according to their current state
   *
   * @param state an integer that specifies which state the cell is in
   */
  @Override
  public void setColors(int state) {
    System.out.println("curr state: "+state);
    switch (state) {
      case 0 -> getCss("agent");
      case 1 -> getCss("agentX");
      case 2 -> getCss("no-agent");
      default -> throw new IllegalStateException("Unexpected value: " + state);
    }
  }
}
