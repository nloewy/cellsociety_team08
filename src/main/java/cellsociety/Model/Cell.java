package cellsociety.Model;

import java.util.*;

public class Cell {

  /**
   * The Cell is the atomic unit of our simulation. Each individual cell represents a location on a
   * grid, and has some sort of state.
   *
   * @author Noah Loewy
   */
  private String myCurrentState;
  private String myNextState;
  private List<Cell> myNeighbors;
  private Point myLocation;

  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param x            is the x-coordinate of the cell on the 2-dimensional grid
   * @param y            is the y-coordinate of the cell on the 2-dimensional grid
   */
  public Cell(String initialState, int x, int y) {
    myCurrentState = initialState;
    myNextState = null;
    myLocation = new Point(x, y);
  }

  /**
   * This function updates the state of the cell after calling the transition function. The new
   * currentState takes the value of the nextState placeholder, and nextState is set to null.
   */
  public void updateStates() {
    myCurrentState = myNextState;
    myNextState = null;
  }

  /**
   * Retrieves myCurrentState instance variable
   *
   * @return myCurrentState, the current state of the cell object.
   */
  public String getCurrentState() {
    return myCurrentState;
  }

  /**
   * Retrieves myLocation instance variable
   *
   * @return myLocation, the current x,y position of the cell object on the 2-dimensional grid
   */
  public Point getLocation() {
    return myLocation;
  }

}
