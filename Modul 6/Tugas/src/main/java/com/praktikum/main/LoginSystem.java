package main.java.com.praktikum.main;

import main.java.com.praktikum.data.DataStore;
import main.java.com.praktikum.users.User;

public class LoginSystem {
    public static User login(String username, String password, String role) {
        for (User user : DataStore.users) {
            if (user.getUsername().equalsIgnoreCase(username)
                    && user.getPassword().equals(password)
                    && user.getRole().equalsIgnoreCase(role)) {
                return user;
            }
        }
        return null;
    }
}
