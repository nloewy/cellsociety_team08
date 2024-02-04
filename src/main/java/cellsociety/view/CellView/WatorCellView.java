package cellsociety.view.CellView;

public class WatorCellView extends CellView{

  public WatorCellView(int state, double width, double height) {
    super(state, width, height);
  }

  @Override
  public void setColors(int state) {
    switch (state){
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
