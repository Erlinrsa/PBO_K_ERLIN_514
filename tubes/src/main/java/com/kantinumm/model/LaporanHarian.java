package main.java.com.kantinumm.model;

import java.util.*;

public class LaporanHarian {
    private final List<Transaksi> transaksiList = new ArrayList<>();

    public void tambahTransaksi(Transaksi t) {
        transaksiList.add(t);
    }

    public int totalTransaksi() {
        return transaksiList.size();
    }

    public double totalPendapatan() {
        return transaksiList.stream()
                .mapToDouble(t -> t.getTotalHarga() - t.getDiskon())
                .sum();
    }

    public double totalDiskon() {
        return transaksiList.stream()
                .mapToDouble(Transaksi::getDiskon)
                .sum();
    }

    // Kembalikan daftar semua menu favorit + jumlah terjual
    public Map<MenuItem, Integer> hitungMenuFavorit() {
        Map<MenuItem, Integer> map = new HashMap<>();
        for (Transaksi t : transaksiList) {
            for (MenuItem m : t.getListMenu()) {
                map.put(m, map.getOrDefault(m, 0) + 1);
            }
        }
        return map;
    }

    // Hanya nama menu favorit utama (string), opsional
    public String menuFavorit() {
        return hitungMenuFavorit().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey().getNamaMenu())
                .orElse("- Tidak ada");
    }

    public List<Transaksi> getTransaksiList() {
        return transaksiList;
    }
}
