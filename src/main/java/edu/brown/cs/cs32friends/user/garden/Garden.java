package edu.brown.cs.cs32friends.user.garden;

import edu.brown.cs.cs32friends.kdtree.coordinates.Coordinate;
import edu.brown.cs.cs32friends.plant.Plant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the Garden class, which has a set of plants, and a single hardiness zone.
 * Users can create these gardens in their profile and generate recommendations for
 * specific gardens.
 */
public class Garden {
    public int zoneInt;
    Set<Plant> plants = new HashSet<>();

    /**
     * This constructor sets the name and the hardiness zone of the garden.
     * @param zoneInt
     */
    public Garden(int zoneInt){
        this.zoneInt = zoneInt;
    }

    /**
     * This function adds a plant to the garden.
     * @param plant
     */
    public void addPlant(Plant plant){
        this.plants.add(plant);
    }

    /**
     * This function removes a plant from the garden.
     * @param plant
     */
    public void removePlant(Plant plant){
        if (plants.contains(plant)){
            plants.remove(plant);
        }
        else{
            System.out.println("Error: this plant is not in the current garden.");
        }
    }

    /**
     * This getter method returns the garden's list of plants.
     * @return
     */
    public Set<Plant> getPlants(){
        return this.plants;
    }

    /**
     * This method resets the hardiness zone of a user to a new zone.
     * @param newZone
     */
    public void setZone(int newZone){
        zoneInt = newZone;
    }

    /**
     * This getter method returns the zone int of the garden.
     * @return
     */
    public int getZone(){
        return zoneInt;
    }

    /**
     * This method returns the average plant coordinates of the plants in the
     * garden.
     * @return Coordinate representing an average plant.
     */
    public Coordinate<String> getAveragePlantCoord(){
        double[] averageCoords = {0, 0, 0, 0, 0, 0, 0};
        int currDim = 0;

        //Iterate through all the dimensions of a plant.
        while (currDim < 7) {
            /* For all plants in the garden, add the current plant's value for the current dimension
              and divide by the number of plants in the garden. */
            for (Plant p: plants){
                averageCoords[currDim] += p.getDs()[currDim]/plants.size();
            }
            currDim += 1;
        }

        return new Plant(averageCoords);
    }

}
