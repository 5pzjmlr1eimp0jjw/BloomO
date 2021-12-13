package edu.brown.cs.cs32friends.main.service;

import edu.brown.cs.cs32friends.user.User;

public class UserAccounts {

    public void addAccount(User u){}

    public User getAccount(String username, String password){
        return new User(username, password);
    }
}
