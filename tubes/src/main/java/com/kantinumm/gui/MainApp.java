package main.java.com.kantinumm.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.kantinumm.model.User;
import main.java.com.kantinumm.service.KantinSystem;
import main.java.com.kantinumm.service.LoginSystem;

public class MainApp extends Application {
    private Stage primaryStage;
    private final LoginSystem loginSystem = new LoginSystem();
    private final KantinSystem kantinSystem = new KantinSystem();
    private User currentUser;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLogin();
        primaryStage.setTitle("Kantin Digital UMM");
        primaryStage.show();
    }

    public void showLogin() {
        LoginPane loginPane = new LoginPane(this, loginSystem);
        Scene scene = new Scene(loginPane, 400, 300);
        primaryStage.setScene(scene);
    }

    public void showMahasiswaDashboard(User user) {
        this.currentUser = user;
        MahasiswaDashboard dash = new MahasiswaDashboard(this, user, kantinSystem);
        Scene scene = new Scene(dash);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // langsung fullscreen

        // Fade-in animation biar smooth
        javafx.animation.FadeTransition ft = new javafx.animation.FadeTransition(javafx.util.Duration.millis(500), dash);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    public void showAdminDashboard() {
        AdminDashboard dash = new AdminDashboard(primaryStage, kantinSystem);
        Scene scene = new Scene(dash);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // ← fullscreen otomatis

        // Fade-in animation (opsional tapi keren)
        javafx.animation.FadeTransition ft = new javafx.animation.FadeTransition(javafx.util.Duration.millis(500), dash);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
