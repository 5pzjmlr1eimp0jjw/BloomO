package edu.brown.cs.cs32friends.user;

import edu.brown.cs.cs32friends.user.garden.Garden;
import java.util.ArrayList;

/**
 * This is the User class.
 */
public class User {
    String username;
    private static String password;
    ArrayList<Garden> gardens;

    public User(String userName, String password){
        this.username = userName;
        User.password = password;
    }

    public void addGarden(int zone){
        this.gardens.add(new Garden(zone));
    }

    public void deleteGarden(Garden garden){
        if (gardens.contains(garden)){
            this.gardens.remove(garden);
        }
        else{
            System.out.println("Error: this garden cannot be deleted.");
        }
    }

    public boolean checkUsername(String username){
        return this.username.equals(username);
    }

    public static boolean checkPassword(String p){
        return password.equals(p);
    }

}
