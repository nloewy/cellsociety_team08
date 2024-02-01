package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.core.WatorCell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class WatorSimulation extends Simulation<WatorCell> {

  public static final int EMPTY = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;
  public static final int[] UPDATE_ORDER = {SHARK, FISH, EMPTY};
  private int fishAgeOfReproduction;
  private int sharkAgeOfReproduction;
  private int fishEnergyBoost;
  private int initialEnergy;

  public WatorSimulation(int row, int col, Neighborhood neighborhoodType, List<Integer> stateList) {
    super(row, col, neighborhoodType, stateList, (ind -> new WatorCell(stateList.get(ind),
        ind / row, ind % col, 2, 0)));
    fishAgeOfReproduction = 4;
    sharkAgeOfReproduction = 4;
    initialEnergy = 2;
    fishEnergyBoost = 4;
  }

  private List<WatorCell> getCellsOfState(List<WatorCell> neighbors, int state) {
    List<WatorCell> ret = new ArrayList<>();
    for (WatorCell cell : neighbors) {
      if (cell.getCurrentState() == state) {
        ret.add(cell);
      }
    }
    return ret;
  }

  //REFACTORING TIME
  public void transitionFunction() {
    for (int i = 0; i < 3; i++) {
      Iterator<WatorCell> gridIterator = getIterator();
      int index = -1;
      while (gridIterator.hasNext()) {
        index++;
        WatorCell currentCell = gridIterator.next();
        if (currentCell.getNextState() != Cell.PLACEHOLDER || (i == 0
            && currentCell.getCurrentState() != SHARK) || (i == 1
            && currentCell.getCurrentState() != FISH) || (i == 2
            && currentCell.getCurrentState() != EMPTY)) {
          continue;
        }
        List<WatorCell> neighbors = getNeighbors(currentCell);
        List<WatorCell> emptyNeighbors = getCellsOfState(neighbors, EMPTY);
        List<WatorCell> fishNeighbors = getCellsOfState(neighbors, FISH);
        if (currentCell.getCurrentState() == EMPTY) {
          if (currentCell.getNextState() == Cell.PLACEHOLDER) {
            currentCell.setNextState(EMPTY);
          }
        } else if (currentCell.getCurrentState() == FISH) {
          if (emptyNeighbors.isEmpty()) {
            System.out.println(index + " NOWHERE TO MOVE FISH");
            currentCell.setNextState(FISH);
            currentCell.setNextAge(currentCell.getAge() + 1);
          } else {
            Collections.shuffle(emptyNeighbors);
            WatorCell nextCell = emptyNeighbors.get(0);
            if (nextCell.getNextState() == SHARK || nextCell.getNextState() == FISH) {
              System.out.println(index + " TRIED TO MOVE FISH TO CELL ALREADY CALLED");
              currentCell.setNextState(FISH);
              currentCell.setNextAge(currentCell.getAge() + 1);
            } else {
              if (currentCell.getAge() >= fishAgeOfReproduction) {
                System.out.println(index + " FISH REPRODUCTION");
                currentCell.setNextAge(0);
                currentCell.setNextState(FISH);
                currentCell.setNextEnergy(-1);
                nextCell.setNextAge(0);
              } else {
                System.out.println(index + " MOVE FISH");
                currentCell.setNextState(EMPTY);
                currentCell.setNextAge(-1);
                currentCell.setNextEnergy(-1);
                System.out.println(nextCell.getLocation().toString());
                nextCell.setNextAge(currentCell.getAge() + 1);
              }
              nextCell.setNextState(FISH);
            }
          }
        } else {
          if (fishNeighbors.isEmpty() && emptyNeighbors.isEmpty()) {
            if (currentCell.getEnergy() > 1) {
              System.out.println(index + " CANT MOVE SHARK");
              currentCell.setNextState(SHARK);
              currentCell.setNextAge(currentCell.getAge() + 1);
              currentCell.setNextEnergy(currentCell.getEnergy() - 1);
            } else {
              System.out.println(index + " SHARK DEAD");
              currentCell.setNextState(EMPTY);
              currentCell.setNextAge(-1);
              currentCell.setNextEnergy(-1);
            }
          } else if (!fishNeighbors.isEmpty()) {
            Collections.shuffle(fishNeighbors);
            WatorCell nextCell = fishNeighbors.get(0);
            if (nextCell.getNextState() == SHARK || nextCell.getNextState() == FISH) {
              System.out.println(index + " SHARK TRIED TO MOVE BUT SHARK ALREADY THERE");
              currentCell.setNextState(SHARK);
              currentCell.setNextAge(currentCell.getAge() + 1);
              currentCell.setNextEnergy(currentCell.getEnergy() - 1);
            } else {
              nextCell.setNextState(SHARK);
              nextCell.setNextEnergy(currentCell.getEnergy() + fishEnergyBoost);
              nextCell.setNextAge(currentCell.getAge() + 1);
              if (currentCell.getAge() >= sharkAgeOfReproduction) {

                System.out.println(index + " SHARK EAT FISH + REPRODUCE");
                System.out.println(nextCell.getLocation().toString());

                currentCell.setNextAge(0);
                currentCell.setNextState(SHARK);
                currentCell.setNextEnergy(initialEnergy);
              } else {
                System.out.println(index + " SHARK EAT FISH");
                System.out.println(nextCell.getLocation().toString());

                currentCell.setNextState(EMPTY);
                currentCell.setNextAge(-1);
                currentCell.setNextEnergy(-1);
              }
            }
          } else {
            if (currentCell.getEnergy() <= 1) {
              System.out.println(index + " SHARK DIE");
              System.out.println(currentCell.getLocation().toString());
              currentCell.setNextState(EMPTY);
              currentCell.setNextAge(-1);
              currentCell.setNextEnergy(-1);
            } else {
              Collections.shuffle(emptyNeighbors);
              WatorCell nextCell = emptyNeighbors.get(0);
              if (nextCell.getNextState() == SHARK) {
                System.out.println(index + " SHARK CANT MOVE BC SHARK ALREADY THERE");
                currentCell.setNextState(SHARK);
                currentCell.setNextAge(currentCell.getAge() + 1);
                currentCell.setNextEnergy(currentCell.getEnergy() - 1);
              } else {
                nextCell.setNextState(SHARK);
                nextCell.setNextEnergy(currentCell.getEnergy() - 1);
                nextCell.setNextAge(currentCell.getAge() + 1);
                if (currentCell.getAge() >= sharkAgeOfReproduction) {
                  System.out.println(index + " SHARK REPRODUCE");
                  System.out.println(nextCell.getLocation().toString());
                  currentCell.setNextAge(0);
                  currentCell.setNextState(SHARK);
                  currentCell.setNextEnergy(initialEnergy);
                } else {
                  System.out.println(index + " SHARK MOVE TO EMPTY SPOT");
                  System.out.println(nextCell.getLocation().toString());
                  currentCell.setNextState(EMPTY);
                  currentCell.setNextAge(-1);
                  currentCell.setNextEnergy(-1);
                }
              }
            }
          }
        }
      }
    }
  }
}


