package cellsociety.model.core.cell;

import cellsociety.model.core.shape.CellShape;
import cellsociety.model.simulation.GameOfLifeSimulation;
import java.util.Map;

public class LifeCell extends Cell {

  private double aliveToAliveMin;
  private double aliveToAliveMax;
  private double deadToAliveMin;
  private double deadToAliveMax;

  public LifeCell(int initialState, int row, int col, CellShape shapeType,
      Map<String, Integer> params) {
    super(initialState, row, col, shapeType);
    aliveToAliveMin = params.get("aliveToAliveMin");
    aliveToAliveMax = params.get("aliveToAliveMax");
    deadToAliveMin = params.get("deadToAliveMin");
    deadToAliveMax = params.get("deadToAliveMax");
  }

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
   * Handles transition of alive cell in GameOfLifeSimulation. Dead cells with no less than
   * deadToAliveMin and no more than deadToAliveMax living neighbors will remain alive, whereas all
   * other alive cells will die
   */
  private void handleAliveCell(int aliveNeighbors) {
    if (aliveNeighbors >= aliveToAliveMin && aliveNeighbors <= aliveToAliveMax) {
      setNextState(GameOfLifeSimulation.ALIVE);
    } else {
      setNextState(GameOfLifeSimulation.DEAD);
    }
  }

  private void handleDeadCell(int aliveNeighbors) {
    if (aliveNeighbors >= deadToAliveMin && aliveNeighbors <= deadToAliveMax) {
      setNextState(GameOfLifeSimulation.ALIVE);
    } else {
      setNextState(GameOfLifeSimulation.DEAD);
    }
  }
}

