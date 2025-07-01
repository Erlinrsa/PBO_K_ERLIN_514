package main.java.com.praktikum.data;

import main.java.com.praktikum.users.*;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<User> users = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();

    static {
        // Sesuai yang kamu minta:
        users.add(new Admin("erlin", "1234"));
        users.add(new Mahasiswa("mariska", "4321", "Mariska"));

        // Data barang dummy
        items.add(new Item("Buku", 10));
        items.add(new Item("Pulpen", 25));
    }
}
