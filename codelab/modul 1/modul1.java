import java.time.LocalDate;
import java.util.Scanner;

public class modul1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Meminta input nama
        System.out.print("Masukkan nama: ");
        String nama = scanner.nextLine();

        // Meminta input jenis kelamin
        System.out.print("Masukkan jenis kelamin (P/L): ");
        char jenisKelamin = scanner.next().charAt(0);

        // Meminta input tahun lahir
        System.out.print("Masukkan tahun lahir: ");
        int tahunLahir = scanner.nextInt();

        // Menutup scanner
        scanner.close();

        // Menghitung umur
        int tahunSekarang = LocalDate.now().getYear();
        int umur = tahunSekarang - tahunLahir;

        // Menentukan jenis kelamin
        String jenisKelaminStr;
        if (jenisKelamin == 'L' || jenisKelamin == 'l') {
            jenisKelaminStr = "Laki-laki";
        } else if (jenisKelamin == 'P' || jenisKelamin == 'p') {
            jenisKelaminStr = "Perempuan";
        } else {
            jenisKelaminStr = "Tidak diketahui";
        }

        // Menampilkan output
        System.out.println("\nData Diri:");
        System.out.println("Nama          : " + nama);
        System.out.println("Jenis Kelamin : " + jenisKelaminStr);
        System.out.println("Umur          : " + umur + " tahun");
    }
}