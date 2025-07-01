package main.java.com.praktikum.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import main.java.com.praktikum.data.Item;
import main.java.com.praktikum.users.Mahasiswa;
import main.java.com.praktikum.users.User;

public class AdminDashboard extends VBox {

    private final ObservableList<Item> laporanList = FXCollections.observableArrayList();
    private final ObservableList<Mahasiswa> mahasiswaList = FXCollections.observableArrayList();

    public AdminDashboard(MainApp mainApp, User adminUser) {
        Label header = new Label("Halo, Administrator " + adminUser.getUsername());

        // ------------------------- Tabel Laporan Barang ------------------------
        TableView<Item> barangTable = new TableView<>();
        TableColumn<Item, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        namaCol.setPrefWidth(140);

        TableColumn<Item, String> lokasiCol = new TableColumn<>("Lokasi");
        lokasiCol.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        lokasiCol.setPrefWidth(140);

        TableColumn<Item, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(120);

        barangTable.getColumns().addAll(namaCol, lokasiCol, statusCol);
        barangTable.setItems(laporanList);

        Button tandaiClaimed = new Button("Tandai Claimed");
        tandaiClaimed.setOnAction(e -> {
            Item selected = barangTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setStatus("Claimed");
                barangTable.refresh();
            }
        });

        VBox barangBox = new VBox(10, new Label("Laporan Barang"), barangTable, tandaiClaimed);
        barangBox.setPadding(new Insets(10));
        barangBox.setPrefWidth(400);

        // ------------------------- Tabel Data Mahasiswa ------------------------
        TableView<Mahasiswa> mhsTable = new TableView<>();
        TableColumn<Mahasiswa, String> namaMhsCol = new TableColumn<>("Nama");
        namaMhsCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        namaMhsCol.setPrefWidth(140);

        TableColumn<Mahasiswa, String> nimCol = new TableColumn<>("NIM");
        nimCol.setCellValueFactory(new PropertyValueFactory<>("nim"));
        nimCol.setPrefWidth(180);

        mhsTable.getColumns().addAll(namaMhsCol, nimCol);
        mhsTable.setItems(mahasiswaList);

        TextField namaInput = new TextField();
        namaInput.setPromptText("Nama Mahasiswa");
        TextField nimInput = new TextField();
        nimInput.setPromptText("NIM");

        Button tambahBtn = new Button("Tambah");
        tambahBtn.setOnAction(e -> {
            String nama = namaInput.getText().trim();
            String nim = nimInput.getText().trim();

            if (!nama.isEmpty() && !nim.isEmpty()) {
                Mahasiswa mhsBaru = new Mahasiswa(nim, "password123", nama);
                mahasiswaList.add(mhsBaru);
                namaInput.clear();
                nimInput.clear();
            }
        });

        Button hapusBtn = new Button("Hapus");
        hapusBtn.setOnAction(e -> {
            String nim = nimInput.getText().trim();
            if (!nim.isEmpty()) {
                mahasiswaList.removeIf(m -> m.getNim().equals(nim));
                nimInput.clear();
            }
        });

        HBox aksiBox = new HBox(5, namaInput, nimInput, tambahBtn, hapusBtn);
        VBox mhsBox = new VBox(10, new Label("Data Mahasiswa"), mhsTable, aksiBox);
        mhsBox.setPadding(new Insets(10));
        mhsBox.setPrefWidth(400);

        // ------------------------- Layout Utama ------------------------
        HBox centerBox = new HBox(20, barangBox, mhsBox);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> mainApp.setScene(new LoginPane(mainApp)));

        setSpacing(10);
        setPadding(new Insets(20));
        getChildren().addAll(header, centerBox, logoutBtn);

        // ------------------------ Dummy Data ------------------------
        laporanList.addAll(
                new Item("HP", "Meja A15", "Claimed"),
                new Item("Dompet", "Meja B-19", "Claimed"),
                new Item("Wdaef", "ev", "Claimed"),
                new Item("HP", "Meja A-01", "Reported")
        );
        mahasiswaList.addAll(
                new Mahasiswa("202310730311006", "pw", "Ken Aryo"),
                new Mahasiswa("202310730311010", "pw", "Wira Yudha"),
                new Mahasiswa("202310730311321", "pw", "Nisrina"),
                new Mahasiswa("202310730311129", "pw", "Herdiana")
        );
    }
}