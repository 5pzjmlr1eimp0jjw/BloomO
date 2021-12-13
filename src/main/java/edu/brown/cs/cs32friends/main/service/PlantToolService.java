package edu.brown.cs.cs32friends.main.service;
import edu.brown.cs.cs32friends.user.User;

import java.util.ArrayList;
import java.util.LinkedList;

public class PlantToolService {

    private UserAccounts userAccounts = new UserAccounts();
    private ArrayList<User> users = new ArrayList<>();

    public PlantToolService(){}


    public void addAccount(User a) { this.userAccounts.addAccount(a); }

    public User getAccount(String username, String password) {
        return userAccounts.getAccount(username, password);
    }

    private User findUser(String username) {
        for (User u : this.users) {
            if (u.checkUsername(username)) return u;
        }
        return null;
    }

    public String login(String name, String pwd) {
        User customer = findUser(name);

        if (customer != null) {
            if (User.checkPassword(pwd)) return "Welcome";
            else return "Try Again";
        }
        return "Customer not found";
    }

}

