package main.java.com.praktikum.actions;

import main.java.com.praktikum.data.DataStore;
import main.java.com.praktikum.data.Item;

public class AdminActions {
    public static void tambahStok(String namaBarang, int jumlah) {
        for (Item item : DataStore.items) {
            if (item.getNamaBarang().equalsIgnoreCase(namaBarang)) {
                item.setJumlah(item.getJumlah() + jumlah);
                return;
            }
        }
        // Tambah item baru jika belum ada
        DataStore.items.add(new Item(namaBarang, jumlah));
    }
}
