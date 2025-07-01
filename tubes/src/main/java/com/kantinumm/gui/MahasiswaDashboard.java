package main.java.com.kantinumm.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.java.com.kantinumm.model.Transaksi;
import main.java.com.kantinumm.model.User;
import main.java.com.kantinumm.service.KantinSystem;

import java.util.ArrayList;
import java.util.List;

public class MahasiswaDashboard extends StackPane {
    private final MainApp app;
    private final User user;
    private final KantinSystem kantinSystem;
    private final List<main.java.com.kantinumm.model.MenuItem> keranjang = new ArrayList<>();
    private VBox mainContent;

    public MahasiswaDashboard(MainApp app, User user, KantinSystem kantinSystem) {
        this.app = app;
        this.user = user;
        this.kantinSystem = kantinSystem;

        mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.TOP_CENTER);

        Label pilihKategori = new Label("Pilih Kategori:");
        ToggleGroup kategoriGroup = new ToggleGroup();
        RadioButton makananBtn = new RadioButton("Makanan");
        RadioButton minumanBtn = new RadioButton("Minuman");
        makananBtn.setToggleGroup(kategoriGroup);
        minumanBtn.setToggleGroup(kategoriGroup);
        makananBtn.setSelected(true);

        HBox kategoriBox = new HBox(20, makananBtn, minumanBtn);
        kategoriBox.setAlignment(Pos.CENTER);

        VBox konten = new VBox(10);
        konten.setAlignment(Pos.CENTER);

        kategoriGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;
                tampilkanMenu(selected.getText(), konten);
            }
        });

        Button lihatKeranjangBtn = new Button("🛒 Lihat Keranjang");
        lihatKeranjangBtn.setOnAction(e -> tampilkanKeranjang());

        mainContent.getChildren().addAll(pilihKategori, kategoriBox, konten, lihatKeranjangBtn);
        this.getChildren().add(mainContent);

        tampilkanMenu("Makanan", konten); // Default tampil
    }

    private void tampilkanMenu(String kategori, VBox konten) {
        konten.getChildren().clear();
        List<main.java.com.kantinumm.model.MenuItem> menu = kantinSystem.getMenuListByKategori(kategori);

        FlowPane flow = new FlowPane();
        flow.setHgap(20);
        flow.setVgap(20);
        flow.setPadding(new Insets(10));
        flow.setAlignment(Pos.CENTER);
        flow.setPrefWrapLength(800);

        ScrollPane scrollPane = new ScrollPane(flow);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent;");

        for (main.java.com.kantinumm.model.MenuItem item : menu) {
            VBox card = new VBox(10);
            card.setPadding(new Insets(10));
            card.setAlignment(Pos.CENTER);
            card.setPrefSize(160, 130);
            card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10;");

            Label nama = new Label(item.getNamaMenu());
            nama.setStyle("-fx-font-weight: bold;");
            Label harga = new Label("Rp" + (int) item.getHarga());
            harga.setStyle("-fx-text-fill: #e91e63;");

            Button infoBtn = new Button("Info");
            CheckBox pilihBox = new CheckBox("Add");

            infoBtn.setOnAction(ev -> showDeskripsiPopup(item));

            pilihBox.setOnAction(e -> {
                if (pilihBox.isSelected()) {
                    int makananCount = 0;
                    int minumanCount = 0;

                    for (main.java.com.kantinumm.model.MenuItem m : keranjang) {
                        if (m.getKategori().equalsIgnoreCase("Makanan")) makananCount++;
                        else if (m.getKategori().equalsIgnoreCase("Minuman")) minumanCount++;
                    }

                    String kategoriItem = item.getKategori();
                    if ((kategoriItem.equalsIgnoreCase("Makanan") && makananCount >= 3) ||
                            (kategoriItem.equalsIgnoreCase("Minuman") && minumanCount >= 3)) {
                        pilihBox.setSelected(false);
                        showErrorPopup("❌ Maksimal 3 makanan dan 3 minuman dalam 1 pesanan");
                    } else {
                        keranjang.add(item);
                    }
                } else {
                    keranjang.remove(item);
                }
            });

            card.getChildren().addAll(nama, harga, infoBtn, pilihBox);
            flow.getChildren().add(card);
        }

        konten.getChildren().add(scrollPane);
    }

    private void showDeskripsiPopup(main.java.com.kantinumm.model.MenuItem item) {
        // Background overlay
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlay.setOnMouseClicked(e -> this.getChildren().remove(overlay));

        // Popup content
        VBox popup = new VBox(15);
        popup.setPadding(new Insets(20));
        popup.setAlignment(Pos.CENTER);
        popup.setMaxWidth(350);
        popup.setMaxHeight(250);
        popup.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);");

        Label titleLabel = new Label(item.getNamaMenu());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label deskripsiLabel = new Label(item.getDeskripsi());
        deskripsiLabel.setWrapText(true);
        deskripsiLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        Button closeBtn = new Button("Tutup");
        closeBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        closeBtn.setOnAction(e -> this.getChildren().remove(overlay));

        popup.getChildren().addAll(titleLabel, deskripsiLabel, closeBtn);

        overlay.getChildren().add(popup);
        StackPane.setAlignment(popup, Pos.CENTER);

        this.getChildren().add(overlay);
    }

    private void tampilkanKeranjang() {
        // Background overlay
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlay.setOnMouseClicked(e -> this.getChildren().remove(overlay));

        // Popup content
        VBox popup = new VBox(15);
        popup.setPadding(new Insets(20));
        popup.setAlignment(Pos.CENTER);
        popup.setMaxWidth(400);
        popup.setMaxHeight(500);
        popup.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);");

        Label titleLabel = new Label("🛒 Keranjang Anda");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Items list in scrollable area
        VBox itemsList = new VBox(5);
        itemsList.setAlignment(Pos.CENTER_LEFT);
        double subtotal = 0;

        for (main.java.com.kantinumm.model.MenuItem m : keranjang) {
            Label itemLabel = new Label("• " + m.getNamaMenu() + " - Rp" + (int) m.getHarga());
            itemLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
            itemsList.getChildren().add(itemLabel);
            subtotal += m.getHarga();
        }

        ScrollPane itemsScroll = new ScrollPane(itemsList);
        itemsScroll.setFitToWidth(true);
        itemsScroll.setPrefHeight(150);
        itemsScroll.setStyle("-fx-background-color: transparent;");

        double diskon = 0;
        double totalAkhir = subtotal;

        if (subtotal >= 50000) {
            diskon = subtotal * 0.10;
            totalAkhir = subtotal - diskon;
        }

        final double finalSubtotal = subtotal;
        final double finalDiskon = diskon;
        final double finalTotalAkhir = totalAkhir;

        // Price info
        VBox hargaInfo = new VBox(5);
        hargaInfo.setAlignment(Pos.CENTER);

        Label subtotalLabel = new Label("Total: Rp" + (int) subtotal);
        hargaInfo.getChildren().add(subtotalLabel);

        if (diskon > 0) {
            Label diskonLabel = new Label("Diskon: Rp" + (int) diskon);
            diskonLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
            Label totalLabel = new Label("Bayar: Rp" + (int) totalAkhir);
            totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            hargaInfo.getChildren().addAll(diskonLabel, totalLabel);
        } else {
            subtotalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        }

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button pesanBtn = new Button("Pesan Sekarang");
        pesanBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        Button backBtn = new Button("Kembali");
        backBtn.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-weight: bold;");

        pesanBtn.setOnAction(e -> {
            if (keranjang.size() > 6) {
                showErrorPopup("❌ Maksimal 3 makanan dan 3 minuman dalam 1 pesanan");
                return;
            }

            try {
                Transaksi t = kantinSystem.buatTransaksi(user, keranjang);
                this.getChildren().remove(overlay);
                showStrukPopup(finalSubtotal, finalDiskon, finalTotalAkhir);
            } catch (Exception ex) {
                showInfoPopup(ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> this.getChildren().remove(overlay));

        buttonBox.getChildren().addAll(backBtn, pesanBtn);

        popup.getChildren().addAll(titleLabel, itemsScroll, hargaInfo, buttonBox);

        overlay.getChildren().add(popup);
        StackPane.setAlignment(popup, Pos.CENTER);

        this.getChildren().add(overlay);
    }

    private void showStrukPopup(double subtotal, double diskon, double totalAkhir) {
        // Background overlay
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlay.setOnMouseClicked(e -> {
            this.getChildren().remove(overlay);
            keranjang.clear();
            app.showLogin();
        });

        // Popup content
        VBox popup = new VBox(15);
        popup.setPadding(new Insets(20));
        popup.setAlignment(Pos.CENTER);
        popup.setMaxWidth(400);
        popup.setMaxHeight(500);
        popup.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);");

        Label titleLabel = new Label("=== STRUK PEMBELIAN ===");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label infoLabel = new Label("Nama: " + user.getUsername() + "\nTanggal: " + java.time.LocalDateTime.now().toString().substring(0, 19));
        infoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

        // Items in scrollable area
        VBox itemsList = new VBox(3);
        itemsList.setAlignment(Pos.CENTER_LEFT);

        for (main.java.com.kantinumm.model.MenuItem item : keranjang) {
            Label itemLabel = new Label("• " + item.getNamaMenu() + " - Rp" + (int) item.getHarga());
            itemLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #555;");
            itemsList.getChildren().add(itemLabel);
        }

        ScrollPane itemsScroll = new ScrollPane(itemsList);
        itemsScroll.setFitToWidth(true);
        itemsScroll.setPrefHeight(120);
        itemsScroll.setStyle("-fx-background-color: transparent;");

        // Total info
        VBox totalInfo = new VBox(3);
        totalInfo.setAlignment(Pos.CENTER);

        Label subtotalLabel = new Label("Total: Rp" + (int) subtotal);
        totalInfo.getChildren().add(subtotalLabel);

        if (diskon > 0) {
            Label diskonLabel = new Label("Diskon: Rp" + (int) diskon);
            diskonLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
            Label totalLabel = new Label("Bayar: Rp" + (int) totalAkhir);
            totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            totalInfo.getChildren().addAll(diskonLabel, totalLabel);
        } else {
            subtotalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        }

        Label thanksLabel = new Label("Terima kasih!");
        thanksLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #666;");

        Button okBtn = new Button("OK");
        okBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        okBtn.setOnAction(e -> {
            this.getChildren().remove(overlay);
            keranjang.clear();
            app.showLogin();
        });

        popup.getChildren().addAll(titleLabel, infoLabel, itemsScroll, totalInfo, thanksLabel, okBtn);

        overlay.getChildren().add(popup);
        StackPane.setAlignment(popup, Pos.CENTER);

        this.getChildren().add(overlay);
    }

    private void showInfoPopup(String message) {
        // Background overlay
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlay.setOnMouseClicked(e -> this.getChildren().remove(overlay));

        // Popup content
        VBox popup = new VBox(15);
        popup.setPadding(new Insets(20));
        popup.setAlignment(Pos.CENTER);
        popup.setMaxWidth(300);
        popup.setMaxHeight(200);
        popup.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);");

        Label titleLabel = new Label("ℹ️ Info");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        Button okBtn = new Button("OK");
        okBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        okBtn.setOnAction(e -> this.getChildren().remove(overlay));

        popup.getChildren().addAll(titleLabel, messageLabel, okBtn);

        overlay.getChildren().add(popup);
        StackPane.setAlignment(popup, Pos.CENTER);

        this.getChildren().add(overlay);
    }

    private void showErrorPopup(String message) {
        // Background overlay
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlay.setOnMouseClicked(e -> this.getChildren().remove(overlay));

        // Popup content
        VBox popup = new VBox(15);
        popup.setPadding(new Insets(20));
        popup.setAlignment(Pos.CENTER);
        popup.setMaxWidth(300);
        popup.setMaxHeight(200);
        popup.setStyle("-fx-background-color: #ffebee; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #f44336; -fx-border-width: 2; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);");

        Label titleLabel = new Label("⚠️ Peringatan!");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #f44336;");

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-text-fill: #333; -fx-font-size: 12px;");

        Button okBtn = new Button("OK");
        okBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        okBtn.setOnAction(e -> this.getChildren().remove(overlay));

        popup.getChildren().addAll(titleLabel, messageLabel, okBtn);

        overlay.getChildren().add(popup);
        StackPane.setAlignment(popup, Pos.CENTER);

        this.getChildren().add(overlay);
    }
}