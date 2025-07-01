package main.java.com.praktikum.users;

public class Mahasiswa extends User {
    private String nama;

    public Mahasiswa(String username, String password, String nama) {
        super(username, password, "mahasiswa");
        this.nama = nama;
    }

    public Mahasiswa(String username, String password) {
        super(username, password, "mahasiswa");
        this.nama = "Tidak diketahui";
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return getUsername(); // NIM dianggap sebagai username
    }
}