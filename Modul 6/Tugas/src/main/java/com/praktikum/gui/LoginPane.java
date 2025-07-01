package main.java.com.praktikum.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import main.java.com.praktikum.users.User;
import main.java.com.praktikum.users.Admin;
import main.java.com.praktikum.users.Mahasiswa;
import main.java.com.praktikum.main.LoginSystem;

public class LoginPane extends VBox {

    public LoginPane(MainApp mainApp) {
        Label title = new Label("Sistem Login");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // ComboBox untuk memilih role
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("admin", "mahasiswa");
        roleBox.setPromptText("Pilih Role");

        Button loginBtn = new Button("Login");
        Button loginBtn = new Button("Logout");
        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleBox.getValue();

            if (role == null || role.isEmpty()) {
                showAlert("Silakan pilih role terlebih dahulu.");
                return;
            }

            handleLogin(mainApp, username, password, role);
        });

        setSpacing(10);
        setPadding(new Insets(20));
        getChildren().addAll(title, usernameField, passwordField, roleBox, loginBtn);
    }

    private void handleLogin(MainApp mainApp, String username, String password, String role) {
        User loggedInUser = LoginSystem.login(username, password, role);
        if (loggedInUser != null) {
            if (loggedInUser instanceof Admin) {
                mainApp.setScene(new AdminDashboard(mainApp, (Admin) loggedInUser)); // Cast ke Admin
            } else if (loggedInUser instanceof Mahasiswa) {
                mainApp.setScene(new MahasiswaDashboard(mainApp, (Mahasiswa) loggedInUser)); // Cast ke Mahasiswa
            } else {
                showAlert("Role pengguna tidak dikenali.");
            }
        } else {
            showAlert("Username atau Password salah.");
        }
    }


    private void showAlert(String pesan) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
}
