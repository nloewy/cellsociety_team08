package cellsociety.view.CellView;

public class GameOfLifeCellView extends CellView {

  public GameOfLifeCellView(int state, double width, double height) {
    super(state, width, height);
  }

  @Override
  public void setColors(int state) {
    switch (state) {
      case 0:
        getCSS("dead-cell");
        break;
      case 1:
        getCSS("alive-cell");
        break;
    }
  }
}
