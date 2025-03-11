import java.util.Scanner;

public class modul1
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input dari pengguna
        System.out.print("Masukkan nama Anda: ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan usia Anda: ");
        int usia = scanner.nextInt();

        // Output hasil
        System.out.println("\n=== Output Page B ===");
        System.out.println("Halo, " + nama + "!");
        System.out.println("Usia Anda: " + usia + " tahun");

        scanner.close();
    }
}
