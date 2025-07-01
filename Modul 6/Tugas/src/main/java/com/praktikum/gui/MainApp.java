package main.java.com.praktikum.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        setScene(new LoginPane(this));
        stage.setTitle("Login Sistem");
        stage.show();
    }

    public void setScene(javafx.scene.layout.Pane pane) {
        primaryStage.setScene(new Scene(pane, 400, 400));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
