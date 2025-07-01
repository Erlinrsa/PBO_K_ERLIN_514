package main.java.com.praktikum.data;

public class Item {
    private String namaBarang;
    private int jumlah;
    private String deskripsi;
    private String status;

    // Constructor untuk tambah stok atau input dari Admin
    public Item(String namaBarang, int jumlah) {
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
        this.deskripsi = "Pusat Stok";
        this.status = "Tersedia";
    }

    // Constructor tambahan untuk keperluan lain (misalnya laporan)
    public Item(String namaBarang, String deskripsi, String status) {
        this.namaBarang = namaBarang;
        this.deskripsi = deskripsi;
        this.status = status;
        this.jumlah = 0;
    }

    // === Getter & Setter Lengkap ===
    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
