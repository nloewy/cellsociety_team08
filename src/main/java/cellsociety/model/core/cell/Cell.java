package cellsociety.model.core.cell;

import cellsociety.Point;
import cellsociety.model.core.grid.Grid;
import cellsociety.model.core.shape.CellShape;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.List;

/**
 * The Cell is the atomic unit of our simulation. Each individual cell represents a location on a
 * grid, and has some sort of state.
 *
 * @author Noah Loewy
 */

public abstract class Cell<T extends Cell<T>> {

  public static final int PLACEHOLDER = -1;
  private int myCurrentState;

  private int myNextState;
  private Point myLocation;
  private List<Point> myVertices;
  private List<T> myNeighbors;
  private Point myCentroid;


  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param row          is the row-coordinate of the cell on the 2-dimensional grid
   * @param col          is the col-coordinate of the cell on the 2-dimensional grid
   */
  public Cell(int initialState, int row, int col, CellShape shapeType) {
    myCurrentState = initialState;
    myNextState = PLACEHOLDER;
    myLocation = new Point(row, col);
    myVertices = shapeType.getVertices(row, col);
  }

  public void initializeNeighbors(Neighborhood hoodType, Grid grid) {
    myNeighbors = hoodType.getNeighbors(grid, this);

  }

  /**
   * This function updates the state of the cell after calling the transition function. The new
   * currentState takes the value of the nextState placeholder, and nextState is set to placeholder
   * value.
   */
  public abstract void transition();

  public int getCurrentState() {
    return myCurrentState;
  }

  public int getNextState() {
    return myNextState;
  }

  public void setCurrentState(int currentState) {
    myCurrentState = currentState;
  }

  public void setNextState(int nextState) {
    myNextState = nextState;
  }

  public void updateStates() {
    myCurrentState = myNextState;
    myNextState = PLACEHOLDER;
  }


  /**
   * Given a list of cells, and an integer representing a state, determines the number of cells in
   * the list that are currently at that state
   *
   * @param state, an integer, representing the state to check for
   * @return count, representing the number of cells in neighbors where myCurrentState == state
   */
  public int countNeighborsInState(int state) {
    int count = 0;
    for (Cell c : getNeighbors()) {
      if (c.getCurrentState() == state) {
        count++;
      }
    }
    return count;
  }

  public List<T> getNeighbors() {
    return myNeighbors;
  }

  /**
   * Retrieves myLocation instance variable
   *
   * @return myLocation, the current x,y position of the cell object on the 2-dimensional grid
   */
  public Point getLocation() {
    return myLocation;
  }


  public List<Point> getVertices() {
    return myVertices;
  }

  public Point getCentroid() {
    double rowSum = 0;
    double colSum = 0;
    for (Point p : myVertices) {
      rowSum += p.getRow();
      colSum += p.getCol();
    }
    myCentroid = new Point(rowSum, colSum);
    return myCentroid;
  }

  public void setNeighborhood(List<T> neighbors) {
    myNeighbors = neighbors;
  }
}
