package com.mikefonseta.animalplanet;

import com.mikefonseta.animalplanet.Database.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class addCategoriaController {

    @FXML
    private TextField nome;

    public void addCategoria(){
        if(!nome.getText().isBlank() && !nome.getText().isEmpty())
        {
            try {
                if(Product.addCategoria(nome.getText()) == 1){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Categoria '"+nome.getText() + "' aggiunta!", ButtonType.OK);
                    alert.setTitle("");
                    alert.setHeaderText("");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Stage stage = (Stage) nome.getScene().getWindow();
                        stage.close();
                    }
                }
            } catch (SQLException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante la connessione al database", ButtonType.OK);
                alert.setTitle("");
                alert.setHeaderText("");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Stage stage = (Stage) nome.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

}
