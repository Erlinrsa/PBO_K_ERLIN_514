import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Random;

public class TebakAngkaApp extends Application {

    private int angkaRandom;
    private int jumlahTebakan;

    private Label labelJudul;
    private Label labelSubJudul;
    private Label labelFeedback;
    private Label labelPercobaan;
    private TextField inputTebakan;
    private Button tombolTebak;

    @Override
    public void start(Stage primaryStage) {
        angkaRandom = generateAngka();
        jumlahTebakan = 0;

        // Judul
        labelJudul = new Label("🎯 Tebak Angka 1–100");
        labelJudul.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        labelJudul.setTextFill(Color.web("#1a237e"));

        // Subjudul
        labelSubJudul = new Label("Masukkan tebakanmu!");
        labelSubJudul.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        labelSubJudul.setTextFill(Color.web("#616161"));

        // Feedback awal
        labelFeedback = new Label("");
        labelFeedback.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

        // Input
        inputTebakan = new TextField();
        inputTebakan.setPromptText("Masukkan angka di sini");
        inputTebakan.setPrefWidth(200);
        inputTebakan.setFont(Font.font("Segoe UI", 14));
        inputTebakan.setStyle(
                "-fx-border-color: #2196F3; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-radius: 4px; " +
                        "-fx-border-radius: 4px;"
        );

        // Tombol
        tombolTebak = new Button("🎲 Coba Tebak!");
        tombolTebak.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        tombolTebak.setPrefWidth(130);
        tombolTebak.setPrefHeight(36);
        tombolTebak.setStyle(
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 4, 0, 0, 2);"
        );
        tombolTebak.setOnAction(e -> prosesTebakan());

        // HBox input
        HBox hBoxInput = new HBox(10, inputTebakan, tombolTebak);
        hBoxInput.setAlignment(Pos.CENTER);

        // Label percobaan
        labelPercobaan = new Label("Jumlah percobaan: 0");
        labelPercobaan.setFont(Font.font("Segoe UI", 13));
        labelPercobaan.setTextFill(Color.GRAY);

        // Layout utama
        VBox layoutUtama = new VBox(15,
                labelJudul,
                labelSubJudul,
                labelFeedback,
                hBoxInput,
                labelPercobaan
        );
        layoutUtama.setPadding(new Insets(30));
        layoutUtama.setAlignment(Pos.CENTER);
        layoutUtama.setStyle("-fx-background-color: #ffe6f0;");

        Scene scene = new Scene(layoutUtama, 500, 320);
        primaryStage.setTitle("Tebak Angka Advance");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private int generateAngka() {
        return new Random().nextInt(100) + 1;
    }

    private void prosesTebakan() {
        String input = inputTebakan.getText();
        try {
            int tebakan = Integer.parseInt(input);
            jumlahTebakan++;

            // Hapus border biru setelah input pertama
            inputTebakan.setStyle(
                    "-fx-border-color: transparent;" +
                            "-fx-background-radius: 4px;" +
                            "-fx-border-radius: 4px;" +
                            "-fx-border-width: 0;"
            );

            if (tebakan < angkaRandom) {
                labelFeedback.setText("⬇️ Terlalu kecil!");
                labelFeedback.setTextFill(Color.web("#f57c00"));
            } else if (tebakan > angkaRandom) {
                labelFeedback.setText("⬆️ Terlalu besar!");
                labelFeedback.setTextFill(Color.web("#f57c00"));
            } else {
                labelFeedback.setText("✔️ Tebakan benar!");
                labelFeedback.setTextFill(Color.web("#2e7d32"));

                inputTebakan.setEditable(false); // Tidak disable, hanya tidak bisa diketik
                tombolTebak.setText("🔁 Main Lagi");
                tombolTebak.setStyle(
                        "-fx-background-color: #2196F3;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 6;" +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 4, 0, 0, 2);"
                );
                tombolTebak.setOnAction(e -> resetGame());
            }

            labelPercobaan.setText("Jumlah percobaan: " + jumlahTebakan);
            inputTebakan.clear();
        } catch (NumberFormatException e) {
            labelFeedback.setText("⚠️ Masukkan angka valid!");
            labelFeedback.setTextFill(Color.RED);
        }
    }

    private void resetGame() {
        angkaRandom = generateAngka();
        jumlahTebakan = 0;
        labelFeedback.setText("");
        labelPercobaan.setText("Jumlah percobaan: 0");

        inputTebakan.setEditable(true);
        inputTebakan.setStyle(
                "-fx-border-color: #2196F3; " +
                        "-fx-border-width: 2px; " +
                        "-fx-background-radius: 4px; " +
                        "-fx-border-radius: 4px;"
        );

        tombolTebak.setText("🎲 Coba Tebak!");
        tombolTebak.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        tombolTebak.setStyle(
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 4, 0, 0, 2);"
        );
        tombolTebak.setOnAction(e -> prosesTebakan());
        inputTebakan.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
