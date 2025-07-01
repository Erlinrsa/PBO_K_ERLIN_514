package main.java.com.kantinumm.model;

public final class Makanan extends MenuItem {
    public Makanan(String kode, String namaMenu, int harga, String deskripsi, String imagePath) {
        super(kode, namaMenu, harga, deskripsi, imagePath, "Makanan"); // ✅ kategori "Makanan" ditambahkan
    }
}
