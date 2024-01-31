package cellsociety.Model;

import java.util.*;

public class WatorSimulation extends Simulation<WatorCell> {

  public static final int EMPTY = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;

  public WatorSimulation(int row, int col, Neighborhood neighborhoodType, List<Integer> stateList) {
    super(row, col, neighborhoodType, stateList,  (ind -> new WatorCell(stateList.get(ind),
        ind / row, ind % col, 0, 0)));
  }

  private List<WatorCell> getCellsFromStates(List<Integer> stateList, int row, int col){
    List<WatorCell> newStateList = new ArrayList<>();
    for (int i = 0; i < stateList.size(); i++) {
      newStateList.add(new WatorCell(stateList.get(i), i / row, i % col, 0, 0));
    }
    return newStateList;
  }
  @Override
  public void transitionFunction() {
    Iterator<WatorCell> gridIterator = getIterator();
    while (gridIterator.hasNext()) {
      WatorCell currentCell = gridIterator.next();
      int whyWontItWork = currentCell.getEnergy();
      return;
    }
  }
}
