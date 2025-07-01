package main.java.com.kantinumm.model;

public abstract class MenuItem {
    protected String kode;
    protected String namaMenu;
    protected int harga;
    protected String deskripsi;
    protected String imagePath;

    protected String kategori; // ✅ Tambahan untuk kategori (Makanan / Minuman)

    // Tambahan untuk laporan harian
    protected int jumlahTerjual = 0;

    public MenuItem(String kode, String namaMenu, int harga, String deskripsi, String imagePath, String kategori) {
        this.kode = kode;
        this.namaMenu = namaMenu;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.imagePath = imagePath;
        this.kategori = kategori; // ✅ Inisialisasi kategori
    }

    public String getKode() {
        return kode;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public int getHarga() {
        return harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getKategori() { // ✅ Getter kategori
        return kategori;
    }

    // Tambahan getter & updater jumlah terjual
    public int getJumlahTerjual() {
        return jumlahTerjual;
    }

    public void tambahTerjual(int jumlah) {
        this.jumlahTerjual += jumlah;
    }

    @Override
    public String toString() {
        return namaMenu + " - Rp." + harga;
    }
}
