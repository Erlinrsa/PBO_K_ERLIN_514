package main.java.com.praktikum.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import main.java.com.praktikum.data.Item;
import main.java.com.praktikum.users.Mahasiswa;

import java.util.ArrayList;

public class MahasiswaDashboard extends VBox {

    private TableView<Item> table;
    private ObservableList<Item> laporanList = FXCollections.observableArrayList();
    private static ArrayList<String> lokasiList = new ArrayList<>();

    public MahasiswaDashboard(MainApp mainApp, Mahasiswa mhs) {
        Label welcome = new Label("Selamat datang, " + mhs.getNama());
        Label instruksi = new Label("Laporkan Barang Hilang/Temuan");

        TextField namaField = new TextField();
        namaField.setPromptText("Nama Barang");
        namaField.setPrefWidth(130);

        TextField deskripsiField = new TextField();
        deskripsiField.setPromptText("Deskripsi Barang");
        deskripsiField.setPrefWidth(180);

        TextField lokasiField = new TextField();
        lokasiField.setPromptText("Lokasi Ditemukan");
        lokasiField.setPrefWidth(130);

        Button laporButton = new Button("Laporkan");
        laporButton.setPrefWidth(90); // supaya nggak ketutup
        laporButton.setMinHeight(30);

        HBox inputBox = new HBox(10, namaField, deskripsiField, lokasiField, laporButton);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        inputBox.setPadding(new Insets(5, 0, 5, 0)); // biar lega

        // TABEL (tetap 3 kolom: Nama, Lokasi, Kosong)
        table = new TableView<>();

        TableColumn<Item, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        namaCol.setPrefWidth(150);

        TableColumn<Item, String> lokasiCol = new TableColumn<>("Lokasi");
        lokasiCol.setCellValueFactory(cell -> {
            int index = laporanList.indexOf(cell.getValue());
            String lokasi = (index >= 0 && index < lokasiList.size()) ? lokasiList.get(index) : "";
            return new javafx.beans.property.SimpleStringProperty(lokasi);
        });
        lokasiCol.setPrefWidth(150);

        TableColumn<Item, String> kosongCol = new TableColumn<>("");
        kosongCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(""));
        kosongCol.setPrefWidth(150);

        table.getColumns().addAll(namaCol, lokasiCol, kosongCol);
        table.setItems(laporanList);
        table.setPlaceholder(new Label("Belum ada laporan."));

        laporButton.setOnAction(e -> {
            String nama = namaField.getText();
            String deskripsi = deskripsiField.getText();
            String lokasi = lokasiField.getText();

            if (!nama.isEmpty() && !lokasi.isEmpty()) {
                laporanList.add(new Item(nama, deskripsi.hashCode())); // deskripsi disimpan, walau tak ditampilkan
                lokasiList.add(lokasi);
                namaField.clear();
                deskripsiField.clear();
                lokasiField.clear();
            }
        });

        Button logout = new Button("Logout");
        logout.setOnAction(e -> mainApp.setScene(new LoginPane(mainApp)));

        setPadding(new Insets(20));
        setSpacing(10);
        getChildren().addAll(
                welcome,
                instruksi,
                inputBox,
                new Label("Daftar Laporan Anda"),
                table,
                logout
        );
    }
}
