package edu.brown.cs.cs32friends.kdtree.coordinates;

import java.util.List;

public interface Coordinate<String> {
    /**
     * Get the coordinate value at the dimension requested.
     *
     * @param dim the dimension number, from 1 to n where n is a positive integer.
     * @return a Double value, any real number.
     */
    Double getCoordinateVal(int dim);

    /**
     * Get the ID.
     *
     * @return id of type with which the Coordinate was created.
     */
    String getId();

    /**
     * Get all coordinate values of the Coordinate.
     *
     * @return a List of Double; i.e., a list of as many real numbers as there are dimensions.
     */
    List<Double> getCoordinates();

    /**
     * Represent the Coordinate as a String.
     *
     * @return a String representation of a Coordinate.
     */
    java.lang.String toString();

    String getID();
}
