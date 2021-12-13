package edu.brown.cs.cs32friends.plant;

import com.google.gson.JsonObject;
import edu.brown.cs.cs32friends.kdtree.coordinates.Coordinate;
import edu.brown.cs.cs32friends.zones.Zone;
import java.io.*;
import java.util.*;

/**
 * This is the Plant class, which stores all important information for
 * a given plant, which we gather from the CSV data (scraped from
 * https://pfaf.org/user/Default.aspx).
 *
 */
public class Plant implements Coordinate<String> {

    /*All these are fields that will be set by the CSVParser when it goes through the CSV file
      with strings.*/
    public int ID;
    public String LatinName;
    public String CommonName;
    public String Habit;
    public String Height;
    public String Hardiness;
    public String Growth;
    public String Soil;
    public String Shade;
    public String Moisture;
    public String Edible;
    public String Medical;
    public String Other;
    public String Image;

    /*All these are the fields that will be set by the CSVParser when it goes through the CSV file
      with ints. */
    public double habitD;
    public double growthD;
    public double soilD;
    public double shadeD;
    public double moistureD;
    public double edibleD;
    public double medicalD;

    //These fields divide the zone range into a min and max hardiness.
    public int maxHardiness = -1;
    public int minHardiness = -1;


    /**
     * This is one of the plant constructors. It sets all the fields of the plant object from the
     * csv with mostly string fields.
     * @param id
     * @param LatinName
     * @param CommonName
     * @param Habit
     * @param Height
     * @param Hardiness
     * @param Growth
     * @param Soil
     * @param Shade
     * @param Moisture
     * @param Edible
     * @param Medical
     * @param Other
     */
    public Plant(int id, String LatinName, String CommonName, String Habit,
                 String Height, String Hardiness, String Growth, String Soil,
                 String Shade, String Moisture, String Edible, String Medical,
                 String Other) {

        this.ID = id;
        this.LatinName = LatinName;
        this.CommonName = CommonName;
        this.Habit = Habit;
        this.Height = Height;
        this.Hardiness = Hardiness;
        this.Growth = Growth;
        this.Soil = Soil;
        this.Shade = Shade;
        this.Moisture = Moisture;
        this.Edible = Edible;
        this.Medical = Medical;
        this.Other = Other;
        this.findMinAndMaxHardiness();
    }

    /**
     * This constructor sets all the fields of the fields of the plant object from the csv with
     * mostly string fields.
     * @param coordinates
     */
    public Plant(double[] coordinates){
        this.habitD = coordinates[0];
        this.growthD = coordinates[1];
        this.soilD = coordinates[2];
        this.shadeD = coordinates[3];
        this.moistureD = coordinates[4];
        this.edibleD = coordinates[5];
        this.medicalD = coordinates[6];
    }


    /**
     * This constructor finds the min and max hardiness of the plant by breaking up the Hardiness
     * field into two (splitting by the "-" symbol).
     */
    public void findMinAndMaxHardiness(){
        String[] hardinesses = Hardiness.split("-");
        //If there are two hardinesses, set them to the min and max hardiness.
        if (hardinesses.length == 2){
            this.minHardiness = Integer.parseInt(hardinesses[0]);
            this.maxHardiness = Integer.parseInt(hardinesses[1]);
        }
    }


    /**
     * This method sets all the fields that are doubles, which are used in the KDTree
     * recommendation.
     * @param habitD
     * @param growthD
     * @param soilD
     * @param shadeD
     * @param moistureD
     * @param edibleD
     * @param medicalD
     */
    public void setDs(double habitD, double growthD, double soilD, double shadeD,
                      double moistureD, double edibleD, double medicalD) {
        this.habitD = habitD;
        this.growthD = growthD;
        this.soilD = soilD;
        this.shadeD = shadeD;
        this.moistureD = moistureD;
        this.edibleD = edibleD;
        this.medicalD = medicalD;
    }

    /**
     * This getter method returns the latin name of the plant.
     * @return
     */
    public String getID2() {
        return this.LatinName;
    }


    /**
     * This method returns an array of all the relevant coordinates to the KDTree.
     * @return
     */
    public double[] getDs(){
        return new double[]{this.habitD, this.growthD, this.soilD, this.shadeD, this.moistureD, this.edibleD, this.medicalD};
    }


    /**
     * This getter method returns the minimum hardiness that this plant can withstand.
     * @return
     */
    public int getMinZone(){
        return this.minHardiness;
    }

    /**
     * This getter method returns the maximum hardiness that this plant can tolerate.
     * @return
     */
    public int getMaxZone(){
        return this.maxHardiness;
    }


    /**
     * This getter method returns the zone object of the plant.
     * @return
     */
    public Zone getZone(){
        Zone zone = new Zone();
        zone.setZone(this.Hardiness);
        return zone;
    }

    /**
     * This getter method creates a Json object that will be sent to front ent to show information
     * the plant.
     * @return
     */
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("ID", ID);
        json.addProperty("LatinName", LatinName);
        json.addProperty("CommonName", CommonName);
        json.addProperty("Habit", Habit);
        json.addProperty("Height", Height);
        json.addProperty("Hardiness", Hardiness);
        json.addProperty("Growth", Growth);
        json.addProperty("Soil", Soil);
        json.addProperty("Shade", Shade);
        json.addProperty("Moisture", Moisture);
        json.addProperty("Edible", Edible);
        json.addProperty("Medical", Medical);
        json.addProperty("Other", Other);
        json.addProperty("Image", Image);
        return json;
    }

    /**
     * This method returns the associated coordinate value given a dimension.
     * Dimension 1 ->   Value: habit double
     *           2 ->          growth double
     *           3 ->          soil double
     *           4 ->          shade double
     *           5 ->          moisture double
     *           6 ->          edible double
     *           7 ->          medical double
     * @param dim the dimension number, from 1 to n where n is a positive integer.
     * @return
     */
    @Override
    public Double getCoordinateVal(int dim) {
        if (dim>=0 && dim<this.getDs().length){
            return this.getDs()[dim];
        }
        else{
            return this.getDs()[dim%7 + 1];
        }

    }

    /**
     * This method returns the latin name (the id) of the plant.
     * @return
     */
    @Override
    public String getId() {
        return this.LatinName;
    }

    /**
     * This method returns the coordinates of the plant as a list of doubles.
     * @return
     */
    @Override
    public List<Double> getCoordinates() {
        return new ArrayList<Double>(
                Arrays.asList(this.habitD, this.growthD, this.soilD, this.shadeD, this.moistureD, this.edibleD, this.medicalD)
        );
    }

    @Override
    public String getID() {
        return null;
    }
}

