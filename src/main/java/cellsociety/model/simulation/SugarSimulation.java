package cellsociety.model.simulation;

import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.cell.SugarCell;
import cellsociety.model.core.shape.Shape;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.SimulationRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This cellular automata simulation represents the Predator-Prey simulation developed by Alexander
 * K Dewdney
 * <p>
 * author @noah loewy
 */

public class SugarSimulation extends Simulation<SugarCell> {

  private final int minVision;
  private final int maxVision;
  private final int minInitialSugar;
  private final int maxInitialSugar;
  private final int minMetabolism;
  private final int maxMetabolism;
  private final int numAgents;
  private final int growBackRate;

  /**
   * Initializes a SugarSimulation object
   *
   * @param row,       the number of rows in the 2-dimensional grid
   * @param col,       the number of columns in the 2-dimensional grid
   * @param hoodType,  the definition of neighbors
   * @param stateList, a list of the integer representation of each cells state in row major order
   * @param r,         a record of all parameters needed for Sugar Simulation. Description of
   *                   parameters can be found in the SugarCell class
   */


  public SugarSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      SimulationRecord r) {
    super(hoodType, r.gridType());
    minVision = (int) Math.floor(r.params().get("minVision"));
    maxVision = (int) Math.floor(r.params().get("maxVision"));
    minInitialSugar = (int) Math.floor(r.params().get("minInitialSugar"));
    maxInitialSugar = (int) Math.floor(r.params().get("maxInitialSugar"));
    minMetabolism = (int) Math.floor(r.params().get("minMetabolism"));
    maxMetabolism = (int) Math.floor(r.params().get("maxMetabolism"));
    growBackRate = (int) Math.floor(r.params().get("growBackRate"));
    numAgents = (int) Math.floor(r.params().get("numAgents"));
    createCellsAndGrid(row, col, stateList, getCellShape(r.cellShape()), hoodType);
  }


  /**
   * Creates list of WatorCells objects to be passed into grid
   *
   * @param col       number of columns in grid for simulation
   * @param stateList list of all cell's states in row major order
   * @param shape     Shape object representing the shape of the cell as represented on 2d plane
   * @return list of initialized WatorCells
   */

  @Override
  public List<SugarCell> cellMaker(int col, List<Integer> stateList,
      Shape shape) {
    List<SugarCell> cellList = new ArrayList<>();

    List<Boolean> agentList = new ArrayList<>(Collections.nCopies(stateList.size(), false));
    Collections.fill(agentList.subList(0, numAgents), true);
    Collections.shuffle(agentList);

    for (int i = 0; i < stateList.size(); i++) {
      Map<String, Integer> params = new HashMap<>();
      if (agentList.get(i)) {
        params.put("vision", new Random().nextInt(maxVision + 1 - minVision) + minVision);
        params.put("sugar",
            new Random().nextInt(maxInitialSugar + 1 - minInitialSugar) + minInitialSugar);
        params.put("metabolism",
            new Random().nextInt(maxMetabolism + 1 - minMetabolism) + minMetabolism);
      }
      params.put("growBackRate", growBackRate);
      cellList.add(new SugarCell(stateList.get(i), i / col, i % col, shape, params));
    }
    return cellList;
  }


  /**
   * Transition function for Sugar Scape. Iterates through each cell, starting with all the agents,
   * then empty cells, and calls the cell's transition function.
   */
  @Override
  public void transitionFunction() {
    for (int i = 0; i < 2; i++) {
      Iterator<SugarCell> gridIterator = getIterator();
      while (gridIterator.hasNext()) {
        SugarCell currentCell = gridIterator.next();
        if (i == 0 == currentCell.agentHere() && currentCell.getNextState() == Cell.PLACEHOLDER) {
          currentCell.transition();
        }
      }
    }
  }
}
