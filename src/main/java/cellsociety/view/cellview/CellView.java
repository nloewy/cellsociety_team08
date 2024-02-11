package cellsociety.view.cellview;

import cellsociety.Point;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;


public abstract class CellView extends Region {

  private Polygon shape;

  public CellView(double width, double height, List<Point> vertices,
      Map<String, Double> gridProperties) {
    shape = new Polygon();
    for (Point vertex : vertices) {
      shape.getPoints().addAll(width * vertex.getCol() + gridProperties.get("gridStartX"),
          height * vertex.getRow() + gridProperties.get("gridStartY"));
    }
    setStroke(shape);
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
    shape.setId(idName);
    shape.getStyleClass().add(idName);
  }


  public Shape getCellGraphic() {
    return shape;
  }

  //TODO: add the r's to the gridpane not the cellviews, avoid extending the region because you
  // don't want to add a bunch of unnecesary packages when extending.

}

