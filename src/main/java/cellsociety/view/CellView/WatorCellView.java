package cellsociety.view.CellView;

/**
 * the view component of cells in the Wa-Tor simulation
 */
public class WatorCellView extends CellView {

  public WatorCellView(int state, double width, double height) {
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
        getCSS("empty-water");
        break;
      case 1:
        getCSS("fish");
        break;
      case 2:
        getCSS("shark");
        break;
    }
  }
}
