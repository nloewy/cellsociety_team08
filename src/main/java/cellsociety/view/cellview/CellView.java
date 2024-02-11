package cellsociety.view.cellview;

import cellsociety.Point;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;


public abstract class CellView{

  private Polygon shape;
  private Label textBox;

  private int boxLocationX;
  private int boxLocationY;

  public CellView(double width, double height, List<Point> vertices,
      Map<String, Double> gridProperties) {
    shape = new Polygon();
    for (Point vertex : vertices) {
      shape.getPoints().addAll(width * vertex.getCol() + gridProperties.get("gridStartX"),
          height * vertex.getRow() + gridProperties.get("gridStartY"));
      boxLocationX += width * vertex.getCol() + gridProperties.get("gridStartX");
      boxLocationY += height * vertex.getRow() + gridProperties.get("gridStartY");
    }

    boxLocationX /= vertices.size();
    boxLocationY /= vertices.size();
    setStroke(shape);


    textBox = new Label("X");

//    getChildren().addAll(shape, textBox); // Add shape and text box as children of CellView
  }

  private void setStroke(Shape shape) {
    shape.setStroke(Color.BLACK); //TODO: read color from css file
    shape.setStrokeWidth(3);
    shape.setStrokeType(StrokeType.OUTSIDE);
  }

  public void toggleStrokeWidth(boolean onOff) {
    if(onOff) {
      shape.setStrokeWidth(3);
    }
    else {
      shape.setStrokeWidth(0);
    }
  }
  public abstract void setColors(int state);

  public void updateState(int state, String text) {
    getCellGraphic().getStyleClass().clear();
    textBox.setText(text);
    setColors(state);
  }

  public void getCss(String idName) {
    shape.setId(idName);
    shape.getStyleClass().add(idName);
  }

  public Shape getCellGraphic() {
    return shape;
  }

  // Getter for the text box
  public Label getTextBox() {
    return textBox;

  }

  public double getBoxLocationX() {

    Bounds textBounds = textBox.getLayoutBounds();
    double textBoxWidth = textBounds.getWidth();
    return boxLocationX - textBoxWidth;
  }

  public double getBoxLocationY() {
    Bounds textBounds = textBox.getLayoutBounds();
    double textBoxHeight = textBounds.getHeight();
    return boxLocationY - textBoxHeight;
  }
}
