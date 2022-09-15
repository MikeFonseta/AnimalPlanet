package com.mikefonseta.animalplanet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("/main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1300, 800);
            stage.setTitle("Animal Planet - Mike Fonseta v1.0.1");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 1", ButtonType.OK);
            e.printStackTrace();
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}