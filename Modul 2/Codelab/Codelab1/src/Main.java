// Kelas Hewan
class Hewan {
    // Atribut
    String nama;
    String jenis;
    String suara;

    // Konstruktor
    Hewan(String nama, String jenis, String suara) {
        this.nama = nama;
        this.jenis = jenis;
        this.suara = suara;
    }

    // Metode untuk menampilkan informasi hewan
    void tampilkanInfo() {
        System.out.println("Nama: " + nama);
        System.out.println("Jenis: " + jenis);
        System.out.println("Suara: " + suara);
        System.out.println();
    }
}

// Kelas Main
public class Main {
    public static void main(String[] args) {
        // Membuat objek hewan1 dan hewan2
        Hewan hewan1 = new Hewan("Kucing", "Mamalia", "Miaww");
        Hewan hewan2 = new Hewan("Ayam", "Unggas", "Kukuruyuk");

        // Menampilkan informasi kedua hewan
        hewan1.tampilkanInfo();
        hewan2.tampilkanInfo();
    }
}