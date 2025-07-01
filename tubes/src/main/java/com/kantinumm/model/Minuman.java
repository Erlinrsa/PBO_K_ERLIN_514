package main.java.com.kantinumm.model;

public final class Minuman extends MenuItem {
    public Minuman(String kode, String namaMenu, int harga, String deskripsi, String imagePath) {
        super(kode, namaMenu, harga, deskripsi, imagePath, "Minuman"); // ✅ kategori "Minuman"
    }
}
