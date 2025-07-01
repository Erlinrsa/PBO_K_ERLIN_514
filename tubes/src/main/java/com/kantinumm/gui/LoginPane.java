package main.java.com.kantinumm.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.java.com.kantinumm.model.User;
import main.java.com.kantinumm.service.LoginSystem;

public class LoginPane extends BorderPane {
    public LoginPane(MainApp app, LoginSystem loginSystem) {
        // Judul Atas
        VBox headerBox = new VBox(5);
        Text title = new Text("Welcome!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Text subtitle = new Text("Kantin Digital UMM");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Text desc = new Text("Sistem Informasi Kantin Universitas Muhammadiyah Malang");
        desc.setFont(Font.font("Arial", FontWeight.LIGHT, 12));
        headerBox.getChildren().addAll(title, subtitle, desc);
        headerBox.setAlignment(Pos.CENTER);

        // Form Login
        VBox loginBox = new VBox(10);
        loginBox.setPadding(new Insets(20));
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setMaxWidth(300);
        loginBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        Label loginLabel = new Label("Sign In");
        loginLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));

        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("admin", "mahasiswa");
        roleBox.setPromptText("Choose your role");

        TextField userField = new TextField();
        userField.setPromptText("Enter your username");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter your password");

        Button loginBtn = new Button("Sign In");
        loginBtn.setMaxWidth(Double.MAX_VALUE);

        Label statusLabel = new Label();

        loginBtn.setOnAction(e -> {
            String user = userField.getText();
            String pass = passField.getText();
            String role = roleBox.getValue();

            User u = loginSystem.login(user, pass, role);
            if (u != null) {
                if (role.equals("admin")) app.showAdminDashboard();
                else app.showMahasiswaDashboard(u);
            } else {
                statusLabel.setText("Login gagal. Coba lagi.");
            }
        });

        loginBox.getChildren().addAll(
                loginLabel,
                roleBox,
                userField,
                passField,
                loginBtn,
                statusLabel
        );

        VBox contentBox = new VBox(20, headerBox, loginBox);
        contentBox.setAlignment(Pos.CENTER);

        setCenter(contentBox);
        setPadding(new Insets(50));
    }
}
