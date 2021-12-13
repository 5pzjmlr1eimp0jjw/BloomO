package edu.brown.cs.cs32friends.handlers;

import edu.brown.cs.cs32friends.kdtree.KDTree;
import edu.brown.cs.cs32friends.kdtree.coordinates.Coordinate;
import edu.brown.cs.cs32friends.kdtree.search.KDTreeSearch;
import edu.brown.cs.cs32friends.main.CommandHandler;
import edu.brown.cs.cs32friends.main.ParseCommands;
import edu.brown.cs.cs32friends.plant.Plant;
import edu.brown.cs.cs32friends.main.Main;

import edu.brown.cs.cs32friends.user.garden.Garden;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Funnels loaded plants into a KDTree for our Recommender
 */
public class RecommenderHandler implements CommandHandler {
    //plantMap is a HashMap that maps plant latin names to plant objects.
    private Map<String, Plant> plantMap = Main.getPlantMap();
    private Map<Integer, List<Coordinate<String>>> zoneMap = Main.getZoneMap();
    private Garden garden;

    public RecommenderHandler() {
    }
    //These are all fields that are needed to begin our nearest neighbor search.
    

    //This is a list of numRecs nearest neighbors to a given plant.
    ArrayList<String> nearestNeighbors = new ArrayList<>();

    /**
     * This is the handle method, which prints out a given amount of nearest neighbors. This method
     * will work for command line input of type:
     *      "plant_recommend numRecs hardinessZone plant_latin_name_one plant_latin_name_2"
     * Based off this input, our recommender will find the most similar plants to the given plant,
     * such that all the recommended plants will be able to be grown in the hardiness zone given.
     */
    @Override
    public void handle() {
        // KDTree<String> kdt;
        // int dim = 7;
        int numRecs = 0;
        int hardinessZone = 0;
        System.out.println("Setting up KDTree/Recommender...");
        //Split the command line input by space.
        String[] input = ParseCommands.getInputLine().split(" ");
        /*Find the amount of recommendations to generate, and the given hardiness zone to recommend plants from. */
        numRecs = Integer.parseInt(input[1]);
        hardinessZone = Integer.parseInt(input[2]);

        //Make sure there are at least 4 inputs. If there are not, we print out an error message.
        if (input.length <= 4) {
            System.out.println(
                    "Invalid input. Please give us input of type: \"plant_recommend numRecs hardinessZone plantLatinName\"");
            return;
        }
        garden = new Garden(hardinessZone);

        /*Find the given plant latin name. Because the plant name is separated by spaces, it will be split.
        Therefore, we have to rebuild it.*/
        for (int i = 3; i < input.length; i++) {
            String plantName = input[i].replace("_", " ");
            if(plantMap.containsKey(plantName)){
            garden.addPlant(plantMap.get(plantName));
            }
        }
        
        Coordinate avgGardenPlant = garden.getAveragePlantCoord();
        List<Coordinate<String>> possiblePlants = zoneMap.get(garden.zoneInt);
        for (Plant p : garden.getPlants()) {
            possiblePlants.remove(p);
        }

        KDTree<String> kdTree = new KDTree<>(numRecs, zoneMap.get(garden.zoneInt));

        KDTreeSearch kdTreeSearch = new KDTreeSearch();
        List<Coordinate<String>> naiveNeighbors = kdTreeSearch.getNearestNeighborsResult(numRecs, avgGardenPlant,
                kdTree.getRoot(), true);

        ArrayList<String> output = new ArrayList<>();
        for (Coordinate<String> c : naiveNeighbors) {
            output.add(c.getId());
        }
        
        this.nearestNeighbors = output;
    }

    


    public ArrayList<String> getNearestNeighbors(){
        return this.nearestNeighbors;
    }

}
