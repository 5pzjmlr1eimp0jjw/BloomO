package edu.brown.cs.cs32friends.handlers;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.brown.cs.cs32friends.kdtree.coordinates.Coordinate;
import edu.brown.cs.cs32friends.plant.Plant;

/**
 * Parses the two CSV files (one with primarily String values and the other with primarily number
 * values) to create the following data structures:
 *      plantMap, that maps plant latin names (strings) to plant objects.
 *      zoneMap, which maps zones (integers) to a list of all plants that can be grown in that zone.
 *      coordList, which stores all plants as Coordinate points, to be used by the KDTree recommender.
 *      comToLat, which maps a plant's common name to its latin name.
 */
public class CSVParser {

    //Here are all the data structures that our parser will create.
    public Map<String, Plant> plantMap = new HashMap<>();
    public Map<Integer, List<Coordinate<String>>> zoneMap = new HashMap<>();
    static Map<String, String> comToLat = new HashMap<String, String>();

    /**
     * This constructor takes in two file paths. First it sets all possible keys in the zoneMap
     * with an empty hashset. It then creates the plantMap, where the objects
     * of the plant map are first given their string values (crucial for plant lookup) and then
     * updates all the plant objects with the double equivalent.
     * @param csvWithStrings
     * @param csvWithDoubles
     */
    public CSVParser(String csvWithStrings, String csvWithDoubles){
        for (int i = 1; i<14; i++){
            zoneMap.put(i, new ArrayList<>());
        }

        //In the space -1, all inconclusive zone will be placed here.
        zoneMap.put(-1, new ArrayList<>());

        //First, create the plant map and set the plant objects' string values.
        this.plantMap = parseStringCSV(csvWithStrings);
        //Finally, update the plant map by setting the plant objects' double values.
        this.updatePlantsWithDoubleCSV(csvWithDoubles);
    }

    /**
     * A getter method which returns the HashMap of common names to plant names.
     * @return A hashmap of a plant's common name to its latin name.
     */
    public static Map<String, String> getComToLat() {
        return comToLat;
    }

    /**
     * This method creates the map of latin names to plant objects, and sets all String field of
     * the plant objects.
     * @param pathToCSVStrings
     * @return the plant map.
     */
    public Map<String,Plant> parseStringCSV(String pathToCSVStrings) {
            String line = "";
            String splitBy = ",";
            File fd = new File(pathToCSVStrings);
            try {
                //Read through the CSV with strings line by line.
                BufferedReader br = new BufferedReader(new FileReader(fd));
                br.readLine(); // Skip the first line.

                while ((line = br.readLine()) != null) {

                    //Split the line into attributes.
                    String[] att = line.split(splitBy);

                    /*If the habit is not null or the hardiness range is not a dash, then
                      create the current plant and place it into the plant map. */
                    if (!(att[3].equals("null")) || !(att[5].equals("-"))){
                        Plant curPlant = new Plant(Integer.parseInt(att[0]), att[1], att[2],
                            att[3], att[4], att[5], att[6], att[7], att[8], att[9], att[10],
                            att[11], att[12]);
                        plantMap.put(att[1], curPlant);

                        //Iterate through the smallest to the largest zone of the current plant.
                        for (int currZone = curPlant.getMinZone();
                             currZone <= curPlant.getMaxZone(); currZone++) {

                            //Put all inconclusive hardinesses here.
                            if (currZone <= 0){
                                //Add the current plant to its corresponding set in the zone map.
                                zoneMap.get(-1).add(curPlant);
                            }

                            else{
                                zoneMap.get(currZone).add(curPlant);
                            }

                        }

                        //Get the different common names of the current plant.
                        String[] comNames = att[2].split("-");

                        /*If there is more than one common name, iterate through each of them and
                          put each one in the comToLat map, and setting its value to the plant's
                          latin name. */
                        if (comNames.length > 1) {
                            for (int i = 0; i < comNames.length; i++) {
                                comToLat.put(comNames[i].strip(), att[1]);
                            }
                        }

                        /*If there is only one common name, put it in the comToLat map directly. */
                        else if (comNames.length == 1){
                            comToLat.put(att[2], att[1]);
                        }
                    }
                }
                br.close();
            }

            catch (IOException e) {
                e.printStackTrace();
            }

        return plantMap;
    }

    /**
     * This method takes in a filepath to the CSV where many of the String fields have been
     * converted to doubles. It looks at the plant map and sets it to
     * @param pathToDoubleCSV
     */
    public void updatePlantsWithDoubleCSV(String pathToDoubleCSV){
        String line = "";
        String splitBy = ",";
        File fd = new File(pathToDoubleCSV);
        try {

            //Go through the Double CSV line by line.
            BufferedReader br = new BufferedReader(new FileReader(fd));
            br.readLine(); //Skip the first line.

            //While there are still lines in the CSV,
            while ((line = br.readLine()) != null) {

                //Split the line into an array of attributes.
                String[] att = line.split(splitBy); // use comma as separator

                /*If the plantMap has the current latin name stored, handle the case where the
                  iterized values in the line are null. */
                if (plantMap.containsKey(att[1])){

                    //Find the growth coordinate.
                    double growthCoord;
                    if (att[6].equals("null")){
                        growthCoord = 1;
                    }
                    else{
                        growthCoord = Double.parseDouble(att[6]);
                    }

                    //Find the soil coordinate.
                    double soilCoord;
                    if (att[7].equals("null")){
                        soilCoord = 1; //soil
                    }
                    else {
                        soilCoord = Double.parseDouble(att[7]);
                    }

                    //Find the shade coordinate.
                    double shadeCoord;
                    if (att[8].equals("null")){
                        shadeCoord = 0;
                    }
                    else{
                        shadeCoord = Double.parseDouble(att[8]);
                    }

                    //Find the moisture coordinate.
                    double moistureCoord;
                    if (att[9].equals("null")){
                        moistureCoord = 1;
                    }
                    else{
                        moistureCoord = Double.parseDouble(att[9]);
                    }

                    //Find the edible coordinate.
                    double edibleCoord;
                    if (att[10].equals("null")){
                        edibleCoord = 0;
                    }
                    else{
                        edibleCoord = Double.parseDouble(att[10]);
                    }

                    //Find the medical coordinate.
                    double medicalCoord;
                    if (att[11].equals("null")){
                        medicalCoord = 0;
                    }
                    else{
                        medicalCoord = Double.parseDouble(att[11]);
                    }

                    /*Get the current plant, and set its double fields to the attributes of the
                      line. */
                    Plant currPlant = plantMap.get(att[1]);
                    currPlant.setDs(Double.parseDouble(att[3]), growthCoord, soilCoord, shadeCoord,
                        moistureCoord, edibleCoord, medicalCoord);
                }
            }
            br.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is a getter method that returns the plant map.
     * @return
     */
    public Map<String, Plant> getPlantMap() {
        return plantMap;
    }

    public Map<Integer, List<Coordinate<String>>> getZoneMap() {
        return zoneMap;
    }
}
