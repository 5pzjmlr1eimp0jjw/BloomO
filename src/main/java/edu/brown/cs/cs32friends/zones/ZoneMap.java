//package edu.brown.cs.cs32friends.zones;
//
//import edu.brown.cs.cs32friends.plant.Plant;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Set;
//
//public class ZoneMap {
//    HashMap<Zone, HashSet<Plant>> zoneMap = new HashMap<>();
//    Zone minZone;
//    Zone maxZone;
//
//    public ZoneMap(){}
//
//    public boolean hasPlant(){
//
//    }
//
//    public void addPlant(Plant plant){
//        Zone zone = plant.getZone();
//        if (!zoneMap.containsKey(zone)){
//            zoneMap.put(zone, new HashSet<>());
//        }
//        zoneMap.get(zone).add(plant);
//        if (zone.compareHardiness(minZone, 0)){
//            this.minZone = zone;
//        }
//        else if (zone.compareHardiness(maxZone, 1)){
//            this.maxZone = zone;
//        }
//    }
//
//    public void deletePlant(Plant plant){
//        Zone plantZone = plant.getZone();
//        if (zoneMap.containsKey(plantZone)){
//            if (this.maxZone.equals(plantZone)){
//                this.maxZone = null;
//                updateMax(plantZone);
//            }
//            if (this.minZone.equals(plantZone)){
//                this.minZone = null;
//                updateMin(plantZone);
//            }
//        }
//        Set<Plant> plantSet = zoneMap.get(plant.getZone());
//    }
//
//    public void updateMax(Zone prevMax){
//
//    }
//    public void updateMin(Zone prevMin){}
//}
