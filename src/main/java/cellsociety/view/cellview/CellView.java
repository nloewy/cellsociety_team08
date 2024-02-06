package cellsociety.view.cellview;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public abstract class CellView extends Region {

  private Rectangle rec;

  public CellView(int state, double width, double height) {
    rec = new Rectangle(width, height); //TODO: read width and height from css

    //testing grid iterator in simualtion page
    setColors(state);

    rec.setStroke(Color.BLACK); //TODO: read color from css file
    rec.setStrokeWidth(1);
    rec.setStrokeType(StrokeType.OUTSIDE);
  }

  public abstract void setColors(int state);


  public void updateState(int state) {
    getCellGraphic().getStyleClass().clear();
    setColors(state);
    System.out.println(state);
  }

  public void getCss(String idName) {
    rec.setId(idName);
    rec.getStyleClass().add(idName);
  }

  public Rectangle getCellGraphic() {
    return rec;
  }

  //TODO: add the r's to the gridpane not the cellviews, avoid extending the region because you don't want to add a bunch of unnecesary packages when extending.
}

