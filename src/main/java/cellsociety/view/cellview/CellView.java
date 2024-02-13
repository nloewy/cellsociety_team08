package cellsociety.view.cellview;

import cellsociety.Point;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

/**
 * This class is the view component of a cell
 */

public abstract class CellView {

  private final Polygon shape;
  private final Label textBox;

  private int boxLocationX;
  private int boxLocationY;

  /**
   * Constructs a cell view object
   * @param width double, width of a cell
   * @param height double, height of a cell
   * @param vertices List, x,y coordinates of the vertices of the cell shape
   * @param gridProperties
   */
  public CellView(double width, double height, List<Point> vertices,
      Map<String, Double> gridProperties) {
    shape = new Polygon();
    for (Point vertex : vertices) {
      shape.getPoints().addAll(width * vertex.getCol() + gridProperties.get("gridStartX"),
          height * vertex.getRow() + gridProperties.get("gridStartY"));
      boxLocationX += (int) (width * vertex.getCol() + gridProperties.get("gridStartX"));
      boxLocationY += (int) (height * vertex.getRow() + gridProperties.get("gridStartY"));
    }
    boxLocationX /= vertices.size();
    boxLocationY /= vertices.size();
    setStroke(shape);
    textBox = new Label("X");
  }

  private void setStroke(Shape shape) {
    shape.setStroke(Color.BLACK); //TODO: read color from css file
    shape.setStrokeWidth(2);
    shape.setStrokeType(StrokeType.OUTSIDE);
  }

  /**
   * This method turns the cell outline on and off according to the boolean status flag argument
   * @param onOff boolean flag - whether the cell outline should go on or go off
   */
  public void toggleStrokeWidth(boolean onOff) {
    if (onOff) {
      shape.setStrokeWidth(2);
    } else {
      shape.setStrokeWidth(0);
    }
  }

  /**
   * abstract method, implemented by sub classes to set colors for cells depending on their state
   * @param state int, the state the cell is in
   */
  public abstract void setColors(int state);

  /**
   * Update cell status and color according to its new state
   * @param state int, the new state the cell is going into
   * @param text String, the text set onto the cell
   */
  public void updateState(int state, String text) {
    getCellGraphic().getStyleClass().clear();
    textBox.setText(text);
    setColors(state);
  }

  /**
   * gets the css styling for the cell according to its id
   * @param idName String, css id of the cell
   */
  public void getCss(String idName) {
    shape.setId(idName);
    shape.getStyleClass().add(idName);
  }

  /**
   * gets the cell shape graphic
   * @return returns the shape object of the cell
   */
  public Shape getCellGraphic() {
    return shape;
  }

  /**
   * gets the label of the cell
   * @return textbox object
   */
  public Label getTextBox() {
    return textBox;
  }

  /**
   * gets the x coordinate of the textbox
   * @return double, the x coordinate
   */
  public double getBoxLocationX() {

    Bounds textBounds = textBox.getLayoutBounds();
    double textBoxWidth = textBounds.getWidth();
    return boxLocationX - textBoxWidth;
  }

  /**
   * gets the y coordniate of the textbox
   * @return double, the y coordinate.
   */
  public double getBoxLocationY() {
    Bounds textBounds = textBox.getLayoutBounds();
    double textBoxHeight = textBounds.getHeight();
    return boxLocationY - textBoxHeight;
  }
}
