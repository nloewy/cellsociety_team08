package cellsociety.model.core.cell;

import cellsociety.Point;
import cellsociety.model.core.grid.Grid;
import cellsociety.model.core.shape.Shape;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.List;
import java.util.Map;

/**
 * The Cell is the atomic unit of our simulation. Each individual cell represents a location on a
 * grid, and has some sort of state.
 *
 * @author Noah Loewy
 */

public abstract class Cell<T extends Cell<T>> {

  public static final int PLACEHOLDER = -1;
  private final Point myLocation;
  private final List<Point> myVertices;
  private int myCurrentState;
  private int myNextState;
  private List<T> myNeighbors;

  /**
   * Constructs a Generic Cell object. Note that Cell is an abstract class. This is because all
   * simulations use a specific kind of Cell, but they all share common methods.
   *
   * @param initialState the integer representation of the cell's current state
   * @param row          the row the cell is positioned at as represented on a 2D grid
   * @param col          the column the cell is positioned at as represented on a 2D grid
   * @param shapeType    the shape of a cell, as represented on a 2D coordinate grid
   */
  public Cell(int initialState, int row, int col, Shape shapeType) {
    myCurrentState = initialState;
    myNextState = PLACEHOLDER;
    myLocation = new Point(row, col);
    myVertices = shapeType.getVertices(row, col);
  }

  /**
   * Constructs a Generic Cell object. Note that Cell is an abstract class. This is because all
   * simulations use a specific kind of Cell, but they all share common methods.
   *
   * @param hoodType the integer representation of the cell's current state
   * @param grid     the row the cell is positioned at as represented on a 2D coordinate grid
   */
  public void initializeNeighbors(Neighborhood hoodType, Grid grid) {
    myNeighbors = hoodType.getNeighbors(grid, this);
  }

  /**
   * This function updates the state of the cell after a given timestep.
   */
  public abstract void transition();

  /**
   * @return the integer representation of the cell's current state (prior to update)
   */
  public int getCurrentState() {
    return myCurrentState;
  }

  /**
   * @param state, the new value of the cell's current state
   */
  public void setCurrentState(int state) {
    myCurrentState = state;
  }

  /**
   * @return the integer representation of the cell's next state (following update)
   */
  public int getNextState() {
    return myNextState;
  }

  /**
   * Updates the myNextState instance variable
   *
   * @param nextState the new value of myNextState
   */
  public void setNextState(int nextState) {
    myNextState = nextState;
  }

  /**
   * Updates the state instance variables after a timeset, by using a temporary placeholder for the
   * next state, and setting the current state equal to the old "next state"
   */
  public void updateStates() {
    myCurrentState = myNextState;
    myNextState = PLACEHOLDER;
  }

  /**
   * Sets text to be displayed for specific cell
   * @return the new text (or emoji) displayed in cell
   */
  public String getText() {
    return "";
  }

  /**
   * Given an integer representing a target state, determines the number of neighboring cells that
   * have a current state matching the target state.
   *
   * @param state an integer, representing the state to check for
   * @return the number of neighboring cells where myCurrentState == state
   */
  public int countNeighborsInState(int state) {
    int count = 0;
    for (T c : getNeighbors()) {
      if (c.getCurrentState() == state) {
        count++;
      }
    }
    return count;
  }

  /**
   * Retrieves myNeighbors instance variable
   *
   * @return list of neighboring cells, using the neighborhood definition provided during Cell
   * initialization in the Grid class
   */
  public List<T> getNeighbors() {
    return myNeighbors;
  }

  /**
   * Retrieves myLocation instance variable
   *
   * @return the current x,y position of the cell object on the 2-dimensional grid
   */
  public Point getLocation() {
    return myLocation;
  }

  /**
   * Retrieves myVertices instance variable
   *
   * @return a list of point objects, representing the vertices of the cell's graphical
   * representation on a 2D plane, under the assumption that each cell is alotted 1 square unit of
   * space.
   */
  public List<Point> getVertices() {
    return myVertices;
  }

  /**
   * Retrieves the centroid of the Cell's graphical representation, based on the Center of Mass of
   * its vertices
   *
   * @return a point object representing the Cell's center of mass when displayed graphically on a
   * 2D plane
   */
  public Point getCentroid() {
    double rowSum = 0;
    double colSum = 0;
    for (Point p : myVertices) {
      rowSum += p.getRow();
      colSum += p.getCol();
    }
    return new Point(rowSum, colSum);
  }

  /**
   * Updates myNeighborhood instance variable
   *
   * @param neighborhood a list of generic Cells representing the new neighbors of this cell.
   */
  public void setNeighborhood(List<T> neighborhood) {
    myNeighbors = neighborhood;
  }


  /**
   * Calculates euclidian distance from current cell to another cell
   * @param cell, a cell object
   * @return distance, euclidian distance from centroid of one cell to another
   */
  public double distance(T cell) {
    if (cell == null) {
      return Integer.MAX_VALUE;
    }
    return Math.pow((getCentroid().getCol() - cell.getCentroid().getCol()), 2) + Math.pow(
        (getCentroid().getRow() - cell.getCentroid().getRow()), 2);
  }

  /**
   * Checks if two objects are both cells at the same location
   * @param other, another object, probably a cell
   * @return true if and only if this and other are both cells at the same location
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (getClass() != other.getClass()) {
      return false;
    }
    Cell other1 = (Cell) other;
    return (other1.getCentroid().getRow() == getCentroid().getRow() &&
        other1.getCentroid().getCol() == getCentroid().getCol());
  }

  /**
   * Gets hashcode of cell object (the hashcode of its centroid), as all cell objects have unique
   * centroids.
   * @return
   */
  @Override
  public int hashCode() {
    return getCentroid().hashCode();
  }


  /**
   * Updates parameters of simulation
   * @param newParameters, map of param name to param value for all needed parameters
   */
  public void setParams(Map<String, Double> newParameters) {
  }
}
