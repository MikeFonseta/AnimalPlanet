package com.mikefonseta.animalplanet;

import com.mikefonseta.animalplanet.Database.Receipt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("/main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1300, 800);
            stage.setTitle("Animal Planet - Mike Fonseta");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 1", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}