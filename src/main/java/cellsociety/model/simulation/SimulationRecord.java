package cellsociety.model.simulation;


import java.util.Map;

public record SimulationRecord(Map<String, Double> params, String gridType, String cellShape){}
