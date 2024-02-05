package cellsociety.view.CellView;

/**
 * the view component of cells in the percolation simulation
 */
public class PercolationCellView extends CellView {

  public PercolationCellView(int state, double width, double height) {
    super(state, width, height);
  }

  /**
   * sets the color of the view component of the cell according to their current state
   * @param state an integer that specifies which state the cell is in
   */
  @Override
  public void setColors(int state) {
    switch (state) {
      case 0:
        getCSS("empty-site");
        break;
      case 1:
        getCSS("full-site");
        break;
      case 2:
        getCSS("blocked-site");
        break;
    }
  }
}
