package edu.brown.cs.cs32friends.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.cs32friends.kdtree.coordinates.Coordinate;
import edu.brown.cs.cs32friends.main.CommandHandler;
import edu.brown.cs.cs32friends.plant.Plant;


/**
 * Loads plants from a CSV into plantMap hashmap
 */
public class PlantLoaderHandler implements CommandHandler {
    
    Map<String, Plant> plantMap = new HashMap<String, Plant>();
    Map<Integer, List<Coordinate<String>>> zoneMap = new HashMap<Integer, List<Coordinate<String>>>();
    
    public Map<String, Plant> getPlantList() {
        return plantMap;
    }

    public Map<Integer, List<Coordinate<String>>> getZoneList() {
        return zoneMap;
    }

    public PlantLoaderHandler(){}

    @Override
    public void handle() {
        CSVParser csvParser = new CSVParser("plantD/plant_data_full_s", "plantD/plant_data_full_d");
        System.out.println("Starting plants Loading:");
        plantMap = csvParser.getPlantMap();
        zoneMap = csvParser.getZoneMap();
    }

}
