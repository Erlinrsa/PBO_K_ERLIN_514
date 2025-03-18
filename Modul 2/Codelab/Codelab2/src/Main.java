// 1. Mendefinisikan kelas RekeningBank
class RekeningBank {
    // 2. Deklarasi atribut
    String nomorRekening;
    String namaPemilik;
    double saldo;

    // 3. Konstruktor untuk menginisialisasi objek
    RekeningBank(String nomorRekening, String namaPemilik, double saldo) {
        this.nomorRekening = nomorRekening;
        this.namaPemilik = namaPemilik;
        this.saldo = saldo;
    }

    // 4. Metode untuk menampilkan informasi rekening
    void tampilkanInfo() {
        System.out.println("Nomor Rekening: " + nomorRekening);
        System.out.println("Nama Pemilik: " + namaPemilik);
        System.out.println("Saldo: Rp" + saldo);
        System.out.println();
    }

    // 5. Metode untuk menyetor uang
    void setorUang(double jumlah) {
        saldo += jumlah;
        System.out.println(namaPemilik + " menyetor Rp" + jumlah + ". Saldo sekarang: Rp" + saldo);
    }

    // 6. Metode untuk menarik uang
    void tarikUang(double jumlah) {
        if (jumlah > saldo) {
            System.out.println(namaPemilik + " menarik Rp" + jumlah + ". (Gagal, Saldo tidak mencukupi) Saldo saat ini: Rp" + saldo);
        } else {
            saldo -= jumlah;
            System.out.println(namaPemilik + " menarik Rp" + jumlah + ". (Berhasil) Saldo sekarang: Rp" + saldo);
        }
    }
}

// 7. Kelas Main untuk menjalankan program
public class Main {
    public static void main(String[] args) {
        // 8. Membuat objek rekening1 dan rekening2
        RekeningBank rekening1 = new RekeningBank("20231307311129", "Maharani", 500000);
        RekeningBank rekening2 = new RekeningBank("20231307311307", "Amelia", 1000000);

        // 9. Menampilkan informasi awal
        rekening1.tampilkanInfo();
        rekening2.tampilkanInfo();

        // 10. Melakukan transaksi setor uang
        rekening1.setorUang(200000);
        rekening2.setorUang(500000);

        // 11. Melakukan transaksi tarik uang
        rekening1.tarikUang(800000); // Gagal karena saldo tidak cukup
        rekening2.tarikUang(300000); // Berhasil

        // 12. Menampilkan informasi akhir
        System.out.println();
        rekening1.tampilkanInfo();
        rekening2.tampilkanInfo();
    }
}