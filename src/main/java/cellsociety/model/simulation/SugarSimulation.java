package cellsociety.model.simulation;

import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.cell.SugarCell;
import cellsociety.model.core.shape.Shape;
import cellsociety.model.neighborhood.Neighborhood;
import cellsociety.model.simulation.Records.SugarRecord;
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


  public static final int MAX_AVAILABLE_SUGAR = 4;
  private final int minVision;
  private final int maxVision;

  private final int minInitialSugar;
  private final int maxInitialSugar;
  private final int minMetabolism;
  private final int maxMetabolism;

  private int growBackRate;
  private final int numAgents;

  public SugarSimulation(int row, int col, Neighborhood hoodType, List<Integer> stateList,
      SugarRecord r) {
    super(hoodType, r.gridType());
    minVision = r.minVision();
    maxVision = r.maxVision();
    minInitialSugar = r.minInitialSugar();
    maxInitialSugar = r.maxInitialSugar();
    minMetabolism = r.minMetabolism();
    maxMetabolism = r.maxMetabolism();
    growBackRate = r.growBackRate();
    numAgents = r.numAgents();

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
   * Transition function for Wator World. Iterates through each cell, starting with all the sharks,
   * then fish, then empty, and calls the cell's transition function.
   */
  @Override
  public void transitionFunction() {
    for (int i = 0; i < 2; i++) {
      Iterator<SugarCell> gridIterator = getIterator();
      while (gridIterator.hasNext()) {
        SugarCell currentCell = gridIterator.next();
        if (i == 0 && currentCell.agentHere()) {
          if (currentCell.getNextState() == Cell.PLACEHOLDER) {
            currentCell.transition();
          }
        } else if (i == 1 && currentCell.agentHere() == false) {
          if (currentCell.getNextState() == Cell.PLACEHOLDER) {
            currentCell.transition();
          }
        }
      }
    }
    }
}