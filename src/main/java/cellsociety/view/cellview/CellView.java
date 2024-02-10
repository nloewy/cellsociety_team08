package cellsociety.view.cellview;

import cellsociety.Point;
import java.util.List;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;


public abstract class CellView extends Region {

  private CellShape avaliableShapes;
  private Rectangle rec;
  private Polygon hex;
  private String shape;

  public CellView(String shape, int state, double width, double height, List<Point> vertices) {
//    rec = new Rectangle(width, height);
    avaliableShapes = new CellShape(width, height, vertices);
    rec = avaliableShapes.getRectangle();
    hex = avaliableShapes.getHexagon();
    this.shape = shape;

    System.out.println(shape);

    //testing grid iterator in simualtion page
    setColors(state);

    setStroke(rec);
    setStroke(hex);
  }

  private void setStroke(Shape shape) {
    shape.setStroke(Color.BLACK); //TODO: read color from css file
    shape.setStrokeWidth(1);
    shape.setStrokeType(StrokeType.OUTSIDE);
  }

  public abstract void setColors(int state);


  public void updateState(int state) {
    getCellGraphic().getStyleClass().clear();
    setColors(state);
  }

  public void getCss(String idName) {
    rec.setId(idName);
    hex.setId(idName);
    rec.getStyleClass().add(idName);
    hex.getStyleClass().add(idName);
  }


  public Shape getCellGraphic() {
    return switch (shape){
      case "square" -> getRecTangleGraphic();
      case "hexagon" -> getHexagonGraphic();
      default -> throw new IllegalStateException("Unexpected value: " + shape);
    };
  }

  public Rectangle getRecTangleGraphic(){
    return rec;
  }

  public Polygon getHexagonGraphic(){
    System.out.println("getting hex graphic");
    return hex;
  }

  //TODO: add the r's to the gridpane not the cellviews, avoid extending the region because you
  // don't want to add a bunch of unnecesary packages when extending.

}

