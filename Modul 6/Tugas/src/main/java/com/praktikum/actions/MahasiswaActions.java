package main.java.com.praktikum.actions;

import main.java.com.praktikum.data.DataStore;
import main.java.com.praktikum.data.Item;

public class MahasiswaActions {
    public static boolean ambilBarang(String namaBarang, int jumlah) {
        for (Item item : DataStore.items) {
            if (item.getNamaBarang().equalsIgnoreCase(namaBarang)) {
                if (item.getJumlah() >= jumlah) {
                    item.setJumlah(item.getJumlah() - jumlah);
                    return true;
                }
            }
        }
        return false; // Barang tidak cukup atau tidak ditemukan
    }
}
