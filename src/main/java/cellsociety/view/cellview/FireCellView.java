package cellsociety.view.cellview;

/**
 * the view component of cells in the spread of fire simulation
 */
public class FireCellView extends CellView {

  public FireCellView(int state, double width, double height) {
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
      case 0:
        getCss("empty-cell");
        break;
      case 1:
        getCss("tree-cell");
        break;
      case 2:
        getCss("burning-cell");
        break;
    }
  }
}
