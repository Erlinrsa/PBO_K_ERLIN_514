package com.praktikum.users;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.praktikum.actions.MahasiswaActions;
import com.praktikum.data.Item;
import com.praktikum.main.LoginSystem;

public class Mahasiswa extends User implements MahasiswaActions {

    public Mahasiswa(String nama, String nim) {
        super(nama, nim);
    }
    Scanner scanner = new Scanner(System.in);

    public void reportItem(){
        System.out.print("Nama item: ");
        String namaItem = scanner.nextLine();
        System.out.print("Deskripsi item: ");
        String deskripsiItem = scanner.nextLine();
        System.out.print("Lokasi Terakhir/Ditemukan: ");
        String lokasiItem = scanner.nextLine();
        Item barangHilang = new Item(namaItem, deskripsiItem, lokasiItem);
        LoginSystem.barangDilaporkan(barangHilang);
        System.out.println("Informasi barang telah tersimpan.");
    }

    public void viewReportedItems(){
        if (LoginSystem.barangDilaporkan.isEmpty()) {
            System.out.println("Belum ada laporan barang.");
            return;
        }
        System.out.println("Daftar barang dilaporkan:");
        for (Item barang : LoginSystem.barangDilaporkan) {
            if (barang.getStatus().equals("Reported")) {
                System.out.println("- Nama: " + barang.getNamaItem() + " | Deskripsi: " + barang.getDeskripsiItem() + " | Lokasi: " + barang.getLokasi());
            }
        }
    }

    @Override
    public void displayAppMenu(){
        int pilihan = 0;
        do {
            System.out.println();
            System.out.println("Menu");
            System.out.println("1. Laporkan Barang Temuan/Hilang");
            System.out.println("2. Lihat Daftar Laporan");
            System.out.println("0. Logout");
            System.out.print("Masukkan Pilihan Anda: ");
            try {
                pilihan = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Input harus berupa angka!");
                scanner.nextLine();
            }

            if (pilihan == 1){
                reportItem();
            } else if (pilihan == 2) {
                viewReportedItems();
            } else if (pilihan == 0){
                System.out.println("Logging out...");
            } else {
                System.out.println("Pilihan Tidak Valid!");
            }
        } while (pilihan != 0);
    }

    @Override
    public boolean login(String inputNama, String inputNim) {
        return getNama().equals(inputNama) && getNim().equals(inputNim);
    }

    @Override
    public void displayInfo() {
        System.out.println("Login Mahasiswa berhasil!");
        System.out.println("Nama: " + getNama());
        System.out.println("NIM: " + getNim());
    }
}