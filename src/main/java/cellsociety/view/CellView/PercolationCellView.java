package cellsociety.view.CellView;

public class PercolationCellView extends CellView {

  public PercolationCellView(int state, double width, double height) {
    super(state, width, height);
  }

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
