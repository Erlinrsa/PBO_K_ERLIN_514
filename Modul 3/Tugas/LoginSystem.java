import java.util.Scanner;

public class LoginSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Admin admin = new Admin("admin", "514", "admin", "1234");
        Mahasiswa mahasiswa = new Mahasiswa("Erlin Mariska", "202410370110514");

        System.out.println("===== SISTEM LOGIN =====");
        System.out.println("1. Login sebagai Admin");
        System.out.println("2. Login sebagai Mahasiswa");
        System.out.print("Pilih (1/2): ");
        int pilihan = scanner.nextInt();
        scanner.nextLine(); // bersihkan newline

        if (pilihan == 1) {
            System.out.print("Username: ");
            String inputUser = scanner.nextLine();
            System.out.print("Password: ");
            String inputPass = scanner.nextLine();

            if (admin.login(inputUser, inputPass)) {
                admin.displayInfo();
            } else {
                System.out.println("Login Admin gagal!");
            }

        } else if (pilihan == 2) {
            System.out.print("Nama: ");
            String inputNama = scanner.nextLine();
            System.out.print("NIM: ");
            String inputNim = scanner.nextLine();

            if (mahasiswa.login(inputNama, inputNim)) {
                mahasiswa.displayInfo();
            } else {
                System.out.println("Login Mahasiswa gagal!");
            }

        } else {
            System.out.println("Pilihan tidak valid.");
        }

        scanner.close();
    }
}