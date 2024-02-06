package cellsociety.view.cellview;

/**
 * the view component of cells in the game of life simulation
 */
public class GameOfLifeCellView extends CellView {

  public GameOfLifeCellView(int state, double width, double height) {
    super(state, width, height);
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
