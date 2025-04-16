public class Mahasiswa {
    private String nama = "Erlin Mariska";
    private String nim = "202410370110514";

    // Metode untuk login mahasiswa
    public boolean login(String inputNama, String inputNim) {
        return inputNama.equals(nama) && inputNim.equals(nim);
    }

    // Menampilkan informasi mahasiswa setelah login berhasil
    public void displayInfo() {
        System.out.println("Nama: " + nama);
        System.out.println("NIM: " + nim);
    }
}