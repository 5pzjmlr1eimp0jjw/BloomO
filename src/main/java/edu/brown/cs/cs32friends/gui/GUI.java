package edu.brown.cs.cs32friends.gui;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import edu.brown.cs.cs32friends.handlers.*;
import edu.brown.cs.cs32friends.main.Main;
import edu.brown.cs.cs32friends.main.ParseCommands;
import edu.brown.cs.cs32friends.plant.Plant;
import edu.brown.cs.cs32friends.zones.Zone;
import spark.Request;
import spark.Response;
import spark.Route;

public class GUI {

    public AddUserPlantGUI addUserPlantGUI;
    public UserPlantGUI userPlantGUI;
    public ZoneLookUpGUI zoneLookupGUI;
    public PlantLookupGUI plantLookupGUI;
    public ZoneAddGUI zoneAddGUI;
    public LatNameLookupGUI latNameLookupGUI;
    //public ImageUploadGUI imageUploadGUI;
   
    public GUI() {
        this.addUserPlantGUI = new AddUserPlantGUI();
        this.userPlantGUI = new UserPlantGUI();
        this.zoneLookupGUI = new ZoneLookUpGUI();
        this.plantLookupGUI = new PlantLookupGUI();
        this.zoneAddGUI = new ZoneAddGUI();
        this.latNameLookupGUI = new LatNameLookupGUI();
        //this.imageUploadGUI = new ImageUploadGUI();
    }

    private class UserPlantGUI implements Route {

        @Override
        public Object handle(Request request, Response response) throws Exception {
            UserPlant userPlant = new UserPlant();
            String json = request.body();
            Gson gson = new Gson();
            JsonObject JSON = gson.fromJson(json, JsonObject.class);
            boolean isRecommendation = JSON.get("isRecommendation").getAsBoolean();
            String zone = "";
            zone = JSON.get("zone").getAsString();
            System.out.println("zone: " + zone);
            System.out.println("HELLO");
            
            String userID = JSON.get("userID").getAsString();
            userPlant.setUserID(userID);    
            if (isRecommendation) {
                
                ArrayList<String> recommendations = userPlant.getRecommendations("2");
                ArrayList<ArrayList<String>> outputToFront = new ArrayList<ArrayList<String>>();
                Map<String, Plant> db = Main.getPlantMap();
                for (String recommendation : recommendations) {
                    ArrayList<String> eachOutput = new ArrayList<String>();
                    Plant curPlant = db.get(recommendation);
                    String outputString = "";
                    String comName = curPlant.CommonName;
                    String[] comNameSplit = comName.split("-");
                    for (int i = 0; i < comNameSplit.length; i++) {
                        if (i == 0) {
                            outputString += comNameSplit[i];
                        } else {
                            outputString += " or " + comNameSplit[i];
                        }
                    }
                    String concat = outputString + "(" + recommendation + ")";
                    eachOutput.add(concat);
                    outputToFront.add(eachOutput); 
                }
                String jsonString = new Gson().toJson(outputToFront);
                return gson.toJson(ImmutableMap.of("recommendations", jsonString));
            }

               
            
            return gson.toJson(ImmutableMap.of("usersPlant", userPlant.toJson()));
        }
    }


    private class AddUserPlantGUI implements Route {

        @Override
        public Object handle(Request request, Response response) throws Exception {
            UserPlant userPlant = new UserPlant();
            String json = request.body();
            Gson gson = new Gson();
            JsonObject JSON = gson.fromJson(json, JsonObject.class);
            Boolean firstRender = JSON.get("firstRender").getAsBoolean();
            Boolean add = JSON.get("add").getAsBoolean();
            String userID = JSON.get("userID").getAsString();
            String plantToAdd = JSON.get("plantName").getAsString();
            userPlant.setUserID(userID);
            userPlant.setPlantToAdd(plantToAdd);
            if (firstRender == true) {
                boolean exist = userPlant.exists();
                System.out.println(userID);
                System.out.println(plantToAdd);
                System.out.println(exist);
                return gson.toJson(exist);
            } else {
                if (add == true) {
                    userPlant.addPlant();
                } else {
                    userPlant.deletePlant();
                }
            }
            return new JsonObject();
        }
    }

    private class ZoneLookUpGUI implements Route {

        @Override
        public Object handle(Request request, Response response) throws Exception {
            ZipcodeHandler zcHandler = new ZipcodeHandler();
            String json = request.body();
            Gson gson = new Gson();
            JsonObject JSON = gson.fromJson(json, JsonObject.class);
            String zipcode = JSON.get("zipcode").getAsString();
            String inputLine = "plant_zone " + zipcode;
            ParseCommands.setInputLine(inputLine);
            zcHandler.handle();
            Zone curZone = zcHandler.getCurZone();
            Map outPutMap;
            if (curZone == null) {
                outPutMap = ImmutableMap.of("zone", "null");
            }
            else {
                outPutMap = ImmutableMap.of("zone", curZone.toJson());
            }
            return gson.toJson(outPutMap);
        }
    }

    private class PlantLookupGUI implements Route {

        @Override
        public Object handle(Request request, Response response) throws Exception {

            String json = request.body();
            Gson gson = new Gson();
            JsonObject JSON = gson.fromJson(json, JsonObject.class);
            String plantName = JSON.get("plant_Name").getAsString();
            Map<String, Plant> db = Main.getPlantMap();
            Plant curPlant = db.get(plantName);
            PlantImageHandlerRobust plantImageHandler = new PlantImageHandlerRobust();
            PlantImageHandler plantImageHandler2 = new PlantImageHandler();
            ParseCommands.setInputLine("input_plant_image_robust " + plantName);
            plantImageHandler.handle();
            curPlant.Image = plantImageHandler.getCurUrl();
            if (curPlant.Image.equals("")) {
                ParseCommands.setInputLine("input_plant_image " + plantName);
                plantImageHandler2.handle();
                curPlant.Image = plantImageHandler2.getCurUrl();
            }

            while (curPlant.Image.equals("")) {
                String comName = curPlant.CommonName;
                String[] comNameSplit = comName.split("-");
                for (int i = 0; i < comNameSplit.length; i++) {
                    String curComName = comNameSplit[i].trim();
                    ParseCommands.setInputLine("input_plant_image_robust " + curComName);
                    plantImageHandler.handle();
                    if (!plantImageHandler.getCurUrl().equals("")) {
                        curPlant.Image = plantImageHandler.getCurUrl();
                        break;
                    }
                }
            }
            
            while (curPlant.Image.equals("")) {
                String comName2 = curPlant.CommonName;
                String[] comNameSplit = comName2.split("-");
                for (int i = 0; i < comNameSplit.length; i++) {
                    String curComName = comNameSplit[i].trim();
                    ParseCommands.setInputLine("input_plant_image " + curComName);
                    plantImageHandler2.handle();
                    if (!plantImageHandler2.getCurUrl().equals("")) {
                        curPlant.Image = plantImageHandler2.getCurUrl();
                        break;
                    }
                }
            }

            Map outputMap = ImmutableMap.of("plant", curPlant.toJson());
            return gson.toJson(outputMap);
        }

    }

    private class LatNameLookupGUI implements Route {

        @Override
        public Object handle(Request request, Response response) throws Exception {
            String json = request.body();
            Gson gson = new Gson();
            JsonObject JSON = gson.fromJson(json, JsonObject.class);
            String comName = JSON.get("plant_ComName").getAsString();
            String latName = "";
            Map<String, String> db = CSVParser.getComToLat();
            Set<String> keySet = db.keySet();
            Map outputMap = null;
            for (String key : keySet) {
                if (key.equals(comName)) {
                    latName = db.get(key);
                    outputMap = ImmutableMap.of("latName", latName);
                } else {
                    continue;
                }
            }
            if (latName.equals("")) {
                outputMap = ImmutableMap.of("latName", "null");
            }
            return gson.toJson(outputMap);
        }
    }
    
    private class ZoneAddGUI implements Route {

        @Override
        public Object handle(Request request, Response response) throws Exception {
            String json = request.body();
            Gson gson = new Gson();
            Map<Integer, Zone> db = ZipcodeHandler.getZones();
            JsonObject JSON = gson.fromJson(json, JsonObject.class);
            String zipcodeTBA = JSON.get("zipcodeTBA").getAsString();
            String zoneNumberTBA = JSON.get("zoneNumberTBA").getAsString();
            Zone newZone = new Zone();
            newZone.zone = zoneNumberTBA;
            newZone.setZoneSpecificInfo();
            db.put(Integer.parseInt(zipcodeTBA), newZone);
            Map outputMap = ImmutableMap.of("zone", "success");
            return gson.toJson(outputMap);
        }
    }
}
