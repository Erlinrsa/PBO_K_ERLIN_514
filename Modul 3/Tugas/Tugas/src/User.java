public class User {
    private String nama;
    private String nim;

    // Constructor
    public User(String nama, String nim) {
        this.nama = nama;
        this.nim = nim;
    }

    // Getter
    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    // Setter
    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    // Method login (akan dioverride)
    public boolean login(String inputNama, String inputNim) {
        return false;
    }

    // Method displayInfo (akan dioverride)
    public void displayInfo() {
        System.out.println("Informasi pengguna.");
    }
}

