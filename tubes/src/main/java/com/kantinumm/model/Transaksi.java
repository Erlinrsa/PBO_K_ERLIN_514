package main.java.com.kantinumm.model;

import java.time.LocalDateTime;
import java.util.List;

public class Transaksi {
    private User user;
    private List<MenuItem> pesanan;
    private double totalHarga;
    private double diskon;
    private LocalDateTime waktu;

    // Tambahan field untuk kebutuhan dashboard/statistik
    private String namaMenu;
    private double harga;
    private int jumlah;
    private LocalDateTime tanggal;

    // Constructor utama untuk sistem nyata
    public Transaksi(User user, List<MenuItem> pesanan) {
        this.user = user;
        this.pesanan = pesanan;
        this.totalHarga = hitungTotal();
        this.diskon = hitungDiskon();
        this.waktu = LocalDateTime.now();
    }

    // Constructor tambahan untuk dummy sample data di dashboard
    public Transaksi() {
        // kosongkan, bisa diisi lewat setter
    }

    // Constructor opsional praktis
    public Transaksi(String namaMenu, double harga, int jumlah, LocalDateTime tanggal) {
        this.namaMenu = namaMenu;
        this.harga = harga;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.totalHarga = harga * jumlah;
        this.diskon = 0;
    }

    private double hitungTotal() {
        return pesanan.stream().mapToDouble(MenuItem::getHarga).sum();
    }

    private double hitungDiskon() {
        if (totalHarga > 50000) {
            return totalHarga * 0.10;
        }
        return 0;
    }

    public String generateStruk() {
        StringBuilder sb = new StringBuilder("\n=== STRUK PEMBELIAN ===\n");
        sb.append("User : ").append(user.getUsername()).append("\n");
        for (MenuItem item : pesanan) {
            sb.append(item.getNamaMenu()).append(" - Rp").append((int)item.getHarga()).append("\n");
        }
        sb.append("Total: Rp").append((int)totalHarga).append("\n");
        sb.append("Diskon: Rp").append((int)diskon).append("\n");
        sb.append("Bayar: Rp").append((int)(totalHarga - diskon)).append("\n");
        sb.append("Waktu: ").append(waktu).append("\n");
        return sb.toString();
    }

    // Getter penting untuk laporan
    public double getTotalHarga() {
        return totalHarga;
    }

    public double getDiskon() {
        return diskon;
    }

    public List<MenuItem> getListMenu() {
        return pesanan;
    }

    public User getUser() {
        return user;
    }

    // Setter & Getter untuk dummy sample (agar tidak error di dashboard)
    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public double getHarga() {
        return harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }
}
