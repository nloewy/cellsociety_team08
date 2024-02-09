package cellsociety.view.cellview;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class CellShape {

  private Rectangle rec;
  private Polygon hex;

  public CellShape(String shape){
    switch (shape){
      case "triangle" -> this.hex = new Polygon();
      case "rectangle" -> this.rec = new Rectangle();
      default -> throw new IllegalStateException("Unexpected value: " + shape);
    }
  }

  public Rectangle getRectangle(){
    return rec;
  }

  public Polygon getHexagon(){
    return hex;
  }
}
