package cellsociety.view.cellview;

import cellsociety.Point;
import java.util.List;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class CellShape {

  private Rectangle rec;
  private Polygon hex;

  public CellShape(double width, double height, List<Point> vertices){
      this.hex = new Polygon();
      // Calculate coordinates for hexagon vertices
      for (Point vertex : vertices) {
        hex.getPoints().addAll(vertex.getCol(),vertex.getRow());
        System.out.println(vertex.getCol()+vertex.getRow());
      }

      this.rec = new Rectangle(width, height);
  }

  public Rectangle getRectangle(){
    return rec;
  }

  public Polygon getHexagon(){
    return hex;
  }
}
