package main.java.com.kantinumm.model;

public class Mahasiswa extends User {
    public Mahasiswa(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "mahasiswa";
    }
}
