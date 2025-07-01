package main.java.com.kantinumm.service;

import main.java.com.kantinumm.model.*;
import java.util.*;

public class KantinSystem {
    private List<MenuItem> menuList = new ArrayList<>();
    private LaporanHarian laporan = new LaporanHarian();

    public KantinSystem() {
        // Makanan
        menuList.add(new Makanan("M001", "🍛 Nasi Gudeg", 10000, "Nasi dengan gudeg khas Yogya", "assets/nasi_gudeg.jpg"));
        menuList.add(new Makanan("M002", "🍲 Bakso", 12000, "Bola daging kuah kaldu segar", "assets/bakso.jpg"));
        menuList.add(new Makanan("M003", "🍜 Mie Ayam", 10000, "Mie dengan topping ayam", "assets/mie_ayam.jpg"));
        menuList.add(new Makanan("M004", "🥗 Nasi Pecel", 8000, "Nasi dengan sayur bumbu pecel", "assets/nasi_pecel.jpg"));
        menuList.add(new Makanan("M005", "🍵 Soto Ayam", 12000, "Sup ayam kuning khas Jawa", "assets/soto_ayam.jpg"));
        menuList.add(new Makanan("M006", "🥙 Gado-Gado", 10000, "Sayur campur bumbu kacang", "assets/gado_gado.jpg"));
        menuList.add(new Makanan("M007", "🍛 Nasi Rawon", 15000, "Nasi dengan sup daging kluwek", "assets/nasi_rawon.jpg"));
        menuList.add(new Makanan("M008", "🐟 Pecel Lele", 12000, "Lele goreng dengan sambal", "assets/pecel_lele.jpg"));
        menuList.add(new Makanan("M009", "🍲 Tahu Campur", 10000, "Tahu dengan kuah petis", "assets/tahu_campur.jpg"));
        menuList.add(new Makanan("M010", "🍢 Sate Ayam", 15000, "Ayam bakar bumbu kacang", "assets/sate_ayam.jpg"));
        menuList.add(new Makanan("M011", "🍳 Nasi Goreng", 12000, "Nasi goreng dengan telur", "assets/nasi_goreng.jpg"));
        menuList.add(new Makanan("M012", "🌯 Martabak Telur", 8000, "Martabak isi telur dan sayur", "assets/martabak_telur.jpg"));
        menuList.add(new Makanan("M013", "🍝 Mie Godog", 9000, "Mie rebus kuah sayuran", "assets/mie_godog.jpg"));
        menuList.add(new Makanan("M014", "🥩 Ayam Penyet", 13000, "Ayam goreng dengan sambal", "assets/ayam_penyet.jpg"));
        menuList.add(new Makanan("M015", "🍛 Nasi Liwet", 11000, "Nasi gurih dengan lauk", "assets/nasi_liwet.jpg"));
        menuList.add(new Makanan("M016", "🍲 Rujak Cingur", 10000, "Rujak khas Jawa Timur", "assets/rujak_cingur.jpg"));
        menuList.add(new Makanan("M017", "🥪 Roti Bakar", 7000, "Roti panggang dengan selai", "assets/roti_bakar.jpg"));
        menuList.add(new Makanan("M018", "🍜 Bakso Bakar", 14000, "Bakso panggang bumbu kacang", "assets/bakso_bakar.jpg"));

        // Minuman
        menuList.add(new Minuman("D001", "🧊 Es Teh Manis", 4000, "Teh dingin dengan gula", "assets/es_teh_manis.jpg"));
        menuList.add(new Minuman("D002", "🍊 Es Jeruk", 5000, "Jeruk peras dengan es", "assets/es_jeruk.jpg"));
        menuList.add(new Minuman("D003", "🥥 Es Kelapa Muda", 10000, "Kelapa muda segar dingin", "assets/es_kelapa_muda.jpg"));
        menuList.add(new Minuman("D004", "☕ Kopi Tubruk", 4000, "Kopi hitam tradisional", "assets/kopi_tubruk.jpg"));
        menuList.add(new Minuman("D005", "🥑 Jus Alpukat", 10000, "Alpukat blender dengan susu", "assets/jus_alpukat.jpg"));
        menuList.add(new Minuman("D006", "🍧 Es Campur", 10000, "Es dengan aneka topping", "assets/es_campur.jpg"));
        menuList.add(new Minuman("D007", "🫖 Wedang Jahe", 5000, "Minuman jahe hangat", "assets/wedang_jahe.jpg"));
        menuList.add(new Minuman("D008", "🍮 Es Dawet", 6000, "Minuman tradisional santan", "assets/es_dawet.jpg"));
        menuList.add(new Minuman("D009", "🍓 Jus Jambu", 8000, "Jambu biji segar blend", "assets/jus_jambu.jpg"));
        menuList.add(new Minuman("D010", "🍧 Es Cendol", 6000, "Cendol hijau dengan santan", "assets/es_cendol.jpg"));
        menuList.add(new Minuman("D011", "🥤 Es Susu", 7000, "Susu segar dengan es batu", "assets/es_susu.jpg"));
        menuList.add(new Minuman("D012", "🍋 Es Lemon Tea", 6000, "Teh lemon segar dingin", "assets/es_lemon_tea.jpg"));
        menuList.add(new Minuman("D013", "🥛 Susu Jahe", 5000, "Susu hangat dengan jahe", "assets/susu_jahe.jpg"));
        menuList.add(new Minuman("D014", "🧋 Es Teh Tarik", 7000, "Teh susu kental manis", "assets/es_teh_tarik.jpg"));
        menuList.add(new Minuman("D015", "🥤 Jus Melon", 9000, "Melon segar blender", "assets/jus_melon.jpg"));
        menuList.add(new Minuman("D016", "🍹 Es Buah", 8000, "Campuran buah segar", "assets/es_buah.jpg"));
        menuList.add(new Minuman("D017", "🥤 Jus Sirsak", 10000, "Sirsak segar blender", "assets/jus_sirsak.jpg"));
        menuList.add(new Minuman("D018", "🧊 Es Kopi Susu", 8000, "Kopi dengan susu dan es", "assets/es_kopi_susu.jpg"));
    }

    public List<MenuItem> getMenuList() {
        return menuList;
    }

    public List<MenuItem> getMenuListByKategori(String kategori) {
        if (kategori.equalsIgnoreCase("Makanan")) {
            return menuList.stream().filter(m -> m instanceof Makanan).toList();
        } else {
            return menuList.stream().filter(m -> m instanceof Minuman).toList();
        }
    }

    public Transaksi buatTransaksi(User user, List<MenuItem> pesanan) throws Exception {
        if (user instanceof Mahasiswa) {
            int makanan = 0;
            int minuman = 0;

            for (MenuItem item : pesanan) {
                if (item.getKategori().equalsIgnoreCase("Makanan")) {
                    makanan++;
                } else if (item.getKategori().equalsIgnoreCase("Minuman")) {
                    minuman++;
                }
            }

            if (makanan > 3 || minuman > 3) {
                throw new Exception("TRANSACTION FAILED - LIMIT EXCEEDED");
            }
        }

        Transaksi t = new Transaksi(user, pesanan);
        laporan.tambahTransaksi(t);
        return t;
    }

    public LaporanHarian getLaporan() {
        return laporan;
    }
}
