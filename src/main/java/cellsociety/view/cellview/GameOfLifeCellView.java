package cellsociety.view.cellview;

import cellsociety.Point;
import java.util.List;
import java.util.Map;

/**
 * the view component of cells in the game of life simulation
 */
public class GameOfLifeCellView extends CellView {

  public GameOfLifeCellView(double width, double height, List<Point> vertices,
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
      case 0 -> getCss("dead-cell");
      case 1 -> getCss("alive-cell");
      default -> throw new IllegalStateException("Unexpected value: " + state);
    }
  }
}
