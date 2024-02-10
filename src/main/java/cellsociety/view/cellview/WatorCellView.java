package cellsociety.view.cellview;

import cellsociety.Point;
import java.util.List;
import java.util.Map;

/**
 * the view component of cells in the Wa-Tor simulation
 */
public class WatorCellView extends CellView {

  public WatorCellView(double width, double height, List<Point> vertices,
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
    switch (state) {
      case 0 -> getCss("empty-water");
      case 1 -> getCss("fish");
      case 2 -> getCss("shark");
      default -> throw new IllegalStateException("Unexpected value: " + state);
    }
  }
}
