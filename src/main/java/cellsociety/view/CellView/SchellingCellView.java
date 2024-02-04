package cellsociety.view.CellView;

public class SchellingCellView extends CellView{

  public SchellingCellView(int state, double width, double height) {
    super(state, width, height);
  }

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
