package edu.brown.cs.cs32friends.kdtree;

import edu.brown.cs.cs32friends.kdtree.coordinates.Coordinate;
import edu.brown.cs.cs32friends.kdtree.search.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Class to create a KdTree of specified type ID.
 @param <T> Any type for the ID of the Coordinates that is specified
 when being used to construct a KdTree.
 */
public class KDTree<T> {
    private final int dimensions;
    private final Node<Coordinate<T>> root;

    /** Set the KdTree to have the passed coordinates with the specified dimensions each.
     @param dimensions the dimension number, from 1 to n where n is a positive integer.
     @param coordinates a list of Coordinates of any identifier/id type
     */
    public KDTree(int dimensions, List<Coordinate<T>> coordinates) {
        this.dimensions = dimensions;
        this.root = createKdTree(new ArrayList<>(coordinates));
    }

    /** Create a KDTree as a series of binary connected nodes with appropriate
     number of cutting layers.
     @param coordinates a list of Coordinates of any identifier/id type
     @return a Node of same type as passed when constructing the KdTree.
     */
    public Node<Coordinate<T>> createKdTree(List<Coordinate<T>> coordinates) {
        return createNextLayer(1, coordinates);
    }

    /** Create a KDTree layer at the passed cutting dimension out of the passed coordinates.
     @param currentDim the current cutting dimension.
     @param remainingCoordinates the coordinates which should be inserted as order specifies
     within this sub-KDTree.
     @return a Node of same type as passed when constructing the KdTree.
     */
    private Node<Coordinate<T>> createNextLayer(int currentDim,
                                                List<Coordinate<T>> remainingCoordinates) {
        if (remainingCoordinates.size() == 0) {
            return new Node<>(null, null, null);
        } else {
            Comparator<Coordinate<T>> byDimension
                    = Comparator.comparingDouble(coordinate -> coordinate.getCoordinateVal(currentDim));

            remainingCoordinates.sort(byDimension);










            int middleIndex = remainingCoordinates.size() / 2;

            // find median coordinate, coordinates lesser, coordinates greater than median
            Coordinate<T> medianCoordinate = remainingCoordinates.get(middleIndex);
            List<List<Coordinate<T>>> splitResult
                    = Utils.splitList(remainingCoordinates, middleIndex);

            List<Coordinate<T>> lesserCoordinates = splitResult.get(0);
            List<Coordinate<T>> greaterCoordinates = splitResult.get(1);

            // calculate next dimension
            int nextDimension;
            if (currentDim + 1 > dimensions) {
                nextDimension = 1;
            } else {
                nextDimension = currentDim + 1;
            }

            return new Node<>(
                    // value
                    medianCoordinate,
                    // recursive call to fill left subtree
                    createNextLayer(nextDimension, lesserCoordinates),
                    // recursive call to fill right subtree
                    createNextLayer(nextDimension, greaterCoordinates));
        }
    }

    /** Get the root of the KdTree created.
     @return a Node with value being a Coordinate of the specified ID type.
     */
    public Node<Coordinate<T>> getRoot() {
        return root;
    }

    /** Represent the KdTree as a String.
     @return a String representation of a KdTree.
     */
    @Override
    public String toString() {
        return "KdTree{"
                + "dimensions=" + dimensions
                + ", tree=" + root.toString()
                + '}';
    }

    /** Check if this KdTree is equal to the passed object.
     @param o Another object
     @return a Boolean ture/false if the objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KDTree<?> kdTree = (KDTree<?>) o;
        return dimensions == kdTree.dimensions && Objects.equals(root, kdTree.root);
    }

    /** Get a hashcode for a KdTree.
     @return an int representing the hash index.
     */
    @Override
    public int hashCode() {
        return Objects.hash(dimensions, root);
    }
//
//  public List<T> nearestNeighbors(int n, T target){
//    if (this.plantList.size()==n){
//      return this.plantList;
//    } else {
//      // currentNode.getDistance(target);
//
//      KDTree<T> greaterThanMedian= this.getLargerTree();
//      T nextNodeLarger = greaterThanMedian.currentNode;
//
//      KDTree<T> smallerThanMedian= this.getSmallerTree();
//      T nextNodeSmaller = smallerThanMedian.currentNode;
//
//      if (nextNodeLarger.getDistance(target) > nextNodeSmaller.getDistance(target)){
//        try{
//          return greaterThanMedian.nearestNeighbors(n, target);
//        } catch (Exception e){
//          ArrayList<T> nPlants = new ArrayList<>();
//          Double largestDist = null;
//          if (!greaterThanMedian.nearestNeighbors(n, target).isEmpty()){
//            for (T plant : greaterThanMedian.nearestNeighbors(n, target)){
//
//              if (largestDist.isNaN()) {
//                largestDist = plant.getDistance(target);
//              } else if (nPlants.size() < n) {
//                nPlants.add(plant);
//                if (plant.getDistance(target) >= largestDist){
//                  largestDist = plant.getDistance(target);
//                }
//              }
//
//
//              nPlants.add(plant);
//            }
//            for (T plant : this.getPlantList()){
//
//            }
//          }
//
//        }
//
//      } else {
//        return smallerThanMedian.nearestNeighbors(n, target);
//      }
//    }
//  }
}


//
///**
// * Comparator to sort by X dimension.
// *
// */
//class SortDimension implements Comparator<KDTreeNode> {
//
//  /**
//   * Compares two Stars to see which has a greater X coordinate.
//   *
//   * @return 1 if Star a's X Coordinate is greater than Star b's; 0 if the two
//   *         are equal; and -1 if Star a's X Coordinate is less than Star b's
//   */
//  private int dim;
//
//  SortDimension(int currentDimension) {
//    this.dim = currentDimension;
//  }
//
//  @Override
//  public int compare(KDTreeNode a, KDTreeNode b) {
//    double diff = a.getCoord(dim) - b.getCoord(dim);
//    if (diff > 0) {
//      return 1;
//    } else if (diff < 0) {
//      return -1;
//    } else {
//      return 0;
//    }
//  }
//}
//
