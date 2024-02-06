package cellsociety.view.cellview;

/**
 * the view component of cells in the Schelling's model of segregation simulation
 */
public class SchellingCellView extends CellView {

  public SchellingCellView(int state, double width, double height) {
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
      case 0 -> getCss("agent");
      case 1 -> getCss("agentX");
      case 2 -> getCss("no-agent");
      default -> throw new IllegalStateException("Unexpected value: " + state);
    }
  }
}
