import java.util.Scanner;

public class LoginSystem {
    private Admin admin;
    private Mahasiswa mahasiswa;

    public LoginSystem() {
        this.admin = new Admin();
        this.mahasiswa = new Mahasiswa();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Menampilkan menu pilihan
        System.out.println("Pilih jenis login:");
        System.out.println("1. Admin");
        System.out.println("2. Mahasiswa");
        System.out.print("Masukkan pilihan (1/2): ");
        int pilihan = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer

        if (pilihan == 1) { // Login sebagai Admin
            System.out.print("Masukkan username: ");
            String inputUsername = scanner.nextLine();
            System.out.print("Masukkan password: ");
            String inputPassword = scanner.nextLine();

            if (admin.login(inputUsername, inputPassword)) {
                System.out.println("Login Admin berhasil!");
            } else {
                System.out.println("Login gagal! Username atau password salah.");
            }
        } else if (pilihan == 2) { // Login sebagai Mahasiswa
            System.out.print("Masukkan nama: ");
            String inputNama = scanner.nextLine();
            System.out.print("Masukkan NIM: ");
            String inputNim = scanner.nextLine();

            if (mahasiswa.login(inputNama, inputNim)) {
                System.out.println("Login Mahasiswa berhasil!");
                mahasiswa.displayInfo(); // Panggil method yang benar
            } else {
                System.out.println("Login gagal! Nama atau NIM salah.");
            }
        } else {
            System.out.println("Pilihan tidak valid!");
        }

        scanner.close();
    }

    public static void main(String[] args) {
        LoginSystem system = new LoginSystem();
        system.run();
    }
}
