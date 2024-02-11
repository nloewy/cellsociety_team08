package cellsociety.model.simulation;

import cellsociety.model.neighborhood.Neighborhood;
import java.util.Map;

public class Records {

  public record GameOfLifeRecord(int aliveToAliveMin, int aliveToAliveMax, int deadToAliveMin,
                                 int deadToAliveMax, String gridType, String cellShape) {

  }

  public record PercolationRecord(int percolatedNeighbors, String gridType, String cellShape) {

  }

  public record FireRecord(int neighborsToIgnite, double probTreeIgnites, double probTreeCreated,
                           String gridType, String cellShape) {

  }

  public record SchellingRecord(double proportionNeededToStay, String gridType, String cellShape) {

  }

  public record WatorRecord(int fishAgeOfReproduction, int sharkAgeOfReproduction,
                            int initialEnergy,
                            int energyBoost, String gridType, String cellShape) {

  }
  public record FallingSandRecord(String gridType, String cellShape) {

  }

  public record SugarRecord(int minVision, int maxVision, int minInitialSugar, int maxInitialSugar,
                            int minMetabolism,
                            int maxMetabolism, int growBackRate, int numAgents, String gridType,
                            String cellShape){}
}


