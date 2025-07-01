package main.java.com.kantinumm.gui;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.com.kantinumm.model.MenuItem;
import main.java.com.kantinumm.model.Transaksi;
import main.java.com.kantinumm.service.KantinSystem;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AdminDashboard extends StackPane {
    private final KantinSystem kantinSystem;
    private final Stage stage;
    private final NumberFormat currencyFormat;
    private List<Transaksi> transaksiList;
    private ObservableList<MenuTableRow> menuTableData;

    // UI Components
    private Label notificationLabel;
    private VBox notificationBox;
    private Label lastUpdateLabel;
    private BarChart<String, Number> barChart;
    private GridPane metricsGrid;
    private TableView<MenuTableRow> menuTable;
    private boolean isEditMode = false;

    // Metrics labels for real-time updates
    private Label totalTransaksiValue;
    private Label totalPendapatanValue;
    private Label totalMenuValue;
    private Label rataRataValue;

    public AdminDashboard(Stage stage, KantinSystem kantinSystem) {
        this.stage = stage;
        this.kantinSystem = kantinSystem;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        this.currencyFormat.setMaximumFractionDigits(0);

        // PENTING: Inisialisasi data sebelum membuat UI
        initializeAllData();

        BorderPane mainContent = createMainContent();
        createNotificationSystem();

        this.getChildren().addAll(mainContent, notificationBox);
        StackPane.setAlignment(notificationBox, Pos.TOP_RIGHT);
        StackPane.setMargin(notificationBox, new Insets(20));
    }

    // Method baru untuk inisialisasi semua data
    private void initializeAllData() {
        // Inisialisasi transaksi data
        initializeSampleData();

        // Inisialisasi menu table data
        initializeMenuTableData();
    }

    private void initializeSampleData() {
        transaksiList = new ArrayList<>();
        String[] menuNames = {"Nasi Goreng", "Bakso", "Mie Ayam", "Nasi Pecel", "Soto Ayam"};
        int[] prices = {12000, 12000, 10000, 8000, 12000};
        int[] soldCounts = {50, 45, 40, 35, 30};

        LocalDateTime todayStart = LocalDateTime.now().withHour(8).withMinute(0).withSecond(0);
        Random rand = new Random();

        for (int i = 0; i < menuNames.length; i++) {
            for (int j = 0; j < soldCounts[i]; j++) {
                Transaksi transaksi = new Transaksi();
                transaksi.setNamaMenu(menuNames[i]);
                transaksi.setHarga(prices[i]);
                transaksi.setJumlah(1);
                // Jam acak antara jam 08:00 s.d. 18:00 hari ini
                int jamAcak = rand.nextInt(10); // 0–9 jam
                int menitAcak = rand.nextInt(60);
                transaksi.setTanggal(todayStart.plusHours(jamAcak).plusMinutes(menitAcak));

                transaksiList.add(transaksi);
            }
        }
    }


    // Method baru untuk inisialisasi menu table data
    private void initializeMenuTableData() {
        menuTableData = FXCollections.observableArrayList();

        String[] menuNames = {
                "🍛 Nasi Gudeg", "🍲 Bakso", "🍜 Mie Ayam", "🥗 Nasi Pecel", "🍵 Soto Ayam", "🥙 Gado-Gado",
                "🍛 Nasi Rawon", "🐟 Pecel Lele", "🍲 Tahu Campur", "🍢 Sate Ayam", "🍳 Nasi Goreng", "🌯 Martabak Telur",
                "🍝 Mie Godog", "🥩 Ayam Penyet", "🍛 Nasi Liwet", "🍲 Rujak Cingur", "🥪 Roti Bakar", "🍜 Bakso Bakar",
                "🧊 Es Teh Manis", "🍊 Es Jeruk", "🥥 Es Kelapa Muda", "☕ Kopi Tubruk", "🥑 Jus Alpukat", "🍧 Es Campur",
                "🫖 Wedang Jahe", "🍮 Es Dawet", "🍓 Jus Jambu", "🍧 Es Cendol", "🥤 Es Susu", "🍋 Es Lemon Tea",
                "🥛 Susu Jahe", "🧋 Es Teh Tarik", "🥤 Jus Melon", "🍹 Es Buah", "🥤 Jus Sirsak", "🧊 Es Kopi Susu"
        };

        int[] prices = {
                10000, 12000, 10000, 8000, 12000, 10000, 15000, 12000, 10000, 15000,
                12000, 8000, 9000, 13000, 11000, 10000, 7000, 14000,
                4000, 5000, 10000, 4000, 10000, 10000, 5000, 6000, 8000, 6000,
                7000, 6000, 5000, 7000, 9000, 8000, 10000, 8000
        };

        String[] categories = {
                "Makanan", "Makanan", "Makanan", "Makanan", "Makanan", "Makanan", "Makanan", "Makanan", "Makanan", "Makanan",
                "Makanan", "Makanan", "Makanan", "Makanan", "Makanan", "Makanan", "Makanan", "Makanan",
                "Minuman", "Minuman", "Minuman", "Minuman", "Minuman", "Minuman", "Minuman", "Minuman", "Minuman", "Minuman",
                "Minuman", "Minuman", "Minuman", "Minuman", "Minuman", "Minuman", "Minuman", "Minuman"
        };

        boolean[] statuses = new boolean[menuNames.length];
        Arrays.fill(statuses, true); // semua default aktif

        for (int i = 0; i < menuNames.length; i++) {
            int newNo = menuTableData.size() + 1;
            String formattedHarga = formatCurrency(prices[i]);

            MenuTableRow newRow = new MenuTableRow(newNo, menuNames[i], formattedHarga, categories[i], "Aktif", statuses[i]);

            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Aktif", "Non-Aktif");
            comboBox.setValue("Aktif");
            comboBox.setOnAction(e -> {
                String newStatus = comboBox.getValue();
                newRow.setStatus(newStatus);
                updateMetrics();
            });
            newRow.setStatusComboBox(comboBox);

            Button deleteButton = new Button("🗑 Hapus");
            deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 5;");
            deleteButton.setOnAction(e -> {
                menuTableData.remove(newRow);
                updateMenuNumbers();
                updateMetrics();
                showNotification("Menu '" + newRow.getNama() + "' berhasil dihapus", "success");
            });
            newRow.setDeleteButton(deleteButton);

            menuTableData.add(newRow);
        }
    }

    private BorderPane createMainContent() {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createHeader());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox mainContainer = new VBox(30);
        mainContainer.setPadding(new Insets(40));
        mainContainer.setStyle("-fx-background-color: linear-gradient(to right, #ece9e6, #ffffff);");

        HBox topSection = createTopSection();
        VBox menuSection = createManagementSection();

        mainContainer.getChildren().addAll(topSection, menuSection);
        scrollPane.setContent(mainContainer);
        borderPane.setCenter(scrollPane);

        return borderPane;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(20, 40, 10, 40));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #2c3e50;");

        Label title = new Label("📊 Dashboard Admin Kantin");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        refreshBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 8;");
        refreshBtn.setOnAction(e -> refreshDashboard());

        lastUpdateLabel = new Label();
        updateLastUpdateTime();
        lastUpdateLabel.setFont(Font.font("Arial", 12));
        lastUpdateLabel.setTextFill(Color.LIGHTGRAY);

        VBox rightSection = new VBox(5);
        rightSection.setAlignment(Pos.CENTER_RIGHT);
        rightSection.getChildren().addAll(refreshBtn, lastUpdateLabel);

        header.getChildren().addAll(title, spacer, rightSection);
        return header;
    }

    private HBox createTopSection() {
        HBox topContainer = new HBox(30);
        topContainer.setAlignment(Pos.TOP_CENTER);
        topContainer.getChildren().addAll(createMetricsSection(), createTodayChart());
        return topContainer;
    }

    private VBox createMetricsSection() {
        Label metricsTitle = new Label("📈 Overview Statistik");
        metricsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        metricsTitle.setTextFill(Color.web("#2c3e50"));

        metricsGrid = new GridPane();
        metricsGrid.setHgap(20);
        metricsGrid.setVgap(20);
        updateMetrics();

        VBox metricsBox = new VBox(20, metricsTitle, metricsGrid);
        metricsBox.setPadding(new Insets(25));
        metricsBox.setPrefWidth(500);
        metricsBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 12, 0, 0, 5);");


        Label statusFeatureTitle = new Label("📍 Fitur Manajemen Status Pesanan");
        statusFeatureTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        statusFeatureTitle.setTextFill(Color.web("#2c3e50"));

        HBox orderStatusBox = new HBox(20);
        orderStatusBox.setPadding(new Insets(10));
        orderStatusBox.setAlignment(Pos.CENTER_LEFT);

        VBox todaysOrderCard = createStatusCard("Pesanan Hari Ini", String.valueOf(transaksiList.size()), "#FF6F61");
        VBox completedCard = createStatusCard("Selesai", String.valueOf((int)(transaksiList.size() * 0.9)), "#28A745");
        VBox pendingCard = createStatusCard("Menunggu", String.valueOf((int)(transaksiList.size() * 0.1)), "#FFC107");

        orderStatusBox.getChildren().addAll(todaysOrderCard, completedCard, pendingCard);

        metricsBox.getChildren().addAll(statusFeatureTitle, orderStatusBox);


        return metricsBox;
    }

    private void updateMetrics() {
        // Null check untuk menuTableData
        if (menuTableData == null) {
            System.out.println("Warning: menuTableData is null, initializing...");
            initializeMenuTableData();
        }

        metricsGrid.getChildren().clear();

        // Calculate today's statistics
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        List<Transaksi> todayTransactions = transaksiList.stream()
                .filter(t -> t.getTanggal().isAfter(today))
                .collect(Collectors.toList());

        int totalTransaksi = todayTransactions.size();
        double totalPendapatan = todayTransactions.stream()
                .mapToDouble(t -> t.getHarga() * t.getJumlah()).sum();
        int totalMenuTerjual = todayTransactions.stream()
                .mapToInt(Transaksi::getJumlah).sum();

        // Safe count dengan null check
        int totalMenuAktif = 0;
        if (menuTableData != null) {
            totalMenuAktif = (int) menuTableData.stream()
                    .filter(menu -> "Aktif".equals(menu.getStatus())).count();
        }

        metricsGrid.add(createMetricBox("TRANSAKSI HARI INI", String.valueOf(totalTransaksi), "#3498db", "📈"), 0, 0);
        metricsGrid.add(createMetricBox("PENDAPATAN HARI INI", formatCurrency(totalPendapatan), "#27ae60", "💰"), 1, 0);
        metricsGrid.add(createMetricBox("MENU TERJUAL HARI INI", String.valueOf(totalMenuTerjual), "#9b59b6", "📦"), 0, 1);
        metricsGrid.add(createMetricBox("TOTAL MENU AKTIF", String.valueOf(totalMenuAktif), "#f39c12", "📋"), 1, 1);
    }

    private VBox createTodayChart() {
        Label chartTitle = new Label("🏆 Menu Favorit Hari Ini (Top 5)");
        chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        chartTitle.setTextFill(Color.web("#2c3e50"));

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Menu");

        NumberAxis yAxis = new NumberAxis(0, 50, 10);
        yAxis.setLabel("Jumlah Terjual");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Penjualan Menu Hari Ini");
        barChart.setLegendVisible(false);
        barChart.setPrefHeight(400);
        barChart.setPrefWidth(600);

        updateTodayChart();

        VBox chartBox = new VBox(15, chartTitle, barChart);
        chartBox.setPadding(new Insets(20));
        chartBox.setPrefWidth(650);
        chartBox.setStyle("-fx-background-color: #fff8e1; -fx-background-radius: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);");

        return chartBox;
    }

    private void updateTodayChart() {
        barChart.getData().clear();

        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        Map<String, Integer> todaySales = transaksiList.stream()
                .filter(t -> t.getTanggal().isAfter(today))
                .collect(Collectors.groupingBy(
                        Transaksi::getNamaMenu,
                        Collectors.summingInt(Transaksi::getJumlah)
                ));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        todaySales.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue())));

        barChart.getData().add(series);

        // Style the bars
        Timeline delayedStyling = new Timeline();
        delayedStyling.getKeyFrames().add(new javafx.animation.KeyFrame(Duration.millis(100), e -> {
            barChart.lookupAll(".default-color0.chart-bar")
                    .forEach(node -> node.setStyle("-fx-bar-fill: #3498db;"));
        }));
        delayedStyling.play();
    }

    private VBox createManagementSection() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(25));
        container.setStyle("-fx-background-color: #e3f2fd; -fx-background-radius: 15;");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("🛠 Manajemen Menu");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setTextFill(Color.web("#2c3e50"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button menuButton = new Button("⋮");
        menuButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 5;");
        ContextMenu contextMenu = new ContextMenu();
        javafx.scene.control.MenuItem editItem = new javafx.scene.control.MenuItem("≡ Mode Edit Menu");
        editItem.setOnAction(e -> toggleEditMode());
        contextMenu.getItems().add(editItem);

        menuButton.setOnAction(e -> contextMenu.show(menuButton,
                menuButton.localToScreen(menuButton.getBoundsInLocal()).getMinX(),
                menuButton.localToScreen(menuButton.getBoundsInLocal()).getMaxY()));

        header.getChildren().addAll(title, spacer, menuButton);

        createMenuTable();

        Button addButton = createStyledButton("➕ Tambah Menu", "#27ae60");
        addButton.setOnAction(e -> showAddMenuForm());

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(addButton);

        container.getChildren().addAll(header, menuTable, buttonBox);
        return container;
    }

    private void createMenuTable() {
        menuTable = new TableView<>();
        menuTable.setPrefHeight(300);
        menuTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<MenuTableRow, Integer> noCol = new TableColumn<>("No.");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));
        noCol.setPrefWidth(50);

        TableColumn<MenuTableRow, String> nameCol = new TableColumn<>("Menu");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<MenuTableRow, String> priceCol = new TableColumn<>("Harga");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("harga"));

        TableColumn<MenuTableRow, String> categoryCol = new TableColumn<>("Kategori");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("kategori"));

        TableColumn<MenuTableRow, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<MenuTableRow, ComboBox<String>> editStatusCol = new TableColumn<>("Status (Edit)");
        editStatusCol.setCellValueFactory(new PropertyValueFactory<>("statusComboBox"));
        editStatusCol.setVisible(false);

        TableColumn<MenuTableRow, Button> actionCol = new TableColumn<>("Aksi");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        actionCol.setVisible(false);

        menuTable.getColumns().addAll(noCol, nameCol, priceCol, categoryCol, statusCol, editStatusCol, actionCol);

        // Set data ke table setelah data sudah diinisialisasi
        if (menuTableData != null) {
            menuTable.setItems(menuTableData);
        }
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;
        TableColumn<MenuTableRow, String> statusCol = (TableColumn<MenuTableRow, String>) menuTable.getColumns().get(4);
        TableColumn<MenuTableRow, ComboBox<String>> editStatusCol = (TableColumn<MenuTableRow, ComboBox<String>>) menuTable.getColumns().get(5);
        TableColumn<MenuTableRow, Button> actionCol = (TableColumn<MenuTableRow, Button>) menuTable.getColumns().get(6);

        statusCol.setVisible(!isEditMode);
        editStatusCol.setVisible(isEditMode);
        actionCol.setVisible(isEditMode);

        showNotification(isEditMode ? "Mode edit aktif" : "Mode edit dinonaktifkan", "info");
    }

    private void showAddMenuForm() {
        VBox overlay = new VBox();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        overlay.setAlignment(Pos.CENTER);
        overlay.setPadding(new Insets(30));
        overlay.setPrefSize(stage.getWidth(), stage.getHeight());

        VBox formContainer = new VBox(20);
        formContainer.setPadding(new Insets(25));
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setMaxWidth(400); // Ukuran max form agar nggak gede banget
        formContainer.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label titleLabel = new Label("➕ Tambah Menu Baru");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        GridPane formGrid = new GridPane();
        formGrid.setHgap(12);
        formGrid.setVgap(12);
        formGrid.setAlignment(Pos.CENTER);

        TextField namaField = new TextField();
        namaField.setPromptText("Nama menu...");
        TextField hargaField = new TextField();
        hargaField.setPromptText("Harga (contoh: 15000)...");
        ComboBox<String> kategoriCombo = new ComboBox<>();
        kategoriCombo.getItems().addAll("Makanan", "Minuman", "Snack");
        kategoriCombo.setPromptText("Pilih kategori...");

        formGrid.add(new Label("Nama Menu:"), 0, 0);
        formGrid.add(namaField, 1, 0);
        formGrid.add(new Label("Harga:"), 0, 1);
        formGrid.add(hargaField, 1, 1);
        formGrid.add(new Label("Kategori:"), 0, 2);
        formGrid.add(kategoriCombo, 1, 2);

        Button saveBtn = createStyledButton("💾 Simpan", "#27ae60");
        Button cancelBtn = createStyledButton("❌ Batal", "#e74c3c");

        HBox buttonBox = new HBox(12, saveBtn, cancelBtn);
        buttonBox.setAlignment(Pos.CENTER);

        formContainer.getChildren().addAll(titleLabel, formGrid, buttonBox);
        overlay.getChildren().add(formContainer);

        this.getChildren().add(overlay); // ← ditambahkan ke StackPane dashboard

        // Tombol batal menghapus overlay
        cancelBtn.setOnAction(e -> this.getChildren().remove(overlay));

        // Simpan menu
        saveBtn.setOnAction(e -> {
            if (validateForm(namaField.getText(), hargaField.getText(), kategoriCombo.getValue())) {
                addMenuToTable(namaField.getText(), hargaField.getText(), kategoriCombo.getValue(), true);
                this.getChildren().remove(overlay);
                showNotification("✅ Menu '" + namaField.getText() + "' berhasil ditambahkan", "success");
                updateMetrics();
            }
        });
    }

    private boolean validateForm(String nama, String harga, String kategori) {
        if (nama == null || nama.trim().isEmpty()) {
            showNotification("Nama menu tidak boleh kosong!", "error");
            return false;
        }

        if (menuTableData.stream().anyMatch(menu -> menu.getNama().toLowerCase().contains(nama.toLowerCase().trim()))) {
            showNotification("Nama menu sudah ada!", "error");
            return false;
        }

        if (harga == null || harga.trim().isEmpty()) {
            showNotification("Harga tidak boleh kosong!", "error");
            return false;
        }

        try {
            double hargaValue = Double.parseDouble(harga.trim());
            if (hargaValue <= 0) {
                showNotification("Harga harus lebih dari 0!", "error");
                return false;
            }
        } catch (NumberFormatException e) {
            showNotification("Format harga tidak valid!", "error");
            return false;
        }

        if (kategori == null) {
            showNotification("Kategori harus dipilih!", "error");
            return false;
        }

        return true;
    }

    private void addMenuToTable(String nama, String harga, String kategori, boolean showNotif) {
        int newNo = menuTableData.size() + 1;
        double hargaValue = Double.parseDouble(harga);
        String formattedHarga = formatCurrency(hargaValue);

        String displayName = nama.startsWith("🍛") || nama.startsWith("🍲") || nama.startsWith("🍜") ||
                nama.startsWith("🥗") || nama.startsWith("🍵") ? nama : "📋 " + nama;

        MenuTableRow newRow = new MenuTableRow(newNo, displayName, formattedHarga, kategori, "Aktif", true);

        // Setup status combo box
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Aktif", "Non-Aktif");
        comboBox.setValue("Aktif");
        comboBox.setOnAction(e -> {
            String newStatus = comboBox.getValue();
            newRow.setStatus(newStatus);
            updateMetrics(); // Real-time update
            if (showNotif) showNotification("Status menu diubah menjadi " + newStatus, "info");
        });
        newRow.setStatusComboBox(comboBox);

        // Setup delete button
        Button deleteButton = new Button("🗑 Hapus");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 5;");
        deleteButton.setOnAction(e -> {
            menuTableData.remove(newRow);
            updateMenuNumbers();
            updateMetrics(); // Real-time update
            showNotification("Menu '" + newRow.getNama() + "' berhasil dihapus", "success");
        });
        newRow.setDeleteButton(deleteButton);

        menuTableData.add(newRow);
        menuTable.refresh();
    }

    private void updateMenuNumbers() {
        for (int i = 0; i < menuTableData.size(); i++) {
            menuTableData.get(i).setNo(i + 1);
        }
        menuTable.refresh();
    }

    // Inner class for table data
    public static class MenuTableRow {
        private int no;
        private String nama, harga, kategori, status;
        private boolean isActive;
        private ComboBox<String> statusComboBox;
        private Button deleteButton;

        public MenuTableRow(int no, String nama, String harga, String kategori, String status, boolean isActive) {
            this.no = no;
            this.nama = nama;
            this.harga = harga;
            this.kategori = kategori;
            this.status = status;
            this.isActive = isActive;
        }

        // Getters and setters
        public int getNo() { return no; }
        public void setNo(int no) { this.no = no; }
        public String getNama() { return nama; }
        public String getHarga() { return harga; }
        public String getKategori() { return kategori; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public ComboBox<String> getStatusComboBox() { return statusComboBox; }
        public void setStatusComboBox(ComboBox<String> statusComboBox) { this.statusComboBox = statusComboBox; }
        public Button getDeleteButton() { return deleteButton; }
        public void setDeleteButton(Button deleteButton) { this.deleteButton = deleteButton; }
    }

    private VBox createMetricBox(String title, String value, String colorHex, String emoji) {
        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font("Arial", 24));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        titleLabel.setTextFill(Color.web("#666"));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        valueLabel.setTextFill(Color.web(colorHex));

        HBox header = new HBox(8, emojiLabel, new VBox(2, titleLabel, valueLabel));
        header.setAlignment(Pos.CENTER_LEFT);

        VBox metricBox = new VBox(5);
        metricBox.setAlignment(Pos.CENTER_LEFT);
        metricBox.setPadding(new Insets(15));
        metricBox.setPrefWidth(200);
        metricBox.setPrefHeight(80);
        metricBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-border-color: " + colorHex + "; -fx-border-width: 2; -fx-border-radius: 10;");

        metricBox.getChildren().add(header);
        return metricBox;
    }
    private VBox createStatusCard(String title, String value, String colorHex) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setPrefWidth(150);
        card.setStyle("-fx-background-color: " + colorHex + "; -fx-background-radius: 10;");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        titleLabel.setTextFill(Color.WHITE);

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        valueLabel.setTextFill(Color.WHITE);

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }


    private void createNotificationSystem() {
        notificationBox = new VBox(10);
        notificationBox.setAlignment(Pos.TOP_RIGHT);
        notificationBox.setPrefWidth(350);
        notificationBox.setVisible(false);

        notificationLabel = new Label();
        notificationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        notificationLabel.setWrapText(true);
        notificationLabel.setPadding(new Insets(15));
        notificationLabel.setMaxWidth(350);
        notificationLabel.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

        notificationBox.getChildren().add(notificationLabel);
    }

    private void showNotification(String message, String type) {
        String style = switch (type) {
            case "success" -> "-fx-background-color: #d4edda; -fx-text-fill: #155724; -fx-border-color: #c3e6cb;";
            case "error" -> "-fx-background-color: #f8d7da; -fx-text-fill: #721c24; -fx-border-color: #f5c6cb;";
            case "warning" -> "-fx-background-color: #fff3cd; -fx-text-fill: #856404; -fx-border-color: #ffeaa7;";
            default -> "-fx-background-color: #d1ecf1; -fx-text-fill: #0c5460; -fx-border-color: #bee5eb;";
        };

        notificationLabel.setText(message);
        notificationLabel.setStyle(style + " -fx-background-radius: 8; -fx-border-radius: 8;");
        notificationBox.setVisible(true);

        Timeline timeline = new Timeline(new javafx.animation.KeyFrame(Duration.seconds(4), e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), notificationBox);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> notificationBox.setVisible(false));
            fadeOut.play();
        }));
        timeline.play();

        notificationBox.setOpacity(1.0);
    }

    private void refreshDashboard() {
        updateMetrics();
        updateTodayChart();
        updateLastUpdateTime();
        showNotification("Dashboard berhasil diperbarui", "success");
    }

    private void updateLastUpdateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        lastUpdateLabel.setText("Update: " + now.format(formatter));
    }

    private Button createStyledButton(String text, String colorHex) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-padding: 12 20 12 20;");
        return button;
    }

    private String formatCurrency(double amount) {
        return currencyFormat.format(amount).replace("IDR", "Rp");
    }
}