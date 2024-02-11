package cellsociety.view.cellview;

import cellsociety.Point;
import java.util.List;
import java.util.Map;

/**
 * the view component of cells in the percolation simulation
 */
public class SugarCellView extends CellView {

  public SugarCellView(double width, double height, List<Point> vertices,
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
      case 0 -> getCss("sugar-0");
      case 1 -> getCss("sugar-1");
      case 2 -> getCss("sugar-2");
      case 3 -> getCss("sugar-3");
      case 4 -> getCss("sugar-4");
      default -> throw new IllegalStateException("Unexpected value: " + state);
    }
  }
}
