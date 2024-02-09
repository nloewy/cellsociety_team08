package cellsociety.model.core.cell;


import cellsociety.model.core.shape.CellShape;

public class SchellingCell extends Cell {

  public SchellingCell(int initialState, int row, int col, CellShape shapeType) {
    super(initialState, row, col, shapeType);
  }


  @Override
  public void transition() {
  }

}

