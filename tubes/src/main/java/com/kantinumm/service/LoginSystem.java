package main.java.com.kantinumm.service;

import main.java.com.kantinumm.model.*;
import java.util.*;

public class LoginSystem {
    private List<User> users = new ArrayList<>();

    public LoginSystem() {
        users.add(new Admin("erlin", "1234"));
        users.add(new Mahasiswa("mariska", "4321"));
    }

    public User login(String username, String password, String role) {
        for (User u : users) {
            if (u.getUsername().equals(username)
                    && u.getPassword().equals(password)
                    && u.getRole().equals(role)) {
                return u;
            }
        }
        return null;
    }
}
