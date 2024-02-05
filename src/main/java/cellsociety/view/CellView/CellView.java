package cellsociety.view.CellView;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * An abstract class that sets up the view component of a cell.
 * subclasses will implement the setColors() method according to the simulation
 */
public abstract class CellView extends Region {

  private Rectangle r;

  /**
   * Constructs a view component of a cell
   * @param state an integer that specifies which state the cell is in
   * @param width a double that specifies the width of a cell
   * @param height a double that specifies the height of a cell
   */
  public CellView(int state, double width, double height) {
    super();
    r = new Rectangle(width, height); //TODO: read width and height from css

    //testing grid iterator in simualtion page
    setColors(state);

    r.setStroke(Color.BLACK); //TODO: read color from css file
    r.setStrokeWidth(1);
    r.setStrokeType(StrokeType.OUTSIDE);
  }

  /**
   * this abstract function will be implemented by subclasses to specify the color associated with each state of the cell
   * @param state an integer that specifies which state the cell is in
   */
  public abstract void setColors(int state);

  /**
   * this method updates the color of the cell according to the new state passed in
   * @param state an integer that specifies which state the cell is in
   */
  public void updateState(int state) {
    getCellGraphic().getStyleClass().clear();
    setColors(state);
    System.out.println(state);
  }

  /**
   * this method sets the css id for the cell
   * @param idName a string that specifies the id of the cell
   */
  public void getCSS(String idName) {
    r.setId(idName);
    r.getStyleClass().add(idName);
  }

  /**
   * a getter method that gets the cell graphic (the rectangle) to be displayed in the grid
   * @return returns the rectangle that represents the cell in the grid
   */
  public Rectangle getCellGraphic() {
    return r;
  }
}
