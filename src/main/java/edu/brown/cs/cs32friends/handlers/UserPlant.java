package edu.brown.cs.cs32friends.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.brown.cs.cs32friends.handlers.*;
import edu.brown.cs.cs32friends.main.ParseCommands;

import java.sql.*;
import java.util.ArrayList;

public class UserPlant {

    private static Connection conn = null;
    private PreparedStatement prep;
    private String _userID;
    private String _plantToAdd;

    public UserPlant() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String urlToDB = "jdbc:sqlite:" + "src/main/java/edu/brown/cs/cs32friends/handlers/users_plant.sqlite3";
        try {
            conn = DriverManager.getConnection(urlToDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement stat = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        // this line loads the driver manager class, and must be
// present for everything else to work properly
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String urlToDB = "jdbc:sqlite:" + "users_plant.sqlite3";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(urlToDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
// these two lines tell the database to enforce foreign keys during operations, and should be present
        try {
            Statement stat = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUserID(String userID){

        _userID = userID;
        //System.out.println(_userID);
    }

    public void setPlantToAdd(String plantToAdd){ _plantToAdd = plantToAdd; }

    public ArrayList<String> findPlants() throws SQLException {
        PreparedStatement prep;
        prep = conn.prepareStatement(
                "SELECT * FROM users_plant WHERE user_id = ?;");
        prep.setString(1, _userID);
//        prep = conn.prepareStatement(
//              "SELECT * FROM users_plant;");
        ResultSet rs = prep.executeQuery();

        ArrayList<String> plants = new ArrayList<>();

        while (rs.next()) {
            String plantName = rs.getString(2);
           // System.out.println("plantname: "+plantName);
            plants.add(plantName);
        }
        rs.close();
        prep.close();

        return plants;
    }

    public String toJson(){
        try {
            ArrayList<String> usersPlants = this.findPlants();
            String jsonString = new Gson().toJson(usersPlants);
            return jsonString;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean exists(){
        boolean exist = false;
        try {
            prep = conn.prepareStatement(
                    "SELECT COUNT(*) FROM users_plant WHERE user_id = ? AND plant_name = ?;");
            prep.setString(1, _userID);
            prep.setString(2, _plantToAdd);
            ResultSet rs = prep.executeQuery();
            int n = 0;
            if ( rs.next() ) {
                n = rs.getInt(1);
            }
            if ( n > 0 ) {
                   exist = true;
                }
            rs.close();
            prep.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public void addPlant(){
        if (!exists()) {
            try {
                prep = conn.prepareStatement(
                        "INSERT INTO users_plant VALUES (?, ?);");
                prep.setString(1, _userID);
                prep.setString(2, _plantToAdd);
                prep.addBatch();
                prep.executeBatch();
                prep.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("plant already exists");
        }
    }

    public void deletePlant() {
        if (exists()) {
            try {
                prep = conn.prepareStatement(
                        "DELETE FROM users_plant WHERE user_id = ? AND plant_name = ?;");
                prep.setString(1, _userID);
                prep.setString(2, _plantToAdd);
                prep.addBatch();
                prep.executeBatch();
                prep.close();
                System.out.println("deleted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("plant doesn't exist");
        }
    }

    public String cutString(String string){
        return string.split("\\(")[1].replace(")","");
    }
    
    public ArrayList<String> getRecommendations(String zone) {
        RecommenderHandler recommender = new RecommenderHandler();
        ArrayList<String> usrPlants;
        ArrayList<String> output = new ArrayList<>();
        try {
            usrPlants = this.findPlants();
            String inputToRec = "";
            for (int i = 0; i < usrPlants.size(); i++) {
                String eachPlant = cutString(usrPlants.get(i));
                eachPlant = eachPlant.replace(" ", "_");
                if (i == 0) {
                    inputToRec += eachPlant;
                } else {
                    inputToRec += " " + eachPlant;
                }
            }
            String inputToRepl = "plant_recommend 3 " +zone+ " " + inputToRec;
            ParseCommands.setInputLine(inputToRepl);
            recommender.handle();
            output = recommender.getNearestNeighbors();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }


    public static void main(String[] args) {
        UserPlant userPlantHandler = new UserPlant();
        userPlantHandler.setUserID("google-oauth2|115394477434624685524");
        userPlantHandler.setPlantToAdd("Tulip");
        userPlantHandler.deletePlant();
        System.out.println(userPlantHandler.exists());
    }

}
