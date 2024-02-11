package cellsociety.model.simulation;

import cellsociety.exception.InvalidValueException;
import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.grid.Grid;
import cellsociety.model.core.grid.WarpedGrid;
import cellsociety.model.core.shape.HexagonShape;
import cellsociety.model.core.shape.RectangleShape;
import cellsociety.model.core.shape.Shape;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Abstract Class that runs the simulation of a cellular automata. Subclasses will implement
 * transitionFunction() based on the rules of the simulation
 *
 * @author Noah Loewy
 */

public abstract class Simulation<T extends Cell> {

  private Neighborhood myNeighborhood;
  private Grid myGrid;
  private String myGridType;
  private int myRow;
  private int myCol;

  public Simulation() {
  }

  /**
   * Constructs a basic Simulation object
   *
   * @param hoodType, the definition of neighbors
   * @param gridType  type of grid used in simulation
   */

  public Simulation(Neighborhood hoodType, String gridType) {
    myNeighborhood = hoodType;
    myGridType = gridType;
  }

  public void setEdgeType(String newEdgeType) {
    Iterator<T> iter = getIterator();
    List<T> lst = new ArrayList<>();
    while (iter.hasNext()) {
      T cell = iter.next();
      lst.add(cell);
    }
    myGridType = newEdgeType;
    initializeMyGrid(myRow, myCol, lst);
  }

  public void initializeMyGrid(int row, int col, List<T> cellList) {
    myRow = row;
    myCol = col;
    System.out.println(myGridType);
    myGrid = switch (myGridType) {
      case "Normal" -> new Grid(row, col, cellList);
      case "Warped" -> new WarpedGrid(row, col, cellList);
      default -> throw new InvalidValueException("Edge Type Does Not Exist");
    };
    Iterator<T> iterator = myGrid.iterator();
    while (iterator.hasNext()) {
      T cell = iterator.next();
      cell.setNeighborhood(myNeighborhood.getNeighbors(myGrid, cell));
    }
  }


  /**
   * Iterates through all available cells and updates the current state based on the results of the
   * transition function
   */
  public void processUpdate() {
    Iterator<T> iterator = myGrid.iterator();
    while (iterator.hasNext()) {
      T cell = iterator.next();
      cell.updateStates();
    }
  }

  public void createCellsAndGrid(int row, int col, List<Integer> stateList,
      Shape shape, Neighborhood hoodType) {
    List<T> cellList = cellMaker(col, stateList, shape);
    initializeMyGrid(row, col, cellList);
    for (T cell : cellList) {
      cell.initializeNeighbors(hoodType, myGrid);
    }
  }

  public abstract List<T> cellMaker(int col, List<Integer> stateList, Shape cellShape);


  public Shape getCellShape(String shapeStr) {
    return switch (shapeStr) {
      case "square" -> new RectangleShape();
      case "hexagon" -> new HexagonShape();
      default -> throw new InvalidValueException("Cell Shape Does Not Exist");
    };
  }

  /**
   * Transition function for Percolation. All cells remain in their state, unless the cell is open,
   * in which the cell is passed into the helper function handleOpenCell for transitioning
   */
  public void transitionFunction() {
    Iterator<T> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      T currentCell = gridIterator.next();
      currentCell.transition();
    }
  }

  public Neighborhood getNeighborhood() {
    return myNeighborhood;
  }

  public Grid getGrid() {
    return myGrid;
  }

  /**
   * Retrieves the Grid's object that can access the grid of cells while hiding the Data Structure
   * used to implement it.
   *
   * @return Iterator object that can iterate through my grid
   */
  public Iterator<T> getIterator() {
    return myGrid.iterator();
  }

  public static boolean isInteger(double number) {
    return Math.floor(number) == number;

  }

  public void setParams(Map<String, Double> newParameters) {
    Iterator<T> iterator = myGrid.iterator();
    while (iterator.hasNext()) {
      T cell = iterator.next();
      cell.setParams(newParameters);
    }

  }
}
