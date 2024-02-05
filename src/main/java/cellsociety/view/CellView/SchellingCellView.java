package cellsociety.view.CellView;

public class SchellingCellView extends CellView {
/**
 * the view component of cells in the Schelling's model of segregation simulation
 */

  public SchellingCellView(int state, double width, double height) {
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
        getCSS("agent");
        break;
      case 1:
        getCSS("agentX");
        break;
      case 2:
        getCSS("no-agent");
        break;
    }
  }
}
