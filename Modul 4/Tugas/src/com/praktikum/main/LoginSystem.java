package com.praktikum.main;

import com.praktikum.users.*;
import java.util.Scanner;

public class LoginSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = null;

        // Dummy data
        Admin admin = new Admin("erlin", "1234");
        Mahasiswa mahasiswa = new Mahasiswa("mariska", "4321");

        System.out.println("=== Sistem Login ===");
        System.out.print("Username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Password: ");
        String inputPassword = scanner.nextLine();

        if (admin.login(inputUsername, inputPassword)) {
            user = admin;
        } else if (mahasiswa.login(inputUsername, inputPassword)) {
            user = mahasiswa;
        } else {
            System.out.println("Login gagal. Username atau password salah.");
            return;
        }

        // Polymorphism in action
        user.displayAppMenu();
    }
}
