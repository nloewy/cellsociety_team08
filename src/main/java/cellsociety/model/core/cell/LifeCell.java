package cellsociety.model.core.cell;

import cellsociety.model.core.shape.Shape;
import cellsociety.model.simulation.GameOfLifeSimulation;
import java.util.Map;

/**
 * Represents an extension of the Cell, and serves as the atomic unit of the Fire simulation.
 * Contains private methods that allow for the transition of Fire Cell objects at each timestep.
 *
 * @author Noah Loewy
 */

public class LifeCell extends Cell<LifeCell> {

  /**
   * Minimum number of alive neighbors required for an alive cell to remain alive and avoid death by
   * underpopulation
   */
  private double aliveToAliveMin;
  /**
   * Maximum number of alive neighbors required for an alive cell to remain alive and avoid death by
   * overpopulation
   */
  private double aliveToAliveMax;
  /**
   * Minimum number of alive neighbors required for a dead cell to become alive by reproduction
   */
  private double deadToAliveMin;
  /**
   * Maximum number of alive neighbors required for a dead cell to become alive by reproduction
   */
  private double deadToAliveMax;

  /**
   * Constructs a Life Cell object for the Game of Life simulation
   *
   * @param initialState the integer representation of the cell's current state
   * @param row          the row the cell is positioned at as represented on a 2D coordinate grid
   * @param col          the column the cell is positioned at as represented on a 2D coordinate
   *                     grid
   * @param shapeType    the shape of a cell, as represented on a 2D coordinate grid
   * @param params       map of string parameter names to their values. Description of parameters
   *                     can be found at the declaration of the instance variables.
   */
  public LifeCell(int initialState, int row, int col, Shape shapeType,
      Map<String, Integer> params) {
    super(initialState, row, col, shapeType);
    aliveToAliveMin = params.get("aliveToAliveMin");
    aliveToAliveMax = params.get("aliveToAliveMax");
    deadToAliveMin = params.get("deadToAliveMin");
    deadToAliveMax = params.get("deadToAliveMax");
  }

  /**
   * Represents a timestep update for a LifeCell. Calls the proper helper function for transitioning
   * based on the state of the Life Cell being updated.
   */
  @Override
  public void transition() {
    int aliveNeighbors = countNeighborsInState(GameOfLifeSimulation.ALIVE);
    if (getCurrentState() == GameOfLifeSimulation.ALIVE) {
      handleAliveCell(aliveNeighbors);
    }
    if (getCurrentState() == GameOfLifeSimulation.DEAD) {
      handleDeadCell(aliveNeighbors);
    }
  }

  /**
   * Handles transition of alive cell in GameOfLifeSimulation. Alive cells with no less than
   * aliveToAliveMin and no more than aliveToAliveMax living neighbors will remain alive, whereas
   * all other alive cells will die
   */
  private void handleAliveCell(int aliveNeighbors) {
    if (aliveNeighbors >= aliveToAliveMin && aliveNeighbors <= aliveToAliveMax) {
      setNextState(GameOfLifeSimulation.ALIVE);
    } else {
      setNextState(GameOfLifeSimulation.DEAD);
    }
  }

  /**
   * Handles transition of dead cell in GameOfLifeSimulation. Dead cells with no less than
   * deadToAliveMin and no more than deadToAliveMax living neighbors will become alive, whereas all
   * other dead cells will remain dead
   */
  private void handleDeadCell(int aliveNeighbors) {
    if (aliveNeighbors >= deadToAliveMin && aliveNeighbors <= deadToAliveMax) {
      setNextState(GameOfLifeSimulation.ALIVE);
    } else {
      setNextState(GameOfLifeSimulation.DEAD);
    }
  }

  /**
   * @param params, new simulation parameters for the Game of Life Simulation
   */

  public void setParams(Map<String, Double> params) {
    aliveToAliveMin = (int) Math.floor(params.get("aliveToAliveMin"));
    aliveToAliveMax = (int) Math.floor(params.get("aliveToAliveMax"));
    deadToAliveMin = (int) Math.floor(params.get("deadToAliveMin"));
    deadToAliveMax = (int) Math.floor(params.get("deadToAliveMax"));
  }

  /**
   * @return angel or devil emoji, or empty string, depending on cell type
   */

  public String getText() {
    return switch (getCurrentState()) {
      case GameOfLifeSimulation.ALIVE -> "\uD83D\uDC7C";
      case GameOfLifeSimulation.DEAD -> "\uD83D\uDC7B";
      default -> "";
    };
  }
}

