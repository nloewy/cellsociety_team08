package cellsociety.view;

public class FireCellView extends CellView {

  public FireCellView(int state, double width, double height) {
    super(state, width, height);
  }

  @Override
  public void setColors(int state) {
    getCellGraphic().getStyleClass().clear();
    switch (state){
      case 0:
        getCSS("empty-cell");
        break;
      case 1:
        getCSS("tree-cell");
        break;
      case 2:
        getCSS("burning-cell");
        break;
    }
  }
}
